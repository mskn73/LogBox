package com.mskn73.logbox_retrofit_interceptor

import com.mskn73.logsbox.LogBox
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okio.Buffer
import okio.GzipSource
import java.io.EOFException
import java.io.IOException
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit

/**
 * An OkHttp interceptor which logs request and response information. Can be applied as an
 * [application interceptor][OkHttpClient.interceptors] or as a [ ][OkHttpClient.networkInterceptors].
 *
 * The format of the logs created by
 * this class should not be considered stable and may change slightly between releases. If you need
 * a stable logging format, use your own interceptor.
 */
class LogboxHttpLoggingInterceptor : Interceptor {
    enum class Level {
        /** No logs.  */
        NONE,
        /**
         * Logs request and response lines.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1 (3-byte body)
         *
         * <-- 200 OK (22ms, 6-byte body)
        `</pre> *
         */
        BASIC,
        /**
         * Logs request and response lines and their respective headers.
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         * <-- END HTTP
        `</pre> *
         */
        HEADERS,
        /**
         * Logs request and response lines and their respective headers and bodies (if present).
         *
         *
         * Example:
         * <pre>`--> POST /greeting http/1.1
         * Host: example.com
         * Content-Type: plain/text
         * Content-Length: 3
         *
         * Hi?
         * --> END POST
         *
         * <-- 200 OK (22ms)
         * Content-Type: plain/text
         * Content-Length: 6
         *
         * Hello!
         * <-- END HTTP
        `</pre> *
         */
        BODY
    }

    interface Logger {
        fun log(
            title: String,
            requestHeaders: List<String>,
            request: String,
            responseHeaders: List<String>,
            response: String,
            responseTime: Long
        )

        companion object {
            /** A [Logger] defaults output appropriate for the current platform.  */
            val DEFAULT: Logger =
                object :
                    Logger {
                    override fun log(
                        title: String,
                        requestHeaders: List<String>,
                        request: String,
                        responseHeaders: List<String>,
                        response: String,
                        responseTime: Long
                    ) {
                        LogBox.log("network", title, requestHeaders, request, responseHeaders, response, responseTime)
                    }
                }
        }
    }

    constructor() {
        logger = Logger.DEFAULT
    }

    constructor(logger: Logger) {
        this.logger = logger
    }

    private val logger: Logger
    @Volatile
    var level =
        Level.BODY
        private set

    /** Change the level at which this interceptor logs.  */
    fun setLevel(level: Level): LogboxHttpLoggingInterceptor {
        this.level = level
        return this
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val lbTitle = StringBuilder()
        val lbRequestHeaders = mutableListOf<String>()
        val lbRequestBody = StringBuilder()
        val lbResponseHeaders = mutableListOf<String>()
        val lbResponseBody = StringBuilder()
        val level =
            level
        val request = chain.request()
        if (level == Level.NONE) {
            return chain.proceed(request)
        }
        val logBody = level == Level.BODY
        val logHeaders = logBody || level == Level.HEADERS
        val requestBody = request.body()
        val connection = chain.connection()
        var requestStartMessage = (
                request.method() +
                ' ' + request.url() +
                if (connection != null) " " + connection.protocol() else "")
        if (!logHeaders) {
            requestBody?.let { body ->
                requestStartMessage += " (" + body.contentLength() + "-byte body)"
            }
        }
        lbTitle.append(requestStartMessage)
        if (logHeaders) {
            requestBody?.let {
                // Request body headers are only present when installed as a network interceptor. Force
                // them to be included (when available) so there values are known.
                if (requestBody.contentType() != null) {
                    lbRequestHeaders.add("Content-Type: ${requestBody.contentType()}")
                }
                if (requestBody.contentLength() != -1L) {
                    lbRequestHeaders.add("Content-Length: ${requestBody.contentLength()}")
                }
            }
            val headers = request.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                val name = headers.name(i)
                // Skip headers from the request body as they are explicitly logged above.
                if (
                    !"Content-Type".equals(name, ignoreCase = true) &&
                    !"Content-Length".equals(name, ignoreCase = true)
                ) {
                    lbRequestHeaders.add("$name: ${headers.value(i)}")
                }
                i++
            }
            if (!logBody) {
                requestBody?.let { _ ->
                    lbRequestBody.append("(body not logged)").append(request.method()).append("\n")
                }
            } else if (bodyHasUnknownEncoding(request.headers())) {
                lbRequestBody.append(request.method())
                    .append("(bodyHasUnknownEncoding - encoded body omitted)").append("\n")
            } else {
                val buffer = Buffer()
                requestBody?.writeTo(buffer)
                var charset = UTF8
                requestBody?.contentType()?.let { contentType ->
                    charset = contentType.charset(UTF8) ?: UTF8
                }
                if (isPlaintext(buffer)) {
                    lbRequestBody.append(buffer.readString(charset))
//                    lbRequestBody.append("--> END ").append(request.method).append(" (")
//                        .append(requestBody?.contentLength()).append("-byte body)")
                } else {
                    lbRequestBody.append(
                        "(binary " + requestBody?.contentLength() + "-byte body omitted)"
                    )
                }
            }
        }
        val startNs = System.nanoTime()
        val response: Response
        response = try {
            chain.proceed(request)
        } catch (e: Exception) {
            lbResponseBody.append("HTTP FAILED: ").append(e)
            logger.log(lbTitle.toString(), lbRequestHeaders, lbRequestBody.toString(), lbResponseHeaders, lbResponseBody.toString(), -1)
            throw e
        }
        val tookMs = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startNs)
        // lbTitle.append(" | ").append(request.url).append(" (").append(tookMs).append(" ms)")
        val responseBody = response.body()
        val contentLength = responseBody?.contentLength()
        val bodySize = if (contentLength != -1L) "$contentLength-byte" else "unknown-length"
//        lbResponseBody.append("<-- ").append(response.code)
//            .append(if (response.message.isEmpty()) "" else ' '.toString() + response.message)
//            .append(' ').append(response.request.url).append(" (").append(tookMs).append("ms")
//            .append(if (!logHeaders) ", $bodySize body" else "").append(')')
        if (logHeaders) {
            val headers = response.headers()
            var i = 0
            val count = headers.size()
            while (i < count) {
                lbResponseHeaders.add("${headers.name(i)}: ${headers.value(i)}")
                i++
            }
            if (!logBody || response.body() == null) {
                lbResponseBody.append("(body not logged)")
            } else if (bodyHasUnknownEncoding(response.headers())) {
                lbResponseBody.append("(encoded body omitted)")
            } else {
                responseBody?.let { responseBody ->
                    val source = responseBody.source()
                    source.request(Long.MAX_VALUE) // Buffer the entire body.
                    var buffer = source.buffer()
                    var gzippedLength: Long? = null
                    if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
                        gzippedLength = buffer.size()
                        var gzippedResponseBody: GzipSource? = null
                        try {
                            gzippedResponseBody = GzipSource(buffer.clone())
                            buffer = Buffer()
                            buffer.writeAll(gzippedResponseBody)
                        } finally {
                            gzippedResponseBody?.close()
                        }
                    }
                    var charset = UTF8
                    val contentType = responseBody.contentType()
                    contentType?.let {
                        charset = it.charset(UTF8) ?: UTF8
                    }

                    if (!isPlaintext(buffer)) {
                        lbResponseBody.append("(binary ").append(buffer.size()).append("-byte body omitted)")
                        logger.log(lbTitle.toString(), lbRequestHeaders, lbRequestBody.toString(), lbResponseHeaders, lbResponseBody.toString(), tookMs)
                        return response
                    }
                    if (contentLength != 0L) {
                        lbResponseBody.append(buffer.clone().readString(charset))
                    }
//                    if (gzippedLength != null) {
//                        lbResponseBody.append("<-- END HTTP (").append(buffer.size).append("-byte, ")
//                            .append(gzippedLength).append("-gzipped-byte body)")
//                    } else {
//                        lbResponseBody.append("<-- END HTTP (").append(buffer.size)
//                            .append("-byte body)")
//                    }
                } ?: lbResponseBody.append("(no body)")
            }
        }
        logger.log(lbTitle.toString(), lbRequestHeaders, lbRequestBody.toString(), lbResponseHeaders, lbResponseBody.toString(), tookMs)
        return response
    }

    private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
        val contentEncoding = headers["Content-Encoding"]
        return (contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true) &&
                !contentEncoding.equals("gzip", ignoreCase = true))
    }

    companion object {
        private val UTF8 = Charset.forName("UTF-8")
        /**
         * Returns true if the body in question probably contains human readable text. Uses a small sample
         * of code points to detect unicode control characters commonly used in binary file signatures.
         */
        private fun isPlaintext(buffer: Buffer): Boolean {
            return try {
                val prefix = Buffer()
                val byteCount = if (buffer.size() < 64) buffer.size() else 64
                buffer.copyTo(prefix, 0, byteCount)
                for (i in 0..15) {
                    if (prefix.exhausted()) {
                        break
                    }
                    val codePoint = prefix.readUtf8CodePoint()
                    if (Character.isISOControl(codePoint) && !Character.isWhitespace(
                            codePoint
                        )
                    ) {
                        return false
                    }
                }
                true
            } catch (e: EOFException) {
                false // Truncated UTF-8 sequence.
            }
        }
    }
}
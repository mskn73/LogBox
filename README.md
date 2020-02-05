# LogsBox
LogBox stores logs for developer purposes.

## Usage
You need to initialize the library with:
`LogBox.init(application)`

Also you can configure some extras (this params are optionals):
- databasename: internally it uses room.
- shakeDetector: `true` if you want to open LogBox when a shake is detected
- maxRows: for each category, you can change the limit of stored rows

`LogBox.init(applicationContext, databaseName = "logsbox.db", shakeDetection = true, maxRows = 2000)`

### Log some data
```kotlin
LogBox.log(
        type: String,       // this categorize the log in the tab
        title: String,
        requestHeaders: List<String> = emptyList(), // if is empty it will not shows the request headers section in detail
        request: String,
        responseHeaders: List<String> = emptyList(), // if is empty it will not shows the response headers section in detail
        response: String,
        responseTime: Long = -1     //if is lower than 0 it will not shows the response time in detail
    )
```

### OkHttp interceptor
if you are using OkHttp you can add the logbox interceptor to the builder
```kotlin
OkHttpClient().newBuilder()
      .addInterceptor(LogboxHttpLoggingInterceptor())
      .build()
```

The log level can be also configured with `setLevel()`:
```kotlin
OkHttpClient().newBuilder()
      .addInterceptor(LogboxHttpLoggingInterceptor().setLevel(Level.BODY))
      .build()
```

and you can provide a custom logger if you want to change some thing in the log:
```kotlin
LogboxHttpLoggingInterceptor(object : LogboxHttpLoggingInterceptor.Logger {
    override fun log(title: String, requestHeaders: List<String>, request: String, responseHeaders: List<String>, response: String, responseTime: Long) {
        LogBox.log(................)
    }
})
```

## Dependencies:
Add it in your root build.gradle at the end of repositories:
```kotlin
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

## Dependency [![](https://jitpack.io/v/mskn73/LogBox.svg)](https://jitpack.io/#mskn73/LogBox)

`implementation  "com.github.mskn73.LogBox:logsbox:$logboxVersion"`

If you need a no-operation version for release builds, you can use:
```
debugImplementation  "com.github.mskn73.LogBox:logsbox:$logboxVersion"
releaseImplementation "com.github.mskn73.LogBox:logsbox:$logboxVersion:Noop@aar"
```

### OkHttpInterceptor
```
debugImplementation "com.github.mskn73.LogBox:logbox-retrofit-interceptor:$logboxVersion"
releaseImplementation "com.github.mskn73.LogBox:logbox-retrofit-interceptor:$logboxVersion:Noop@aar"
```

# ADTUtils

[![](https://jitpack.io/v/HonzaR/ADTUtils.svg)](https://jitpack.io/#HonzaR/ADTUtils)

Android Library providing different kind of util classes. List of util classes:
    
    AppPropertyUtils
    ColorUtils
    EqualsUtils
    IntentUtils
    JsonUtils
    LogUtils
    MeasuresUtils
    NumberUtils
    StringUtils
    Utils
    ViewUtils
    
## How to Use
```
  JsonObject o = new JsonObject();
  o.addProperty("test", true);
  Boolean b = JsonUtils.getBoolean(o, "test");

  LogUtils.d(com.honzar.adtutils.sampleApp.BuildConfig.DEBUG, MainActivity.class.getName(), "test: " + b);
```

## Integration
This library is hosted by jitpack.io.

Root level gradle:
```
allprojects {
 repositories {
    jcenter()
    maven { url "https://jitpack.io" }
 }
}
```

Application level gradle:
```
dependencies {
    compile 'com.github.HonzaR:ADTUtils:x.y.z'
}
```

## Proguard
```
-keep class com.honzar.adtutils.library.** {*;}
```

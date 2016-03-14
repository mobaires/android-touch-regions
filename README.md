android-touch-regions
=====================

Wondered how to make a map where the user could touch different regions and detect those actions?
Or maybe a Car wireframe?
Body parts or maybe a way to implement a paint for kids app?

android-touch-regions manages touchable and selectable regions.

Gradle dependency
-----------------

```
dependencies {
    ...
    compile 'com.mobaires.regionswithdetection:android-touch-regions-lib:0.1-alpha'
}
```
**Note:** Please note that with the 0.1-alpha release you will have a merger error during the build because of an already used icon in the library (next release will fix it removing that).
That can be fixed adding tools:replace in the manifest:
```
<application
        tools:replace="android:icon"
        android:icon="@mipmap/ic_launcher"
```
And the tools namespace in the manifest root element:
```
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
```

One of the examples included
----------------------------

Here is an example of a Westeros (+ the western part of Essos) with the different regions being selectable:
![Westeros Screenshot](https://raw.githubusercontent.com/Cristo86/android-touch-regions/master/stuff/westeros-screenshot.png "Westeros Screenshot")

For the moment the way to get it working is checking out how it is done in the given example modules: *sample* and *westerosmap*


# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-dontwarn java.lang.reflect.AnnotatedType
-dontwarn java.lang.reflect.AnnotatedElement
-dontwarn java.lang.reflect.AnnotatedParameterizedType
-dontwarn java.lang.reflect.AnnotatedArrayType
-dontwarn java.lang.reflect.AnnotatedWildcardType

-keepattributes Signature
-keep,allowobfuscation class * extends com.highcapable.kavaref.extension.TypeRef
-keepclassmembers class * extends com.highcapable.kavaref.extension.TypeRef {
    <init>(...);
}
-keep class com.highcapable.kavaref.extension.TypeRef {*;}
-keep class com.kaerushi.monetify.xposed {*;}
-keep class com.highcapable.yukihookapi {*;}
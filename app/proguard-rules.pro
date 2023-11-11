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

# LightAop必备混淆规则 -----start-----

-keep @com.flyjingfish.light_aop_annotation.* class * {*;}
-keep @com.flyjingfish.light_aop_core.annotations.* class * {*;}
-keep @org.aspectj.lang.annotation.* class * {*;}
-keep class * {
    @com.flyjingfish.light_aop_core.annotations.* <fields>;
    @org.aspectj.lang.annotation.* <fields>;
}
-keepclassmembers class * {
    @com.flyjingfish.light_aop_core.annotations.* <methods>;
    @org.aspectj.lang.annotation.* <methods>;
}

-keepnames class * implements com.flyjingfish.light_aop_annotation.BasePointCut
-keepnames class * implements com.flyjingfish.light_aop_annotation.MatchClassMethod
-keep class * implements com.flyjingfish.light_aop_annotation.BasePointCut{
    public <init>();
}
-keepclassmembers class * implements com.flyjingfish.light_aop_annotation.BasePointCut{
    <methods>;
}

-keep class * implements com.flyjingfish.light_aop_annotation.MatchClassMethod{
    public <init>();
}
-keepclassmembers class * implements com.flyjingfish.light_aop_annotation.MatchClassMethod{
    <methods>;
}
# LightAop必备混淆规则 -----end-----


# 你自定义的混淆规则 -----start-----
-keep @com.flyjingfish.test_lib.annotations.* class * {*;}
-keep class * {
    @com.flyjingfish.test_lib.annotations.* <fields>;
}
-keepclassmembers class * {
    @com.flyjingfish.test_lib.annotations.* <methods>;
}
# 你自定义的混淆规则 -----end-----
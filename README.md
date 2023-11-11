# LightAOP
轻量级Aop框架


混淆规则
```
-keep @com.flyjingfish.light_aop_core.annotations.* class * {*;}
-keep @com.flyjingfish.light_aop_annotation.* class * {*;}
-keep @org.aspectj.lang.annotation.* class * {*;}
-keep class * {
    @com.flyjingfish.light_aop_core.annotations.* <fields>;
    @com.flyjingfish.light_aop_annotation.* <fields>;
    @org.aspectj.lang.annotation.* <fields>;
}
-keepclassmembers class * {
    @com.flyjingfish.light_aop_core.annotations.* <methods>;
    @com.flyjingfish.light_aop_annotation.* <methods>;
    @org.aspectj.lang.annotation.* <methods>;
}
```
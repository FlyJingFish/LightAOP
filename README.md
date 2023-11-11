# LightAOP

轻量级Aop框架，你值得拥有，心动不如行动，赶紧用起来吧

[![Maven central](https://img.shields.io/maven-central/v/io.github.FlyJingFish.LightAop/LightAopLib)](https://central.sonatype.com/search?q=io.github.FlyJingFish.LightAop)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/LightAop.svg)](https://github.com/FlyJingFish/LightAop/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/LightAop.svg)](https://github.com/FlyJingFish/LightAop/network/members)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/LightAop.svg)](https://github.com/FlyJingFish/LightAop/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/LightAop.svg)](https://github.com/FlyJingFish/LightAop/blob/master/LICENSE)

## 使用步骤

#### 一、在项目根目录下的build.gradle添加（必须）

```gradle
buildscript {
    dependencies {
        classpath 'io.github.FlyJingFish.LightAop:light-aop-plugin:1.0.0'
    }
}
```

#### 二、在 app 的build.gradle添加（此步为必须项）

#### ⚠️注意：👆此步为必须项👇

```gradle
plugins {
    id 'light.aop'
}
```

#### 三、引入依赖库

- A、在app 的 module 下使用

```gradle
plugins {
    id 'light.aop'
}
dependencies {
    implementation 'io.github.FlyJingFish.LightAop:light-aop-annotation:1.0.0'
    annotationProcessor 'io.github.FlyJingFish.LightAop:light-aop-processor:1.0.0'
    implementation 'io.github.FlyJingFish.LightAop:light-aop-core:1.0.0'
}
```

- B、在您定义的基础库 的 module 下使用

```gradle
plugins {
    id 'light.aop'
}
dependencies {
    api 'io.github.FlyJingFish.LightAop:light-aop-annotation:1.0.0'
    annotationProcessor 'io.github.FlyJingFish.LightAop:light-aop-processor:1.0.0'
    api 'io.github.FlyJingFish.LightAop:light-aop-core:1.0.0'
}
```

### 本库内置了一些功能注解可供你直接使用

| 注解名称             |            参数说明            |                 功能说明                  |
|------------------|:--------------------------:|:-------------------------------------:|
| @SingleClick     |        value = 时间间隔        |      单击注解，加入此注解，可是你的方法只有单击时才可进入       |
| @DoubleClick     |        value = 时间间隔        |       双击注解，加入此注解，可是你的方法双击时才可进入        |
| @IOThread        |     ThreadType = 线程类型      |   切换到子线程的操作，加入此注解可使你的方法内的代码切换到子线程执行   |
| @MainThread      |            无参数             |   切换到主线程的操作，加入此注解可使你的方法内的代码切换到主线程执行   |
| @OnLifecycle     |  value = Lifecycle.Event   | 监听生命周期的操作，加入此注解可使你的方法内的代码在对应生命周期内才去执行 |
| @TryCatch        |    value = 你自定义加的一个flag    |     加入此注解可为您的方法包裹一层 try catch 代码      |
| @Permission      |      value = 权限的字符串数组      |     申请权限的操作，加入此注解可使您的代码在获取权限后才执行      |
| @CustomIntercept | value = 你自定义加的一个字符串数组的flag |         自定义拦截，此注解可以加到方法和构造器上          |


### 下面着重介绍下 @TryCatch @Permission @CustomIntercept

- @TryCatch 使用自注解你可以设置

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
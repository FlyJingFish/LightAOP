# LightAOP

轻量级Aop框架，你值得拥有，心动不如行动，赶紧用起来吧

[![Maven central](https://img.shields.io/maven-central/v/io.github.FlyJingFish.LightAop/LightAopLib)](https://central.sonatype.com/search?q=io.github.FlyJingFish.LightAop)
[![GitHub stars](https://img.shields.io/github/stars/FlyJingFish/LightAop.svg)](https://github.com/FlyJingFish/LightAop/stargazers)
[![GitHub forks](https://img.shields.io/github/forks/FlyJingFish/LightAop.svg)](https://github.com/FlyJingFish/LightAop/network/members)
[![GitHub issues](https://img.shields.io/github/issues/FlyJingFish/LightAop.svg)](https://github.com/FlyJingFish/LightAop/issues)
[![GitHub license](https://img.shields.io/github/license/FlyJingFish/LightAop.svg)](https://github.com/FlyJingFish/LightAop/blob/master/LICENSE)

## 特色功能

1、本库内置了开发中常用的一些切面注解供你使用

2、本库支持让你自己做切面，语法简单易上手

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

- @TryCatch 使用此注解你可以设置
```java
LightAop.INSTANCE.setOnThrowableListener(new OnThrowableListener() {
    @Nullable
    @Override
    public Object handleThrowable(@NonNull String flag, @Nullable Throwable throwable) {
        // TODO: 2023/11/11 发生异常可根据你当时传入的flag作出相应处理，如果需要改写返回值，则在 return 处返回即可
        return 3;
    }
});
```

- @Permission 使用此注解你可以设置
```java
LightAop.INSTANCE.setOnPermissionsInterceptListener(new OnPermissionsInterceptListener() {
    @SuppressLint("CheckResult")
    @Override
    public void requestPermission(@NonNull ProceedingJoinPoint joinPoint, @NonNull Permission permission, @NonNull OnRequestPermissionListener call) {
        Object target =  joinPoint.getTarget();
        if (target instanceof FragmentActivity){
            RxPermissions rxPermissions = new RxPermissions((FragmentActivity) target);
            rxPermissions.request(permission.value()).subscribe(call::onCall);
        }else if (target instanceof Fragment){
            RxPermissions rxPermissions = new RxPermissions((Fragment) target);
            rxPermissions.request(permission.value()).subscribe(call::onCall);
        }
    }
});
```

- @CustomIntercept 使用此注解你可以设置
```java
LightAop.INSTANCE.setOnCustomInterceptListener(new OnCustomInterceptListener() {
    @Nullable
    @Override
    public Object invoke(@NonNull ProceedingJoinPoint joinPoint, @NonNull CustomIntercept customIntercept) {
        // TODO: 2023/11/11 在此写你的逻辑 在合适的地方调用 joinPoint.proceed()，
        //  joinPoint.proceed(args)可以修改方法传入的参数，如果需要改写返回值，则在 return 处返回即可

        return null;
    }
});
```



在这介绍下 在使用 ProceedingJoinPoint 这个对象的 proceed() 或 proceed(args) 表示执行原来方法的逻辑，区别是：

- proceed() 不传参，表示不改变当初的传入参数，
- proceed(args) 有参数，表示改写当时传入的参数

在此的return 返回的就是对应拦截的那个方法返回的


### 此外本库也同样支持让你自己做切面，语法相对来说也比较简单，你不用关心该如何编写AspectJ的切面

本库中提供了 @LightAopPointCut 和 @LightAopMatchClassMethod 两种切面供你使用

⚠️被两个注解的类只可以用 Java 代码

- @LightAopPointCut 是在方法上和构造器上做切面的，上述中注解都是通过这个做的

下面以 @CustomIntercept 为例介绍下该如何使用

```java
@LightAopPointCut(CustomInterceptCut.class)
@Target({ElementType.METHOD,ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
public @interface CustomIntercept {
    String[] value() default {};
}
```
@LightAopPointCut 的 CustomInterceptCut.class 为您处理切面的类

@Target 的 ElementType.METHOD 表示作用在方法上

@Target 的 ElementType.CONSTRUCTOR 表示作用在构造器上

@Retention 只可以用 RetentionPolicy.RUNTIME

@Target 只可以传 ElementType.METHOD 和 ElementType.CONSTRUCTOR,传其他无作用

CustomInterceptCut 的代码(可以用kotlin) 如下：

```kotlin
class CustomInterceptCut : BasePointCut<CustomIntercept> {
    override fun invoke(
        joinPoint: ProceedingJoinPoint,
        annotation: CustomIntercept
    ): Any? {
        // 在此写你的逻辑
        return joinPoint.proceed()
    }
}
```

- @LightAopMatchClassMethod 是做匹配类和类方法的切面的

```java
@LightAopMatchClassMethod(targetClassName = "com.flyjingfish.test_lib.BaseActivity", methodName = {"onCreate","onResume"})
public class MatchActivityOnCreate implements MatchClassMethod {
    @Nullable
    @Override
    public Object invoke(@NonNull ProceedingJoinPoint joinPoint, @NonNull String methodName) {
        Log.e("MatchActivityOnCreate","invoke="+methodName);
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            return null;
        }
    }
}
```

上边表示凡是继承自 com.flyjingfish.test_lib.BaseActivity 的类执行 onCreate 和 onResume 方法时则进行切面

⚠️注意如果你没写对应的方法或者没有重写父类的该方法则切面无效

例如你想做退出登陆逻辑时可以使用这个，注意实现MatchClassMethod接口的类只可以用 Java 代码

#### 混淆规则

下边是涉及到本库的一些混淆规则

```
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

-keep class * implements com.flyjingfish.light_aop_annotation.BasePointCut
-keep class * implements com.flyjingfish.light_aop_annotation.MatchClassMethod
-keep class * implements com.flyjingfish.light_aop_annotation.BasePointCut{
    <fields>;
}
-keepclassmembers class * implements com.flyjingfish.light_aop_annotation.BasePointCut{
    <methods>;
}
```

如果你自己写了新的切面代码，记得加上你的混淆规则
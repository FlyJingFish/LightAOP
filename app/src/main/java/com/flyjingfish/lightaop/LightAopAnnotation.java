package com.flyjingfish.lightaop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface LightAopAnnotation {
    byte byteValue() default 0;

    short shortValue() default 0;

    int intValue() default 0;

    long longValue() default 0;

    float floatValue() default 0;

    double doubleValue() default 0;

    char charValue() default 0;

    boolean booleanValue() default false;

    String stringValue() default "";

    byte[] byteValues() default {};

    short[] shortValues() default {};

    int[] intValues() default {};

    long[] longValues() default {};

    float[] floatValues() default {};

    double[] doubleValues() default {};

    char[] charValues() default {};

    boolean[] booleanValues() default {};

    String[] stringValues() default {};
}

package com.flyjingfish.test_lib.match;

import com.flyjingfish.light_aop_annotation.LightAopClass;
import com.flyjingfish.light_aop_annotation.LightAopMethod;


@LightAopClass
public class TestLightAopClass {
    @LightAopMethod("@com.flyjingfish.test_lib.annotations.MyAnno")
    public final void targetMethod() {
    }

}

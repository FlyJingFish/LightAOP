package com.flyjingfish.lightaop;


import com.flyjingfish.light_aop_core.annotations.MainThread;

public interface TestInterface {
    @MainThread
    public void onTest();
}

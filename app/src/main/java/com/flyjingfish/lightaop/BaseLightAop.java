package com.flyjingfish.lightaop;

import org.aspectj.lang.ProceedingJoinPoint;

public interface BaseLightAop {
    void beforeInvoke(DebugLog debugLog);
    Object invoke(ProceedingJoinPoint joinPoint,DebugLog debugLog);

    void afterInvoke(DebugLog debugLog);


}

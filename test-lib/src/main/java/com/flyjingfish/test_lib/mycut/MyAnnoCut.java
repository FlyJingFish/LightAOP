package com.flyjingfish.test_lib.mycut;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flyjingfish.light_aop_annotation.BasePointCut;
import com.flyjingfish.light_aop_annotation.ProceedJoinPoint;
import com.flyjingfish.test_lib.annotation.MyAnno;

public class MyAnnoCut implements BasePointCut<MyAnno> {
    @Nullable
    @Override
    public Object invoke(@NonNull ProceedJoinPoint joinPoint, @NonNull MyAnno anno) {
        return null;
    }
}

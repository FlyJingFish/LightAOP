package com.flyjingfish.lightaop;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.flyjingfish.light_aop_core.listeners.OnThrowableListener;
import com.flyjingfish.light_aop_core.utils.LightAop;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        LightAop.INSTANCE.setOnThrowableListener(new OnThrowableListener() {
            @Nullable
            @Override
            public Object handleThrowable(@NonNull String flag, @Nullable Throwable throwable) {
                // TODO: 2023/11/11 发生异常可根据你当时传入的flag作出相应处理，如果需要改写返回值，则在 return 处返回即可
                return 3;
            }
        });
    }
}

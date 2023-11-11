package com.flyjingfish.lightaop;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.flyjingfish.light_aop_core.annotations.CustomIntercept;
import com.flyjingfish.light_aop_core.annotations.Permission;
import com.flyjingfish.light_aop_core.listeners.OnCustomInterceptListener;
import com.flyjingfish.light_aop_core.listeners.OnPermissionsInterceptListener;
import com.flyjingfish.light_aop_core.listeners.OnRequestPermissionListener;
import com.flyjingfish.light_aop_core.listeners.OnThrowableListener;
import com.flyjingfish.light_aop_core.utils.LightAop;
import com.flyjingfish.test_lib.BaseActivity;
import com.tbruyelle.rxpermissions3.RxPermissions;

import org.aspectj.lang.ProceedingJoinPoint;

public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}

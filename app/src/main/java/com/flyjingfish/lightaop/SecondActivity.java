package com.flyjingfish.lightaop;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

import com.flyjingfish.light_aop_core.annotations.CustomIntercept;
import com.flyjingfish.test_lib.BaseActivity;

public class SecondActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("SecondActivity","---");
        super.onCreate(savedInstanceState);
    }
}

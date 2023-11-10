package com.flyjingfish.lightaop;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;

public class SecondActivity extends BaseActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        Log.e("SecondActivity","---");
        super.onCreate(savedInstanceState);
    }
}

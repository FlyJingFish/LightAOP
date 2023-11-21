package com.flyjingfish.lightaop

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.flyjingfish.test_lib.BaseActivity

abstract class BaseActivity2 :BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
}
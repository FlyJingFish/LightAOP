package com.flyjingfish.lightaop;

import android.util.Log;

import com.flyjingfish.light_aop_core.annotations.DefaultAnnotation;
import com.flyjingfish.light_aop_core.annotations.MainThread;
import com.flyjingfish.light_aop_core.annotations.Permission;


public class Round {
    public Runnable runnable;

   public Round() {
    }

//    @DebugLog
    @DefaultAnnotation
    @DebugLog2("hahhhhhhhhhhhh")
    public Runnable getRunnable() {
        return runnable;
    }

    @MainThread
    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
        Log.e("Round","setRunnable");
        Log.e("Round","setRunnable");
        Log.e("Round","setRunnable");
    }
}

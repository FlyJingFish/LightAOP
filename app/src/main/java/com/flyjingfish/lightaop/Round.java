package com.flyjingfish.lightaop;

import android.util.Log;

import com.flyjingfish.light_aop_core.annotations.CustomIntercept;
import com.flyjingfish.light_aop_core.annotations.MainThread;


public class Round {
    public Runnable runnable;

   public Round() {
    }

//    @DebugLog
    @CustomIntercept
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

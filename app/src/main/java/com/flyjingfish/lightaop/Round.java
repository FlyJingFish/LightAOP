package com.flyjingfish.lightaop;

import android.util.Log;

import com.flyjingfish.light_aop_core.annotations.CustomIntercept;
import com.flyjingfish.light_aop_core.annotations.MainThread;
import com.flyjingfish.test_lib.MyAnno;


public class Round {
    public Runnable runnable;


   public Round() {
    }

//    @DebugLog
//    @CustomIntercept
    @MyAnno
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

    public int getNumber() {
        return 1;
    }
}

package com.flyjingfish.lightaop;

import android.util.Log;


public class Round {
    public Runnable runnable;

    @DebugLog
    public Round() {
    }

//    @DebugLog
    public Runnable getRunnable() {
        return runnable;
    }
//    @DebugLog

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
        Log.e("Round","setRunnable");
    }
}

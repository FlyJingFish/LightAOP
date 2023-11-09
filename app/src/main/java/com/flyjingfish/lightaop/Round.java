package com.flyjingfish.lightaop;

import android.util.Log;

public class Round {
    public Runnable runnable;

    public Round() {
    }

    public Runnable getRunnable() {
        return runnable;
    }
    @DebugLog2

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
        Log.e("Round","setRunnable");
    }
}

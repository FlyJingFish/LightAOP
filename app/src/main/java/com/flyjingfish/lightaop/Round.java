package com.flyjingfish.lightaop;

public class Round {
    public Runnable runnable;

    @DebugLog
    public Round() {
    }

    @DebugLog
    public Runnable getRunnable() {
        return runnable;
    }

    @DebugLog
    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
    }
}

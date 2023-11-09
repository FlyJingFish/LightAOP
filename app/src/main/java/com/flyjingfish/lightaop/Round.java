package com.flyjingfish.lightaop;

import android.util.Log;


public class Round {
    public Runnable runnable;

   public Round() {
    }

//    @DebugLog
    @DebugLog2("hahhhhhhhhhhhh")
    @DebugLog(annotationClass = HahaLightAop.class ,lannotation = @LightAopAnnotation(stringValues = {"你哈哈好啦","2"}))
    public Runnable getRunnable() {
        return runnable;
    }
//    @DebugLog

    public void setRunnable(Runnable runnable) {
        this.runnable = runnable;
        Log.e("Round","setRunnable");
    }
}

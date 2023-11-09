package com.flyjingfish.lightaop

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var round :Round ?=null
        findViewById<Button>(R.id.haha).setOnClickListener {
//            round = Round()
            onClick()
        }

        findViewById<Button>(R.id.haha1).setOnClickListener {
//            round?.setRunnable { }
            onClick1()
        }

        findViewById<Button>(R.id.haha2).setOnClickListener {
            round?.getRunnable()
        }

    }

//    public fun onClick(){
//        Log.e("onClick","------")
//        //插入逻辑
//        val round = Round()
//        round.setRunnable {
//            onClick1()
//        }
//
//    }

    @DebugLog1
    public fun onClick(){
        Log.e("onClick","---1---")
    }





    @DebugLog2
    public fun onClick1(){
        Log.e("onClick","---2---")
    }
}
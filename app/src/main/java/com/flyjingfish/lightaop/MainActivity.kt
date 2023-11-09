package com.flyjingfish.lightaop

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.haha).setOnClickListener {
            onClick()
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

    public fun onClick(){
        val test = Test()
        if (test.intercept()){
            //植入代码
            val pointClass = PointClass()
            pointClass.setTest(test)
            pointClass.run()
            return
        }
        Log.e("onClick","------")
    }





    public fun onClick1(){
        Log.e("onClick","---1---")
    }
}
package com.flyjingfish.lightaop

import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.flyjingfish.light_aop_core.annotations.DoubleClick
import com.flyjingfish.light_aop_core.annotations.IOThread
import com.flyjingfish.light_aop_core.annotations.MainThread
import com.flyjingfish.light_aop_core.annotations.SingleClick
import com.flyjingfish.light_aop_core.enums.ThreadType

class MainActivity:BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var round :Round ?=null
        findViewById<Button>(R.id.haha).setOnClickListener {
            round = Round()
            onClick()
            startActivity(Intent(this,SecondActivity::class.java))
        }

        findViewById<Button>(R.id.haha1).setOnClickListener {
            round?.setRunnable { }

            startActivity(Intent(this,ThirdActivity::class.java))
            onSingleClick()
        }

        findViewById<Button>(R.id.haha2).setOnClickListener {
            round?.getRunnable()

            onDoubleClick()
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

    @IOThread(ThreadType.SingleIO)
    fun onClick(){
        Log.e("Test_MainThread","是否主线程="+(Looper.getMainLooper() == Looper.myLooper()))
        Log.e("Test_MainThread","开始睡5秒")
        Thread.sleep(5000)
        Log.e("Test_MainThread","睡醒了")
        onMainThread()
    }


    @MainThread
    fun onMainThread(){
        Log.e("Test_MainThread","onMainThread是否主线程="+(Looper.getMainLooper() == Looper.myLooper()))
    }

    @SingleClick
    fun onSingleClick(){
        Log.e("Test_click","onSingleClick")
    }

    @DoubleClick
    fun onDoubleClick(){
        Log.e("Test_click","onDoubleClick")
    }

}
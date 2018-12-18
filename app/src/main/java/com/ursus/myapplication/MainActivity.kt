package com.ursus.myapplication

import android.app.ActivityManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.ursus.core.CallManager
import com.ursus.core.CallService
import com.ursus.core.NotificationHelper
import com.ursus.feature1.BarFragment
import com.ursus.feature2.FooFragment
import dagger.android.AndroidInjection
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var notifHelper: NotificationHelper
    @Inject lateinit var callManager: CallManager
    @Inject lateinit var bar: Bar

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        Log.d("Default", "MainActivity # onCreate=$bar")
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "foo"
        textView.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, FooFragment())
                .commit()
        }

        val authorTextView = findViewById<TextView>(R.id.authorTextView)
        authorTextView.text = "bar"
        authorTextView.setOnClickListener {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, BarFragment())
                .commit()
        }

        notifHelper.showMissedCallNotification()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Log.d("Default", "starting service")
            startForegroundService(Intent(this, CallService::class.java).apply {
                action = CallService.ACTION_START
            })
        } else {
            startService(Intent(this, CallService::class.java))
        }
//        Log.d("Default", "stopping service")
//        startForegroundService(Intent(this, CallService::class.java).apply {
//            action = CallService.ACTION_STOP
//        })
        Log.d("Default", "barFragment # callManager=$callManager")
    }

    override fun onStart() {
        super.onStart()
        Log.d("Default", "MainActivity # onStart=${foo()}")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Default", "MainActivity # onResume=${foo()}")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Default", "MainActivity # onPause=${foo()}")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Default", "MainActivity # onStop=${foo()}")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Default", "MainActivity # onDestroy")
    }

    private fun foo(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val appProcesses = activityManager.runningAppProcesses
        for (appProcess in appProcesses) {
            if (appProcess.processName == packageName) {
                if (appProcess.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    return true
                }
            }
        }
        return false
    }
}

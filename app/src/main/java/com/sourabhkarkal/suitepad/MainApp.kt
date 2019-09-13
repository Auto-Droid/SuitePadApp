package com.sourabhkarkal.suitepad

import android.app.Application
import android.content.ComponentName
import android.content.Intent
import android.os.Build

class MainApp : Application() {

    override fun onCreate() {
        super.onCreate()

        val intent = Intent()
        intent.component =
            ComponentName("com.sourabhkarkal.httpproxy", "com.sourabhkarkal.httpproxy.HttpProxyService")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(intent)
        } else {
            startService(intent)
        }
    }
}
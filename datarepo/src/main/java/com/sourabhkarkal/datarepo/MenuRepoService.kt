package com.sourabhkarkal.datarepo

import android.app.Service
import android.content.Intent
import android.os.IBinder

class MenuRepoService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
        return mBinder
    }


    private val mBinder = object : IRemoteMenuInterface.Stub() {
        override fun getMenuJson(): String {
            return getCachedResponse();
        }
    }


    fun getCachedResponse() : String {

        return Utils.menuJsonStr
    }

}
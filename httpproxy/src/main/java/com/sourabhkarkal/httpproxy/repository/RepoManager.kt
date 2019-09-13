package com.sourabhkarkal.httpproxy.repository

import com.sourabhkarkal.datarepo.IRemoteMenuInterface
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.Request

class RepoManager private constructor() {

    companion object {
        private const val SOURCE_URL = "http://gist.githubusercontent.com/Rio517/5c95cc6402da8c5e37bc579111e14350/raw/b8ac727658a2aae2a4338d1cb7b1e91aca6288db/z_output.json"
        private var INSTANCE: RepoManager = RepoManager ()
        private var localCache : String = ""
        fun getInstance() : RepoManager {
            return INSTANCE
        }
    }

    fun getMenuData(mService: IRemoteMenuInterface?): String {
        if (mService != null) {
            localCache = mService.menuJson
        } else {
            populateLocalData()
        }
        return localCache
    }

    fun populateLocalData() {
        GlobalScope.launch {
            localCache = callApi()!!
        }
    }


    fun callApi(): String? {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url(SOURCE_URL)
            .build()
        val response = client.newCall(request).execute()
        return response.body()?.string()
    }


}
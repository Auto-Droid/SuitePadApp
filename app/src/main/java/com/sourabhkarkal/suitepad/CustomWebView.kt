package com.sourabhkarkal.suitepad

import android.annotation.TargetApi
import android.os.Build
import android.webkit.WebResourceRequest
import android.webkit.WebResourceResponse
import android.webkit.WebView
import android.webkit.WebViewClient
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class CustomWebView : WebViewClient() {

    companion object {
        private const val REMOTE_DATA_SOURCE_URL = "http://someremoteurl.com/sample.json"
        private const val LOCAL_HOST = "http://localhost:8080/file.json"
    }
    
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    override fun shouldInterceptRequest(view: WebView, request: WebResourceRequest): WebResourceResponse? {

        val url = request.url.toString()
        val response = super.shouldInterceptRequest(view, request)

        if (url.contains(REMOTE_DATA_SOURCE_URL) && response == null) {
            try {

                return sendProxyRequest(LOCAL_HOST)
            } catch (e: Exception) {
                e.printStackTrace()
                return response
            }

        }

        return response
    }

    override fun shouldInterceptRequest(view: WebView, url: String): WebResourceResponse? {
        val response = super.shouldInterceptRequest(view, url)

        if (url.contains(REMOTE_DATA_SOURCE_URL) && response == null) {
            try {
                return sendProxyRequest(LOCAL_HOST)
            } catch (e: IOException) {
                e.printStackTrace()
                return response
            }

        }
        return response
    }

    @Throws(IOException::class)
    private fun sendProxyRequest(url: String): WebResourceResponse {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url(url)
            .build()
        val response = client.newCall(request).execute()
        return WebResourceResponse(
            response.header("content-type", "text/plain"),
            response.header("content-encoding", "utf-8"),
            response.body()?.byteStream()
        )

    }

}
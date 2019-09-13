package com.sourabhkarkal.suitepad

import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.webkit.WebChromeClient
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.activity_main.*
import android.webkit.WebSettings



class MainActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.webViewClient = CustomWebView()

        webView.settings.javaScriptEnabled = true;
        webView.settings.javaScriptCanOpenWindowsAutomatically = true;
        webView.settings.allowUniversalAccessFromFileURLs = true
    }

    override fun onResume() {
        super.onResume()
        webView.loadUrl("file:///android_asset/sample.html")
    }


    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent()
        intent.component =
            ComponentName("com.sourabhkarkal.httpproxy", "com.sourabhkarkal.httpproxy.HttpProxyService")
        stopService(intent)
        System.exit(0)
    }

}

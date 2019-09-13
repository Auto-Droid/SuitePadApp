package com.sourabhkarkal.httpproxy.presenter

import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse

interface IProxyService {

    interface Presenter {
        fun onCreate()

        fun onStart()

        fun onDestroyed()
    }

    interface Service {
        fun onRequest(request: AsyncHttpServerRequest?, response: AsyncHttpServerResponse?)
    }

}
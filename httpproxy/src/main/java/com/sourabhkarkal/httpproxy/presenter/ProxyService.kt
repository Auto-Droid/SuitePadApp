package com.sourabhkarkal.httpproxy.presenter

import com.koushikdutta.async.AsyncServer
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import com.sourabhkarkal.httpproxy.repository.RepoManager

class ProxyService(val service : IProxyService.Service) : IProxyService.Presenter, HttpServerRequestCallback {

    private val server = AsyncHttpServer()
    private val mAsyncServer = AsyncServer()

    override fun onCreate() {
        server.get("/file.json", this)
        server.listen(mAsyncServer, 8080)
    }

    override fun onStart() {
        /*call api when service is started
           Can be used for prefetch call
         */
       //RepoManager.getInstance().populateLocalData()
    }

    override fun onDestroyed() {
        server.stop()
        mAsyncServer.stop()
    }

    override fun onRequest(request: AsyncHttpServerRequest?, response: AsyncHttpServerResponse?) {
        service.onRequest(request, response)
    }

}
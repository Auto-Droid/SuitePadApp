package com.sourabhkarkal.httpproxy

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.koushikdutta.async.AsyncServer
import com.koushikdutta.async.http.server.AsyncHttpServer
import com.koushikdutta.async.http.server.AsyncHttpServerRequest
import com.koushikdutta.async.http.server.AsyncHttpServerResponse
import com.koushikdutta.async.http.server.HttpServerRequestCallback
import com.sourabhkarkal.datarepo.IRemoteMenuInterface
import com.sourabhkarkal.httpproxy.presenter.IProxyService
import com.sourabhkarkal.httpproxy.presenter.ProxyService
import com.sourabhkarkal.httpproxy.repository.RepoManager


open class HttpProxyService : Service(), IProxyService.Service {

    private val presenter: IProxyService.Presenter = ProxyService(this)
    private var mServiceConnection: ServiceConnection? = null
    private var mService: IRemoteMenuInterface? = null
    private val packageStr: String = "com.sourabhkarkal.datarepo"
    private val serviceTag = "com.remote.service.MENU"

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)
        presenter.onStart()
        return Service.START_STICKY
    }

    override fun onCreate() {
        super.onCreate()
        presenter.onCreate()

    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroyed()
        unbindService(mServiceConnection);
    }

    override fun onBind(intent: Intent?): IBinder? {
        TODO("not implemented")
    }


    private fun initConnection(response: AsyncHttpServerResponse?) {
        mServiceConnection = object : ServiceConnection {


            override fun onServiceDisconnected(name: ComponentName) {
                mService = null
                Log.d("HttpProxyService", "HttpProxyService Binding - Service disconnected")
            }

            override fun onServiceConnected(name: ComponentName, service: IBinder) {
                mService = IRemoteMenuInterface.Stub.asInterface(service)
                response?.send(RepoManager.getInstance().getMenuData(mService))
                Log.d("HttpProxyService", " HttpProxyService Binding is done - Service connected")
            }

        }
        if (mService == null) {
            val intent = Intent(IRemoteMenuInterface::class.java.name)
            intent.action = serviceTag
            intent.setPackage(packageStr)
            bindService(intent, mServiceConnection, Service.BIND_AUTO_CREATE)
            response?.send(RepoManager.getInstance().getMenuData(null))
        } else {
            response?.send(RepoManager.getInstance().getMenuData(mService))
        }
    }


    override fun onRequest(request: AsyncHttpServerRequest?, response: AsyncHttpServerResponse?) {
        initConnection(response);
    }

}
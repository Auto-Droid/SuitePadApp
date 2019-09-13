package com.sourabhkarkal.httpproxy


import android.content.Context
import android.content.Intent
import com.sourabhkarkal.datarepo.IRemoteMenuInterface
import junit.framework.Assert.assertNotNull
import org.junit.Test
import org.mockito.Mockito

class HttpProxyServiceTest {

    val mServiceRule =  Mockito.mock(HttpProxyService::class.java)
    val context  = Mockito.mock(Context::class.java)

    @Test
    fun testWithStartedService() {
        mServiceRule.startService(
            Intent(context, HttpProxyService::class.java)
        )
    }

    @Test
    fun testWithBoundService() {
        val binder = mServiceRule.onBind(Intent(context, HttpProxyService::class.java))
        val iRemoteMenuInterface = IRemoteMenuInterface.Stub.asInterface(binder)
        assertNotNull(iRemoteMenuInterface)
    }

}
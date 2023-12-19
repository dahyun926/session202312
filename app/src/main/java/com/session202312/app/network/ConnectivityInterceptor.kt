package com.session202312.app.network

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import com.session202312.app.BaseApplication
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

open class ConnectivityInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!DetectConnection().checkInternetConnection(BaseApplication.applicationContext())) {
            val mHandler = Handler(Looper.getMainLooper())
            mHandler.postDelayed({
                Toast.makeText(
                    BaseApplication.applicationContext(),
                    "인터넷에 연결되어 있지 않습니다.",
                    Toast.LENGTH_SHORT
                ).show()
            }, 0)
        }
        val builder = chain.request().newBuilder()
        return chain.proceed(builder.build())
    }
}
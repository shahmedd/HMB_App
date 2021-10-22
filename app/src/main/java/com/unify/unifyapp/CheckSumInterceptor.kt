package com.unify.unifyapp

import com.unify.avanza.services.network.IUnifyInterceptor
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

object CheckSumInterceptor : Interceptor, IUnifyInterceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.header("checksum", "checksum value")
        return chain.proceed(requestBuilder.build())
    }
}
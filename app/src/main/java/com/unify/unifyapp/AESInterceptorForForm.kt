package com.unify.unifyapp

import android.util.Log
import com.unify.avanza.services.network.IUnifyInterceptor
import okhttp3.*
import okio.Buffer
import java.io.IOException

object AESInterceptorForForm : Interceptor, IUnifyInterceptor {
    lateinit var response: Response

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            var request: Request = chain.request()
            val requestBody: RequestBody = request.body()!!
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var strRequestBody: String = String(buffer.readByteArray())
            Log.e("REQUEST BODY => ", strRequestBody)
            var password = strRequestBody.substring(strRequestBody.indexOf("password") + 9, 49)
            strRequestBody = strRequestBody.replaceFirst(password, AESEncrypter.getInstance().encrypt(password))

            val body: RequestBody =
                    RequestBody.create(MediaType.parse("application/x-www-form-urlencoded"), strRequestBody)
            request = request.newBuilder().method(request.method(), body).build()

            response = chain.proceed(request)
        } catch (e: Exception) {
        }
        return response
    }

}




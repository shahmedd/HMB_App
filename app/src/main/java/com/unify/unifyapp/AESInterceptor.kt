
package com.unify.unifyapp
import android.util.Log
import com.unify.avanza.services.network.IUnifyInterceptor
import okhttp3.*
import okio.Buffer
import org.json.JSONObject
import java.io.IOException

object AESInterceptor : Interceptor, IUnifyInterceptor {
    lateinit var response:Response
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            var request: Request = chain.request()
            val requestBody: RequestBody = request.body()!!
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            val strRequestBody: String = buffer.readUtf8()
            Log.e("REQUEST BODY => ", strRequestBody)
            var jsonObject: JSONObject = JSONObject(strRequestBody)
            if (jsonObject.has("smsOtp")) {
                var smsOtp = jsonObject.get("smsOtp") as String
                smsOtp = AESEncrypter.getInstance().encrypt(smsOtp)
                jsonObject.put("smsOtp", smsOtp)

            }
            if (jsonObject.has("emailOtp")) {
                var emailOtp = jsonObject.get("emailOtp") as String
                emailOtp = AESEncrypter.getInstance().encrypt(emailOtp)
                jsonObject.put("emailOtp", emailOtp)

            }
            if (jsonObject.has("password")) {
                var password = jsonObject.get("password") as String
                password = AESEncrypter.getInstance().encrypt(password)
                jsonObject.put("smsOtp", password)
            }


            val body: RequestBody =
                    RequestBody.create(MediaType.parse("application/json"), jsonObject.toString())
            request = request.newBuilder().method(request.method(), body).build()

            response =  chain.proceed(request)
        } catch (e: Exception) {
        }
        return response
    }

}


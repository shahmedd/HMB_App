package com.unify.unifyapp;

import com.unify.avanza.services.network.IUnifyInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AESInterceptor implements Interceptor, IUnifyInterceptor {
    public static AESInterceptor obj = new AESInterceptor();

    private AESInterceptor() {
    }

    public static AESInterceptor getInstance() {
        return obj;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.header("aes", "aes value");
        return chain.proceed(requestBuilder.build());
    }
}

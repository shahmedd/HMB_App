package com.unify.unifyapp;

import com.unify.avanza.services.network.IUnifyInterceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CheckSumInterceptor implements Interceptor, IUnifyInterceptor {
    public static CheckSumInterceptor obj = new CheckSumInterceptor();

    private CheckSumInterceptor() {
    }

    public static CheckSumInterceptor getInstance() {
        return obj;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder requestBuilder = chain.request().newBuilder();
        requestBuilder.header("checksum", "checksum value");
        return chain.proceed(requestBuilder.build());
    }
}

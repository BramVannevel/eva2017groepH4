package com.projecten3.eva.Data;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * a helper interceptor class to add an auth header (with token) to calls that need it.
 */
public class AuthenticateHeaderInterceptor implements Interceptor {
    private String token = "JWT eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJfaWQiOiI1OThiMGYwMmI1ZTJlOTEzMDA3OTgyMGIiLCJuYW1lIjoiYWRtaW4iLCJwYXNzd29yZCI6IiQyYSQxMCQ0NVExZ2xkd1lYY1RHYkNDY2hEYlZ1RC8yem9ac3hGeGp5WlRxVTBpc1IyS2RoZWRsWXBhcSIsIl9fdiI6MCwicm9sZSI6ImFkbWluIn0.kMYphY1_fwlvCpBn4iFbl2p2DJLGQ9U9NvH27W34Cjg";
    public static AuthenticateHeaderInterceptor authHeaderInterceptorInstance = null;

    public void setToken(String token){
        this.token = token;
    }


    public AuthenticateHeaderInterceptor() {
        Log.i("Header","it goes in");
        Log.e("HEADER","token: " + token);
    }

    public static AuthenticateHeaderInterceptor getAuthHeaderInterceptorInstance(){
        if(authHeaderInterceptorInstance == null){
            authHeaderInterceptorInstance = new AuthenticateHeaderInterceptor();
        }

        return authHeaderInterceptorInstance;
    }
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Log.i("Header", token);

        Request request = chain.request()
                .newBuilder()
                .header("Authorization", token)
                .build();

        return chain.proceed(request);
    }
}
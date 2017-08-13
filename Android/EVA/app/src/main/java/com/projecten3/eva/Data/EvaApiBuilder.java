package com.projecten3.eva.Data;

import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * builder class to build the calls trough okhttp
 */
public class EvaApiBuilder {
    private static String BASE_URL = "https://evabeheer.herokuapp.com/";
    private static final String TAG = "EvaApiBuilder";
    private static ApiService apiService;

    /**
     * get a build instance of the apiservice so that it's possible to create the calls.
     * added an extra level of logging to see all the logs from okhttp for debugging purposes (loggin interceptor)
     * @return
     */
    public static ApiService getInstance() {
        if(apiService == null) {
            Log.i(TAG,"service == null");

            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);

            OkHttpClient client = new OkHttpClient.Builder()
                    .addInterceptor(AuthenticateHeaderInterceptor.getAuthHeaderInterceptorInstance())
                    .addInterceptor(logging)
                    .build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();

            apiService = retrofit.create(ApiService.class);
            return apiService;
        }else {
            Log.i(TAG,"service != null");
            return apiService;
        }
    }
}

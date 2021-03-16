package com.org.androidtools.retrofit;

import android.text.TextUtils;

import com.org.androidtools.network.NetworkService;
import com.org.androidtools.network.util.SSLSocketFactoryUtils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpMethod {
    private static volatile OkHttpClient client,hasTokenClient;
    private static volatile Retrofit retrofit;

    public static OkHttpClient getClient(){
        if (client == null){
            //log日志
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            //设置https协议访问
            client = new OkHttpClient.Builder()
                    //过滤器
                    .addInterceptor(new Interceptor() {
                        @Override
                        public Response intercept(Chain chain) throws IOException {
                            Request request = chain.request()
                                    .newBuilder()
                                    .addHeader("App-Version","1.0.0")
                                    .addHeader("App-Type","SYN_MOBIOSPORT")
                                    .addHeader("accept", "application/json")
                                    .build();
                            return chain.proceed(request);
                        }
                    })
                    .addInterceptor(loggingInterceptor)
                    .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(),SSLSocketFactoryUtils.createTrustAllManager())
                    .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                    .readTimeout(5, TimeUnit.MINUTES)
                    .writeTimeout(5,TimeUnit.MINUTES)
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .build();
        }
        return client;
    }

    public static Retrofit getInstance(){
        final String token = "";
        if (TextUtils.isEmpty(token.trim())){
            if (client == null){
                synchronized (HttpMethod.class){
                    getClient();
                }
            }

            retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkService.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(client)
                    .build();
        }else {
            if (hasTokenClient == null){
                synchronized (HttpMethod.class){
                    initTokenClient(token);
                }
            }
            retrofit = new Retrofit.Builder()
                    .baseUrl(NetworkService.BaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(hasTokenClient)
                    .build();
        }
        return retrofit;
    }

    public static void initTokenClient(final String token){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        hasTokenClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("App-Version","1.0.0")
                                .addHeader("App-Type","SYN_MOBIOSPORT")
                                .addHeader("accept", "application/json")
                                .addHeader("Authorization", token)
                                .build();
                        return chain.proceed(request);
                    }
                })
                .addInterceptor(loggingInterceptor)
                .sslSocketFactory(SSLSocketFactoryUtils.createSSLSocketFactory(), SSLSocketFactoryUtils.createTrustAllManager())
                .hostnameVerifier(new SSLSocketFactoryUtils.TrustAllHostnameVerifier())
                .readTimeout(5, TimeUnit.MINUTES)
                .writeTimeout(5, TimeUnit.MINUTES)
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();
    }

}

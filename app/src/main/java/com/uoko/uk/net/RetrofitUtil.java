/*
 * Copyright (C) 2016 david.wei (lighters)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.uoko.uk.net;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;


import com.uoko.uk.enity.request.ApiService;
import com.net.retrofit.converter.MyGsonConverterFactory;
import com.net.retrofit.proxy.ProxyHandler;
import com.net.retrofit.util.ContextUtils;
import com.net.retrofit.util.IGlobalManager;
import com.net.retrofit.util.NetWorkUtils;
import com.net.retrofit.util.SharedPreferencesHelper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Proxy;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by david on 16/8/19.
 * Email: huangdiv5@gmail.com
 * GitHub: https://github.com/alighters
 */
public class RetrofitUtil<T> implements IGlobalManager {

//    public static final String API = "https://www.baidu.com/";

    public static final String TAG = "GsonRequest";
    private static Retrofit sRetrofit;
    private static OkHttpClient sOkHttpClient;
    private static RetrofitUtil instance;
    private final static Object mRetrofitLock = new Object();
    static int DEFAULT_TIMEOUT = 20000;
    private static ApiService apiService;
    private static Retrofit getRetrofit() {
        if (sRetrofit == null) {
            synchronized (mRetrofitLock) {
                if (sRetrofit == null) {
                    OkHttpClient.Builder clientBuilder = new OkHttpClient().newBuilder();

                    File cacheFile = new File(ContextUtils.getContext().getCacheDir(), "cache");
                    Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb

                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                        @Override
                        public void log(String message) {
                            Log.d(TAG, "message = " + message);
                        }
                    });
                    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

                    clientBuilder.addInterceptor(httpLoggingInterceptor);
                    clientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                            .cache(cache)
                            .addInterceptor(httpLoggingInterceptor)
                            .addInterceptor(new HttpHeaderInterceptor())
                            .addNetworkInterceptor(new HttpCacheInterceptor())
                            .build();
                    sOkHttpClient = clientBuilder.build();
                    sRetrofit = new Retrofit.Builder().client(sOkHttpClient)
                            .baseUrl(ServerUrl.baseApi())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(MyGsonConverterFactory.create())
                            .build();
                }
            }
        }
        return sRetrofit;
    }

    public static RetrofitUtil getInstance() {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil();
                }
            }
        }
        return instance;
    }


    public static ApiService getAPi() {
        return apiService == null ? apiService = getInstance().getAPi(ApiService.class) : apiService;
    }


    /**
     * 初始化
     * @param context
     */

    public void init(Context context){
        ContextUtils.init(context);
    }

//    public static ApiService getAPi(){
//        return  getInstance().get(ApiService.class);
//    }

    public static <T> T getAPi(Class<T> tClass) {
        return getRetrofit().create(tClass);
    }

    @SuppressWarnings("unchecked")
    public <T> T getProxy(Class<T> tClass) {
        T t = getRetrofit().create(tClass);
        return (T) Proxy.newProxyInstance(tClass.getClassLoader(), new Class<?>[]{tClass}, new ProxyHandler(t, this));
    }

    @Override
    public void exitLogin() {
        // Cancel all the netWorkRequest
        sOkHttpClient.dispatcher().cancelAll();

        // Goto the home page
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {

            }
        });
    }

    //  添加请求头的拦截器
    private static class HttpHeaderInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            //  将token统一放到请求头
            String token= (String) SharedPreferencesHelper.get(ContextUtils.getContext(),"token","");
            //  也可以统一配置用户名
            String user_id="123456";
            Response originalResponse = chain.proceed(chain.request());
//            OrderBean orderBean = AppMemoryShared.findObject("_order");
            return originalResponse.newBuilder()
//                    .header("token", token)
//                    .header("user_id", user_id)
//                    .header("userName" , CrmApi.currentUserName())
//                    .header("id" , orderBean.getId())
                    .build();
        }
    }

    //  配置缓存的拦截器
    private static class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtils.isNetworkAvailable()) {  //没网强制从缓存读取
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (!NetWorkUtils.isNetworkAvailable()) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();

                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }
}

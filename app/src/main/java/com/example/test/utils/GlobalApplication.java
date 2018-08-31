package com.example.test.utils;

import android.app.Application;
import android.content.Context;

import com.example.test.utils.constant.Constant;
import com.example.test.utils.okhttps.HttpsUtils;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * 全局Application
 * Created by tsy on 16/7/15.
 */
public class GlobalApplication extends Application {

    private static GlobalApplication mGlobalApplication;
    private static Context mContext;
    private static Retrofit mRetrofit;

    @Override
    public void onCreate() {
        super.onCreate();

        mGlobalApplication = this;
        this.mContext = getApplicationContext();

        initRetrofit();

    }

    /**
     * 获取全局Application
     * @return
     */
    public static synchronized GlobalApplication getInstance() {
        return mGlobalApplication;
    }

    /**
     * 获取ApplicationContext
     * @return
     */
    public static Context getContext() {
        return mContext;
    }


    public static Retrofit getRetrofit() {
        return mRetrofit;
    }

    private void initRetrofit()
    {
        if(null == mRetrofit) {
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(Constant.baseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(HttpsUtils.getOKhttpsClient())
                    .build();
        }
    }

}

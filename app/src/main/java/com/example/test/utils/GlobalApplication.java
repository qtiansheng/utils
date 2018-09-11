package com.example.test.utils;

import android.app.Application;
import android.content.Context;

import com.example.test.utils.constant.Constant;
import com.example.test.utils.okhttps.HttpsUtils;
import com.wondertek.sdk.util.LogUtils;


import net.wequick.small.Small;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Properties;

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

    public GlobalApplication() {
        Small.preSetUp(this);
    }


    @Override
    public void onCreate() {
        super.onCreate();

        mGlobalApplication = this;
        this.mContext = getApplicationContext();

        initRetrofit();

        prop = new Properties();

        try {
            String config_file = "config.txt";
            InputStream in = getApplicationContext().getAssets().open(config_file);  //打开assets目录下的config.properties文件
            prop.load( new InputStreamReader(in,"utf-8"));
        } catch (Exception e1) {
            e1.printStackTrace();
        }

        setValue("1111","4444");
        LogUtils.d("111111111");

        try{
            InputStream is = getAssets().open("config.txt");
            int lenght = is.available();
            byte[]  buffer = new byte[lenght];
            is.read(buffer);
            is.close();
            String result = new String(buffer, "utf-8");
            result = result.trim();
            LogUtils.d(result);
        }catch(Exception e ){
            e.printStackTrace();
        }

        LogUtils.d("222222");
    }

    Properties prop = null;
    String pro_value = "";

    //读取
    private String getValue(String key){
        String value  = prop.getProperty(key);
        return value;
    }

    //修改
    private String setValue(String key,String value) {
        try {
            prop.setProperty (key,value);
            File file = new File("file:///android_asset/config.txt");
            OutputStream fos = new FileOutputStream(file);
            prop.store(fos, "Update '" + key + "' value");
            String abc = "12345";

            fos.flush();
            return value;
        } catch (Exception e1) {
            return null;
        } finally {

        }
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

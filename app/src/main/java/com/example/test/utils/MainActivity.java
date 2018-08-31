package com.example.test.utils;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.wondertek.sdk.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


public class MainActivity extends AppCompatActivity {

    private Activity mActivity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        String imgUrl = "http://120.210.214.22:8443/publish/clt//image/2/514/83.jpg";
        ImageView imageView = findViewById(R.id.test_img);
        GlideApp.with(mActivity).load(imgUrl).into(imageView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        LogUtils.d("retrofit ="  );

        Map<String, Object> inMap = new HashMap<String, Object>();
        inMap.put("token", "");
        inMap.put("celltowerId", "229199399");
        inMap.put("userCount", 2);
        List<Map<String, Object>> userInfoList = new ArrayList<Map<String, Object>>();
        Map<String, Object> userInfoListMap1 = new HashMap<String, Object>();
        userInfoListMap1.put("businessId", "1");
        userInfoListMap1.put("mobile", "13588755652");
        Map<String, Object> userInfoListMap2 = new HashMap<String, Object>();
        userInfoListMap2.put("businessId", "2");
        userInfoListMap2.put("mobile", "13588755653");
        userInfoList.add(userInfoListMap1);
        userInfoList.add(userInfoListMap2);
        inMap.put("userInfoList", userInfoList);

        LogUtils.d(buildRequestUrl("https://api-gateway.mecby.com/order/batchCheck", inMap));

        GitHubService service = GlobalApplication.getRetrofit().create(GitHubService.class);

        service.signNew_isSign(inMap)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody value) {

                        try {
                            LogUtils.d("onNext = " + value.string());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                            e.printStackTrace();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            LogUtils.d("click action_settings");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private static String buildRequestUrl(String url, Map<String, Object> params)  {
        for (String key : params.keySet()) {


            if (url.contains("?")) {
                url = url + "&" + key + "=" + params.get(key);
            } else {
                url = url + "?" + key + "=" + params.get(key);
            }
        }
        return url;
    }
}

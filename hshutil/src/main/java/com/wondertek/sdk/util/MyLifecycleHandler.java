package com.wondertek.sdk.util;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * 判断应用是否在前台运行
 *
 * Created by wondertek on 2018/08/24.
 */

/*在Application的onCreat()方法中注册：registerActivityLifecycleCallbacks(new MyLifecycleHandler());
使用： MyLifecycleHandler.isApplicationInForeground();// 判断应用是否在前台运行
*/

public class MyLifecycleHandler implements Application.ActivityLifecycleCallbacks {
    private static int resumed;
    private static int paused;
    private static int started;
    private static int stopped;

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
    }

    @Override
    public void onActivityStarted(Activity activity) {
        ++started;
    }

    @Override
    public void onActivityResumed(Activity activity) {
        ++resumed;
    }

    @Override
    public void onActivityPaused(Activity activity) {
        ++paused;
    }

    @Override
    public void onActivityStopped(Activity activity) {
        ++stopped;
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    }

    public static boolean isApplicationVisible() {
        return started > stopped;
    }

    public static boolean isApplicationInForeground() {
        // 当所有 Activity 的状态中处于 resumed 的大于 paused 状态的，即可认为有Activity处于前台状态中
        return resumed > paused;
    }
}
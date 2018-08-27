package com.wondertek.sdk.util;

import android.view.View;
import java.util.Calendar;

public abstract class NoDoubleClickListener implements View.OnClickListener {

    private long lastClickTime = 0;

    //点击事件间隔 这里设置不能超过多长时间
    public static final int MIN_CLICK_DELAY_TIME = 800;

    protected abstract void onNoDoubleClick(View v);
    @Override
    public void onClick(View v) {
        long currentTime = Calendar.getInstance().getTimeInMillis();
        if (currentTime - lastClickTime > MIN_CLICK_DELAY_TIME) {
            lastClickTime = currentTime;
            onNoDoubleClick(v);
        }
    }
}
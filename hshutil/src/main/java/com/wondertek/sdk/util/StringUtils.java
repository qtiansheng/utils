package com.wondertek.sdk.util;

/**
 * 字符串相关方法
 * Created by wondertek on 2018/08/24.
 */
public class StringUtils {

    /**
     * 是否为空
     * @param str 字符串
     * @return true 空 false 非空
     */
    public static Boolean isEmpty(String str) {
        if(str == null || str.equals("")) {
            return true;
        }

        return false;
    }
}

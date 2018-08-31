package com.example.test.utils;

/**
 * Created by qts on 2018/08/29.
 */

public class CommonResult<T> {
    public String retcode;
    public String retmsg;
    public String retdata;


    @Override
    public String toString() {
        return "Result{" +
                ", resultCode=" + retcode +
                ", downloadUrl=" + retmsg +
                ", version=" + retdata +
                '}';
    }
}
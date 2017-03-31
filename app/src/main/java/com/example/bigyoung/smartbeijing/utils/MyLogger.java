package com.example.bigyoung.smartbeijing.utils;

import android.util.Log;

/**
 * Created by BigYoung on 2017/3/29.
 */

public final class MyLogger {

    //开关
    private final static boolean flag = true;//true 测试                 false  上线

    public static void v(String tag,String msg){
        if(flag){
            Log.v(tag, msg);
        }
    }
    public static void d(String tag,String msg){
        if(flag){
            Log.d(tag, msg);
        }
    }
    public static void i(String tag,String msg){
        if(flag){
            Log.i(tag, msg);
        }
    }
    public static void w(String tag,String msg){
        if(flag){
            Log.w(tag, msg);
        }
    }
    public static void e(String tag,String msg){
        if(flag){
            Log.e(tag, msg);
        }
    }
}

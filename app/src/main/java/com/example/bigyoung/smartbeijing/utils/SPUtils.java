package com.example.bigyoung.smartbeijing.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by BigYoung on 2017/3/29.
 */

public class SPUtils {
    private final static  String NAME="smart_beijing";
    private static SharedPreferences preferences;
    private static SharedPreferences getPreferences(Context context) {
        if (preferences == null) {
            preferences = context.getSharedPreferences(NAME,
                    Context.MODE_PRIVATE);
        }
        return preferences;
    }
    public static void setBooleanValue(Context context, String key,boolean value){
        SharedPreferences sp=getPreferences(context);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key,value);
        editor.commit();
    }
    public static boolean getBooleanValue(Context context, String key,boolean defValue){
        SharedPreferences sp=getPreferences(context);
        return sp.getBoolean(key, defValue);
    }
}

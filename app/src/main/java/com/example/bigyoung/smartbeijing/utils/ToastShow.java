package com.example.bigyoung.smartbeijing.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.bigyoung.smartbeijing.activity.SplashActivity;

/**
 * Created by BigYoung on 2017/3/29.
 */

public class ToastShow {
    public static void showTextShortTime(Context context,String text ){
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show();
    }

}

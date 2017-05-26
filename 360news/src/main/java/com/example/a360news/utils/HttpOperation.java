package com.example.a360news.utils;

/**
 * Created by BigYoung on 2017/4/7.
 */

public class HttpOperation {
    public static String replaceUrl(String url){
        if(url!=null)
            url=url.replace("http://10.0.2.2:8080", AppConfig.HOST);
        return url;
    }
}

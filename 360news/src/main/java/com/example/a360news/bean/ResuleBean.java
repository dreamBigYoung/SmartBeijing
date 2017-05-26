package com.example.a360news.bean;

import java.util.List;

/**
 * Created by Apple on 2016/10/8.
 */
public class ResuleBean {
    public List<NewsData> data;
    public int error;

    public class NewsData{
        public int id;
        public List<String> imgs;
        public String title;
        public String weburl;
    }
}

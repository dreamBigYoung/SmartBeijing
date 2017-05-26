package com.example.a360news.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.a360news.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Apple on 2016/10/9.
 */
public class NewsDetailActivity extends Activity {

    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);

        //初始化WebView
        webView.setWebViewClient(new WebViewClient(){
            //页面加载完成
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                //给网页里面的img标签去添加点击事件
                addPictureClick();
            }
        });
        webView.setWebChromeClient(new WebChromeClient());
        webView.getSettings().setJavaScriptEnabled(true);

        String url = getIntent().getStringExtra("url");
        //加载网页
        webView.loadUrl(url);

        //传递对象给webview
        webView.addJavascriptInterface(new JsCallJava() {
            @JavascriptInterface
            @Override
            public void openImage(String src) {
                Intent intent = new Intent(getApplicationContext(),ShowActivity.class);
                intent.putExtra("url",src);
                startActivity(intent);
            }
        },"imagelistner");

    }

    private void addPictureClick() {
        //android调用js代码
        webView.loadUrl("javascript:(function(){"
                + "var objs = document.getElementsByTagName(\"img\"); "
                + "for(var i=0;i<objs.length;i++)  " + "{"
                + "    objs[i].onclick=function()  " + "   " + " {  "
                + "        window.imagelistner.openImage(this.src);  "
                + "    }  " + "}" + "})()");

    }

    public interface JsCallJava{
        public void openImage(String src);
    }

}

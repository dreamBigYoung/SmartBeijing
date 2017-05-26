package com.example.mywebtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.web_content)
    WebView webContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initwebContent();
    }

    /**
     * 初始化webView,完成显示必备设置
     */
    private void initwebContent() {
        //在webContent里面打开网页
        webContent.setWebViewClient(new WebViewClient());
        //允许在webContent里面弹出js的窗体
        webContent.setWebChromeClient(new WebChromeClient());
        //允许js的执行
        webContent.getSettings().setJavaScriptEnabled(true);
        //加载assets目录下的网页
        webContent.loadUrl("file:///android_asset/demo.html");
        //令js可引用java对象
        webContent.addJavascriptInterface(new HandJsCalling() {
            @Override
            public void beatIt() {
                Toast.makeText(MainActivity.this,"just beat it",Toast.LENGTH_SHORT).show();
            }
        },"bigyoung");
    }
    private interface HandJsCalling{
        public void beatIt();
    }
    public void javaCallJs(View v){
        webContent.loadUrl("javascript:muse()");
    }
}

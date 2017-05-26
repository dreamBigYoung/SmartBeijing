package com.example.a360news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.a360news.adapter.NewsAdapter;
import com.example.a360news.bean.ResuleBean;
import com.example.a360news.utils.AppConfig;
import com.example.a360news.view.RecycleViewDivider;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.rv_news)
    RecyclerView rvNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initRecycleView();
    }

    /**
     * 初始化RecycleView
     */
    private void initRecycleView() {
        //设置布局方式
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MainActivity.this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        rvNews.setLayoutManager(linearLayoutManager);
        //设置item分割线
        rvNews.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1, Color.BLACK));
        //获取服务器端数据
        //联网获取数据
        OkHttpUtils
                .get()
                .url(AppConfig.URL)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        Toast.makeText(MainActivity.this,"网络异常，获取数据失败",Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //将结果数据转换为javaBean对象
                        ResuleBean resuleBean = new Gson().fromJson(response, ResuleBean.class);
                        //设置适配器
                        rvNews.setAdapter(new NewsAdapter(resuleBean.data,getApplicationContext()));
                    }
                });

    }
    //create adapter

}

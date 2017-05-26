package com.example.talentvideoplayer;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.talentvideoplayer.adapter.MyVideoAdapter;
import com.example.talentvideoplayer.bean.VideoPlayerItemInfo;
import com.example.talentvideoplayer.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.video_list)
    RecyclerView videoList;
    private List<VideoPlayerItemInfo> videoPlayerItemInfoList;
    private MyVideoAdapter myVideoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initMyAdapter();
        initRecyclerView();
    }

    private void initMyAdapter() {
        myVideoAdapter=new MyVideoAdapter(MainActivity.this,videoPlayerItemInfoList);
    }

    private void initData() {
        //网络视频路径
        String url = "http://flv2.bn.netease.com/videolib3/1604/28/fVobI0704/SD/fVobI0704-mobile.mp4";
        //数据的初始化
        videoPlayerItemInfoList = new ArrayList<VideoPlayerItemInfo>();
        for (int i = 0; i < 10; i++) {
            videoPlayerItemInfoList.add(new VideoPlayerItemInfo(i,url));
        }
    }

    private void initRecyclerView() {
        //设置布局管理器
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        videoList.setLayoutManager(manager);
        //设置items分割线
        videoList.addItemDecoration(new RecycleViewDivider(this,LinearLayoutManager.HORIZONTAL,1, Color.BLACK));
        //加入适配器
        videoList.setAdapter(myVideoAdapter);
    }
}

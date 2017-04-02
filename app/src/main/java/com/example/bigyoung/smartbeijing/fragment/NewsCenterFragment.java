package com.example.bigyoung.smartbeijing.fragment;

import android.view.View;

import com.example.bigyoung.smartbeijing.base.HomeFragLoadData;
import com.example.bigyoung.smartbeijing.base.HomeFragmentBase;
import com.example.bigyoung.smartbeijing.bean.HomeNewsCenter.NewsCenterBean;
import com.example.bigyoung.smartbeijing.utils.AppConfig;
import com.example.bigyoung.smartbeijing.utils.ToastShow;
import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Call;

/**
 * Created by BigYoung on 2017/3/30.
 */

public class NewsCenterFragment extends HomeFragmentBase implements HomeFragLoadData {
    private final String titleText = "新闻中心";
    private OnConnectResposeListener myResposeListener;
    private OnMenuClickingListener menuClickingListener;

    @Override
    public void initTitle() {
        setTitleText(titleText);
        setIbMenuVisible(true);
        setIbPictypeVisible(false);
    }

    @Override
    public View createContent() {
        return null;
    }

    @Override
    public void loadNetData() {
        String newsUrl = AppConfig.NEWS_CENTER_URL;
        OkHttpUtils.get()
                .url(newsUrl)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastShow.showTextShortTime(getContext(), "网络异常，获取数据失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        //将json数据转为目标json对象
                        myResposeListener.processResponseData(response);
                    }
                });

    }

    public void setOnOnConnectResposeListener(OnConnectResposeListener listener) {
        this.myResposeListener = listener;
    }

    //处理返回数据的操作由调用者实现
    public interface OnConnectResposeListener {
        public void processResponseData(String respose);
    }

    @Override
    public void clickingMenuButton() {
        menuClickingListener.clickingMenuButton();
    }

    public void setOnMenuClickingListener(OnMenuClickingListener menuClickingListener) {
        this.menuClickingListener = menuClickingListener;
    }

    public interface OnMenuClickingListener {
        public void clickingMenuButton();
    }
}

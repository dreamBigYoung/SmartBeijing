package com.example.bigyoung.smartbeijing.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.example.bigyoung.smartbeijing.R;
import com.example.bigyoung.smartbeijing.adapter.NewsCenterTabVPAdapter;
import com.example.bigyoung.smartbeijing.base.HomeFragLoadData;
import com.example.bigyoung.smartbeijing.base.HomeFragmentBase;
import com.example.bigyoung.smartbeijing.base.NewsCenterContentTabPager;
import com.example.bigyoung.smartbeijing.bean.HomeNewsCenter.NewsCenterBean;
import com.example.bigyoung.smartbeijing.bean.HomeNewsCenter.NewsDataChild;
import com.example.bigyoung.smartbeijing.utils.AppConfig;
import com.example.bigyoung.smartbeijing.utils.ToastShow;
import com.google.gson.Gson;
import com.viewpagerindicator.TabPageIndicator;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import okhttp3.Call;

/**
 * Created by BigYoung on 2017/3/30.
 */

public class NewsCenterFragment extends HomeFragmentBase implements HomeFragLoadData {
    private final String titleText = "新闻中心";
    private OnConnectResposeListener myResposeListener;
    private OnMenuClickingListener menuClickingListener;
    private TabPageIndicator tabPageIndicator;
    private ImageButton ibNext;
    private ViewPager vpNewsCenterContent;

    private NewsCenterBean newsCenterBean;
    private ArrayList<NewsCenterContentTabPager> views;

    private int preTabPagePosition=0;

    @Override
    public void initTitle() {
        setTitleText(titleText);
        setIbMenuVisible(true);
        setIbPictypeVisible(false);
    }

    @Override
    public View createContent() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.newscenter_content, (ViewGroup) getView(), false);
        //获得view的引用
        tabPageIndicator = (TabPageIndicator) view.findViewById(R.id.tabPagerIndicator);
        ibNext = (ImageButton) view.findViewById(R.id.ib_next);
        vpNewsCenterContent = (ViewPager) view.findViewById(R.id.vp_newscenter_content);
        //设置点击事件
        //点击箭头，切换到下一页
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取ViewPager当前显示页面的下标
                int currentItem = vpNewsCenterContent.getCurrentItem();
                if (currentItem != (newsCenterBean.data.get(0)).children.size() - 1) {
                    vpNewsCenterContent.setCurrentItem(currentItem + 1);
                } else {
                    vpNewsCenterContent.setCurrentItem(0);
                }
            }
        });
        //初始化vpNewsCenterContent
        initViewPager();
        return view;
    }

    //初始化vpNewsCenterContent
    private void initViewPager() {
        //获取vpNewsCenterContent的内容
        views = new ArrayList<NewsCenterContentTabPager>();
        //获取每个item的数据
        for (NewsDataChild tabBean : newsCenterBean.data.get(0).children) {
            NewsCenterContentTabPager tabPager = new NewsCenterContentTabPager(getContext());
            views.add(tabPager);
        }
        //创建vpNewsCenterContent适配器
        NewsCenterTabVPAdapter adapter = new NewsCenterTabVPAdapter(views, newsCenterBean.data.get(0).children);
        //设置适配器
        vpNewsCenterContent.setAdapter(adapter);
        //让TabPagerIndicator和ViewPager进行联合
        tabPageIndicator.setViewPager(vpNewsCenterContent);

        //给ViewPager设置页面切换监听
            //注意：ViewPager和TabPagerIndicator配合使用，监听只能设置给TabPagerIndicator
        tabPageIndicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //当前的开始切换，其他的tab停止切换
            /*    for (int i = 0; i < views.size(); i++) {
                    NewsCenterContentTabPager tabPager = views.get(i);
                    if (position == i) {
                        //选中页
                        tabPager.startSwitch();
                    } else {
                        //未选中页
                        tabPager.stopSwitch();
                    }
                }*/
                //如果pager完成了切换
                if(preTabPagePosition!=position){
                    NewsCenterContentTabPager tabPager = views.get(preTabPagePosition);
                    //移除上一个线程轮播
                    tabPager.stopSwitch();
                    //开启新的线程轮播
                    tabPager = views.get(position);
                    tabPager.startSwitch();
                    preTabPagePosition=position;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        //让新闻中心第一个子tab的轮播图开始切换
        views.get(preTabPagePosition).startSwitch();
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
                        newsCenterBean = myResposeListener.processResponseData(response);
                        //remove all view contained in frameLayout
                        removerAllView();
                        addContainView(createContent());
                    }
                });

    }

    public void setOnOnConnectResposeListener(OnConnectResposeListener listener) {
        this.myResposeListener = listener;
    }

    //处理返回数据的操作由调用者实现
    public interface OnConnectResposeListener {
        public NewsCenterBean processResponseData(String respose);
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

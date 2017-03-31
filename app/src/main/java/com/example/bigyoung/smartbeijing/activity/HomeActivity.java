package com.example.bigyoung.smartbeijing.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.bigyoung.smartbeijing.R;
import com.example.bigyoung.smartbeijing.adapter.HomeFragmentAdapter;
import com.example.bigyoung.smartbeijing.base.HomeFragLoadData;
import com.example.bigyoung.smartbeijing.base.HomeFragmentBase;
import com.example.bigyoung.smartbeijing.bean.HomeNewsCenter.NewsCenterBean;
import com.example.bigyoung.smartbeijing.fragment.HomeFragment;
import com.example.bigyoung.smartbeijing.fragment.NewsCenterFragment;
import com.example.bigyoung.smartbeijing.view.HomeViewPage;
import com.google.gson.Gson;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/3/29.
 */

public class HomeActivity extends FragmentActivity implements NewsCenterFragment.OnConnectResposeListener {
    @BindView(R.id.rb_home)
    RadioButton rbHome;
    @BindView(R.id.rb_newscener)
    RadioButton rbNewscener;
    @BindView(R.id.rb_smartservice)
    RadioButton rbSmartservice;
    @BindView(R.id.rb_govaffairs)
    RadioButton rbGovaffairs;
    @BindView(R.id.rb_setting)
    RadioButton rbSetting;
    @BindView(R.id.rg_tab)
    RadioGroup rgTab;
    @BindView(R.id.vp_home)
    HomeViewPage vpHome;
    private SlidingMenu slidingMenu;
    private HomeFragmentAdapter frgAdapter;
    private List<Fragment> frgList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        initSlidingMenu();
        initVpHome();
        initRaidoGroup();
    }

    private void initVpHome() {
        frgList = new ArrayList<Fragment>();
        frgList.add(new HomeFragment());
        NewsCenterFragment newsCenterFragment = new NewsCenterFragment();
        newsCenterFragment.setOnOnConnectResposeListener(HomeActivity.this);
        frgList.add(newsCenterFragment);
        frgAdapter = new HomeFragmentAdapter(getSupportFragmentManager(), frgList);
        vpHome.setAdapter(frgAdapter);

    }

    private void initRaidoGroup() {
        //
        rgTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int position = 0;
                switch (checkedId) {
                    case R.id.rb_home:
                        position = 0;
                        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
                        break;
                    case R.id.rb_newscener:
                        position = 1;
                        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
                        break;
                }
                vpHome.setCurrentItem(position, false);
                //更新网路数据
                HomeFragLoadData frag = (HomeFragLoadData) frgAdapter.getItem(position);
                frag.loadNetData();
            }
        });
    }

    private void initSlidingMenu() {
        //创建侧滑菜单对象
        slidingMenu = new SlidingMenu(HomeActivity.this);
        //设置由左边划出
        slidingMenu.setMode(SlidingMenu.LEFT);
        //Denies the SlidingMenu to be opened with a swipe gesture
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_NONE);
        //设置slipMenu划出后，主页面的宽度
        slidingMenu.setBehindOffset(300);
        /**
         * SLIDING_WINDOW will include the Title/ActionBar in the content
         * section of the SlidingMenu, while SLIDING_CONTENT does not.
         */
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);
        //设置slidingMenu的布局
        slidingMenu.setMenu(R.layout.slip_menu);
    }

    @Override
    public void processResponseData(String respose) {
        Gson gson = new Gson();
        NewsCenterBean newsCenterBean = gson.fromJson(respose, NewsCenterBean.class);
    }
}

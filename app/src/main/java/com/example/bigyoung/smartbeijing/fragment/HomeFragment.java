package com.example.bigyoung.smartbeijing.fragment;

import android.view.View;

import com.example.bigyoung.smartbeijing.base.HomeFragLoadData;
import com.example.bigyoung.smartbeijing.base.HomeFragmentBase;

/**
 * Created by BigYoung on 2017/3/30.
 */

public class HomeFragment extends HomeFragmentBase implements HomeFragLoadData {
    private final String titleText = "首页";

    @Override
    public void initTitle() {
        setTitleText(titleText);
        setIbMenuVisible(false);
        setIbPictypeVisible(false);
    }

    @Override
    public View createContent() {
        return null;
    }

    @Override
    public void loadNetData() {

    }
}

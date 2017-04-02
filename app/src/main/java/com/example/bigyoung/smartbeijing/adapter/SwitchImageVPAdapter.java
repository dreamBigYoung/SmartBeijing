package com.example.bigyoung.smartbeijing.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.bigyoung.smartbeijing.bean.HomeNewsCenter.NewsCenterTabBean;

import java.util.List;

/**
 * Created by ywf on 2016/11/12.
 */
public class SwitchImageVPAdapter extends PagerAdapter {
    private List<ImageView> imageViews;
    private List<NewsCenterTabBean.TopNewsBean> topNewsBeanList;



    public SwitchImageVPAdapter(List<ImageView> imageViews, List<NewsCenterTabBean.TopNewsBean> topNewsBeanList) {
        this.imageViews = imageViews;
        this.topNewsBeanList = topNewsBeanList;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView iv = imageViews.get(position);
        container.addView(iv);
        return iv;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}

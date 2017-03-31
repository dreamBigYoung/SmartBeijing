package com.example.bigyoung.smartbeijing.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by BigYoung on 2017/3/29.
 */

public class GuiderAdapter extends PagerAdapter {
    List<ImageView> imgList;

    public GuiderAdapter(List<ImageView> imgList) {
        this.imgList = imgList;
    }

    @Override
    public int getCount() {
//        if (imgList != null)
//            return imgList.size();
        return Integer.MAX_VALUE;
//        return 0;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        if (view == object)
            return true;
        return false;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        if (imgList != null) {

            ImageView imageView = imgList.get(position);
            container.addView(imageView);
            return imageView;
        }
        return super.instantiateItem(container, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (imgList != null) {
            ImageView imageView = imgList.get(position);
            container.removeView(imageView);
        } else
            super.destroyItem(container, position, object);
    }
}

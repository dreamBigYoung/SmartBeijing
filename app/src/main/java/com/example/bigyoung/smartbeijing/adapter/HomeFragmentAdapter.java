package com.example.bigyoung.smartbeijing.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by BigYoung on 2017/3/30.
 */

public class HomeFragmentAdapter extends FragmentPagerAdapter {
    private List<Fragment> frgList;

    public HomeFragmentAdapter(FragmentManager fm, List<Fragment> frgList) {
        super(fm);
        this.frgList = frgList;
    }

    @Override
    public Fragment getItem(int position) {
        if (frgList != null)
            return frgList.get(position);
        return null;
    }

    @Override
    public int getCount() {
        if (frgList != null)
            return frgList.size();
        return 0;
    }
}

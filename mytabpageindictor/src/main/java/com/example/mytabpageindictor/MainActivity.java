package com.example.mytabpageindictor;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity implements ViewPager.OnPageChangeListener {

    @BindView(R.id.page_indicator)
    TabPageIndicator pageIndicator;
    @BindView(R.id.vp)
    ViewPager vp;
    public String[] myTtiles={"miko","penta","manako"};
    public int[] beautys={R.drawable.beauty_1,R.drawable.beauty_2,R.drawable.beauty_3};
    public List<ImageView> imgList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initData();
        vp.setAdapter(new MyViewPagerAdapter());
        pageIndicator.setViewPager(vp);
        pageIndicator.setOnPageChangeListener(this);


        vp.setCurrentItem(0,true);
    }

    private void initData() {
        imgList=new ArrayList<ImageView>();
        ImageView iv=null;
        for(int i=0;i<beautys.length;i++){
            iv=new ImageView(MainActivity.this);
            iv.setImageResource(beautys[i]);
            imgList.add(iv);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Toast.makeText(MainActivity.this,myTtiles[position],Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public class MyViewPagerAdapter extends PagerAdapter{

        @Override
        public int getCount() {
            return myTtiles.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView iv=imgList.get(position);

            container.addView(iv);
            return iv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((ImageView)object);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return myTtiles[position];
        }
    }
}

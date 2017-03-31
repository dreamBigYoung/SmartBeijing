package com.example.bigyoung.smartbeijing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.bigyoung.smartbeijing.R;
import com.example.bigyoung.smartbeijing.adapter.GuiderAdapter;
import com.example.bigyoung.smartbeijing.utils.MyLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/3/29.
 */

public class GuideActivity extends Activity implements View.OnClickListener {
    @BindView(R.id.container_guidedot)
    LinearLayout containerGuidedot;
    @BindView(R.id.current_dot)
    View currentDot;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.leave_button)
    Button leaveButton;

    //向导图片
    private int[] imgs = new int[]{R.drawable.beauty_1, R.drawable.beauty_2, R.drawable.beauty_3};
    //
    private int DOT_MARGIN_RIGHT = 20;
    private int DOT_WIDTH = 10;
    private int dot_width = 0;
    private GuiderAdapter guideAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //去除标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //去除状态栏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_guide);
        ButterKnife.bind(this);
        initVp();
        initGuideDot();
        initButton();
    }

    private void initButton() {
        leaveButton.setOnClickListener(GuideActivity.this);
    }

    private void initGuideDot() {
        //填充greyDot
        if (imgs != null) {
            int len = imgs.length;
            //View view=null;
            for (int i = 0; i < len; i++) {
                View view = new View(GuideActivity.this);
                view.setBackgroundResource(R.drawable.point_gray_bg);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
                params.leftMargin = DOT_MARGIN_RIGHT;
                view.setLayoutParams(params);
                containerGuidedot.addView(view);
            }
        }
    }

    private void initVp() {
        List<ImageView> imgList = new ArrayList<ImageView>();
        for (int imgId : imgs) {
            imgList.add(getImageView(imgId));
        }
        guideAdapter = new GuiderAdapter(imgList);
        vp.setAdapter(guideAdapter);
        //add scrolling listenr
        vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //capture percent
                MyLogger.w("GuideActivity", position + " " + " " + positionOffset + " " + positionOffsetPixels);
                int startPos = DOT_MARGIN_RIGHT;

                if (dot_width == 0)
                    if (containerGuidedot != null && containerGuidedot.getChildCount() > 1) {
                        dot_width = containerGuidedot.getChildAt(1).getLeft() - containerGuidedot.getChildAt(0).getLeft();
                    }
                int greyOffset = (int) (position * dot_width + positionOffset * dot_width);
                //重置current_dot的位置
                RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(10, 10);
                params.leftMargin = startPos + greyOffset;
                currentDot.setLayoutParams(params);
            }

            @Override
            public void onPageSelected(int position) {
               int maxIndex=guideAdapter.getCount()-1;

                if (position == maxIndex) {
                    //显示button
                    leaveButton.setVisibility(View.VISIBLE);
                } else {
                    //隐藏button
                    leaveButton.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private ImageView getImageView(int imgId) {
        ImageView iv = new ImageView(GuideActivity.this);
        iv.setBackgroundResource(imgId);
        return iv;
    }

    @Override
    public void onClick(View v) {
        Intent it=new Intent(GuideActivity.this,HomeActivity.class);
        startActivity(it);
        finish();
    }
}

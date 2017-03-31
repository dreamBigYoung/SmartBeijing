package com.example.bigyoung.smartbeijing.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bigyoung.smartbeijing.R;
import com.example.bigyoung.smartbeijing.utils.AppConfig;
import com.example.bigyoung.smartbeijing.utils.SPUtils;
import com.example.bigyoung.smartbeijing.utils.ToastShow;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/3/29.
 */

public class SplashActivity extends Activity implements Animation.AnimationListener {

    @BindView(R.id.magic_img)
    ImageView magicImg;

    Handler mHandler=new Handler();

    private final int DELAY_TIME=2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        //start animation
        Animation anima=getAnimationSet();
        //start animation listener
        anima.setAnimationListener(SplashActivity.this);
        magicImg.startAnimation(anima);
    }
    //create set of animation
    public Animation getAnimationSet() {
        AnimationSet aniSet = new AnimationSet(false);
        //
        RotateAnimation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setRepeatCount(0);
        rotate.setDuration(2000);
        //缩放
        ScaleAnimation scale = new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(2000);
        //
        AlphaAnimation alpha=new AlphaAnimation(0,1);
        alpha.setDuration(2000);

        aniSet.addAnimation(rotate);
        aniSet.addAnimation(scale);
        aniSet.addAnimation(alpha);

        return aniSet;
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                boolean result=SPUtils.getBooleanValue(SplashActivity.this, AppConfig.FIRST_OPEN,true);
                Intent it=null;
                Class cc=null;
                if(result==false){
                    cc=HomeActivity.class;
                }else{
                    cc=GuideActivity.class;
                    SPUtils.setBooleanValue(SplashActivity.this, AppConfig.FIRST_OPEN,false);
                }
                it=new Intent(SplashActivity.this,cc);
                startActivity(it);
                SplashActivity.this.finish();
            }

        },DELAY_TIME);
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}

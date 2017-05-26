package com.example.testmultiadapter.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by BigYoung on 2017/4/9.
 */

public class MyRecycleView extends RecyclerView {
    //记录本次按下的初始点
    private float downX;
    private float downY;
    //移动过程中的最新值
    private float moveY;
    private float moveX;
    //本次滑动viewHeadSecond.top-recycle.top
    private float disY;

    private NotificationView sliplistener;

    private LinearLayoutManager layoutManager;
    //headView的首个布局
    private View firstHeadView;
    //headView的第二个布局
    private View viewHeadSecond;
    //headView首个布局的完全高度
    private int mFirstViewHeight;

    public MyRecycleView(Context context) {
        this(context, null);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyRecycleView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    @Override
    public void setLayoutManager(LayoutManager layout) {
        super.setLayoutManager(layout);
        this.layoutManager = (LinearLayoutManager) layout;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                downY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                moveX = ev.getX();
                moveY = ev.getY();
                //disY = moveY - downY;
                if(layoutManager.findFirstVisibleItemPosition()!=0||headDistance()<0){
                    downY=moveY;
                }
                disY = moveY - downY;
                sliplistener.moveFreash(disY);
                break;
            case MotionEvent.ACTION_UP:
                sliplistener.slipFinish(disY);
                break;
        }

        return super.dispatchTouchEvent(ev);
    }

    public void setNotificationView(NotificationView listener) {
        this.sliplistener = listener;
    }

    /**
     * 传递首个item的两个view
     * @param firstHeadView
     * @param viewHeadSecond
     */
    public void setHeadView(View firstHeadView,View viewHeadSecond){
        this.firstHeadView=firstHeadView;
        this.viewHeadSecond=viewHeadSecond;
    }
    public void setFirstHeadViewHeight(int mFirstViewHeight){
        this.mFirstViewHeight=mFirstViewHeight;
    }

    /**
     *viewHeadSecond与recycle的top值的差
     * @return
     */
    public int headDistance(){
        int[] location1=new int[2];
        int[] location2=new int[2];
        this.getLocationInWindow(location1);
        viewHeadSecond.getLocationInWindow(location2);
        return location2[1]-location1[1];
    }

    /**
     * 一次移动所过程中的view操作
     */
    public interface NotificationView {
        public void moveFreash(float disY);

        /**
         *
         * @param disY viewHeadSecond.top-recycle.top
         */
        public void slipFinish(float disY);
    }
}

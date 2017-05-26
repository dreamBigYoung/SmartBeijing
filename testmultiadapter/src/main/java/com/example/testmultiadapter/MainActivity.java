package com.example.testmultiadapter;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.testmultiadapter.view.MyRecycleView;
import com.example.testmultiadapter.view.RecycleViewDivider;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.test_adapter)
    MyRecycleView testAdapter;

    private List<String> posList;

    private XWrapAdapter wrapAdapter;
    private int mFirstViewHeight;
    //headView的首个布局
    private View firstHeadView;
    //headView的第二个布局
    private View viewHeadSecond;
    private LinearLayoutManager manager;
    private TextView headTv;
    private ImageView imgRing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initData();
        initAdapter();
        initRecycle();
    }

    private void initRecycle() {
        manager = new LinearLayoutManager(MainActivity.this);
        testAdapter.setLayoutManager(manager);
        //设置item分割线
        testAdapter.addItemDecoration(new RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 1, Color.BLACK));
        testAdapter.setAdapter(wrapAdapter);
        //添加headView
        testAdapter.setHeadView(firstHeadView, viewHeadSecond);
        //添加滑动监听
        testAdapter.setNotificationView(new MyRecycleView.NotificationView() {

            @Override
            public void moveFreash(float disY) {
                int[] location1 = new int[2];
                int[] location2 = new int[2];
                testAdapter.getLocationInWindow(location1);
                viewHeadSecond.getLocationInWindow(location2);
                Log.wtf("two different location :", location1[1] + "____" + location2[1] + "__firstVisibleItemPosition:" + manager.findFirstVisibleItemPosition());
                if (disY > 0 && disY < mFirstViewHeight) {
                    //显示隐藏内容
                    int newTopPadding = -mFirstViewHeight + (int) disY;
                    firstHeadView.setPadding(0, newTopPadding > 0 ? 0 : newTopPadding, 0, 0);
                    //更换firstHeadView的内容
                    headTv.setText("下拉刷新");
                } else {
                    if (disY >= mFirstViewHeight) {
                        //更换firstHeadView的内容
                        headTv.setText("释放刷新");
                    }
                }
            }

            @Override
            public void slipFinish(float disY) {
                if (disY < mFirstViewHeight && disY > 0)
                    firstHeadView.setPadding(0, -mFirstViewHeight, 0, 0);
                else{
                    if(disY>=mFirstViewHeight){
                        Animation animationImgRing = createAnimationImgRing();
                        imgRing.startAnimation(animationImgRing);
                        animationImgRing.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                firstHeadView.setPadding(0, -mFirstViewHeight, 0, 0);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                }

            }
        });

    }

    private void initData() {
        posList = new ArrayList<String>();
        for (int i = 1; i < 11; i++) {
            posList.add(i + "");
        }
    }

    private void initAdapter() {
        ViewGroup viewHead = (ViewGroup) LinearLayout.inflate(MainActivity.this, R.layout.item_head, null);
        wrapAdapter = new XWrapAdapter(MainActivity.this, posList, viewHead);
        viewHeadSecond = LinearLayout.inflate(MainActivity.this, R.layout.item_second_head, null);
        wrapAdapter.updateHeadView(viewHeadSecond);
        //隐藏头布局部分控件
        firstHeadView = viewHead.findViewById(R.id.first_layout);
        headTv = (TextView) firstHeadView.findViewById(R.id.head_tv);
        imgRing =(ImageView) firstHeadView.findViewById(R.id.img_ring);
        //测量默认头的高度
        firstHeadView.measure(0, 0);
        //获取测量后的高度
        mFirstViewHeight = firstHeadView.getMeasuredHeight();
        //隐藏头
        firstHeadView.setPadding(0, -mFirstViewHeight, 0, 0);
    }

    //从下拉刷新切换到释放刷新的动画
    private Animation createAnimationImgRing() {
        Animation animation = new RotateAnimation(0,-360,Animation.RELATIVE_TO_SELF,0.5f,Animation.RELATIVE_TO_SELF,0.5f);
        animation.setDuration(200);
        animation.setRepeatCount(5);
        animation.setFillAfter(true);
        return animation;
    }

    public class XWrapAdapter extends RecyclerView.Adapter {
        public final int LIST_BODY = 1;
        public final int LIST_HEAD = 0;
        public final int LIST_FOOTER = 2;
        private Context context;
        private List<String> dataList;

        private ViewGroup defaultHead;

        private XWrapAdapter(Context context, List<String> dataList, ViewGroup defaultHead) {
            this.context = context;
            this.dataList = dataList;
            this.defaultHead = defaultHead;
        }

        /**
         * 更新头布局内容
         *
         * @param headView
         */
        public void updateHeadView(View headView) {
            if (defaultHead.getChildCount() == 2 && headView != null) {
                //remove old
                defaultHead.removeViewAt(1);
                //add new
                defaultHead.addView(headView);
            } else {
                if (headView != null) {
                    //add new
                    defaultHead.addView(headView);
                }
            }

        }

        @Override
        public int getItemViewType(int position) {
            if (position == 0) {
                return LIST_HEAD;
            } else {
                if (position == getItemCount() - 1) {
                    return LIST_FOOTER;
                }
            }
            return LIST_BODY;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            switch (viewType) {
                case LIST_HEAD:
                    return new HeadViewHolder(defaultHead);
                case LIST_BODY:
                    View viewBody = LinearLayout.inflate(MainActivity.this, R.layout.item_body, null);
                    return new BodyViewHolder(viewBody);
                case LIST_FOOTER:
                    View viewFooter = LinearLayout.inflate(MainActivity.this, R.layout.item_footer, null);
                    return new FooterViewHolder(viewFooter);
                default:
                    return null;
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            switch (viewType) {
                case LIST_HEAD:
                 /*   HeadViewHolder head = (HeadViewHolder) holder;
                    head.headTv.setText("i am head");*/
                    break;
                case LIST_BODY:
                    BodyViewHolder body = (BodyViewHolder) holder;
                    body.tvBody.setText(dataList.get(position - 1));
                    break;
                case LIST_FOOTER:
                    FooterViewHolder foot = (FooterViewHolder) holder;
                    foot.footTv.setText("i am foot");
                    break;
                default:
                    ;
            }
        }

        @Override
        public int getItemCount() {
            if (dataList != null)
                return dataList.size() + 2;
            else
                return 0;
        }

        class HeadViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.head_tv)
            TextView headTv;

            HeadViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        class BodyViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.tv_body)
            TextView tvBody;

            BodyViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }

        class FooterViewHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.foot_tv)
            TextView footTv;

            FooterViewHolder(View view) {
                super(view);
                ButterKnife.bind(this, view);
            }
        }
    }
}

package com.example.bigyoung.smartbeijing.base;

import android.content.Context;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bigyoung.smartbeijing.R;
import com.example.bigyoung.smartbeijing.adapter.SwitchImageVPAdapter;
import com.example.bigyoung.smartbeijing.bean.HomeNewsCenter.NewsCenterTabBean;
import com.example.bigyoung.smartbeijing.utils.MyLogger;
import com.example.bigyoung.smartbeijing.utils.ToastShow;
import com.example.bigyoung.smartbeijing.view.SwitchImageViewViewPager;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by ywf on 2016/11/12.
 */
public class NewsCenterContentTabPager implements ViewPager.OnPageChangeListener {


    private static final String TAG = "NewsCenterContentTabPager";
    private final Context context;
    public final View view;
    private NewsCenterTabBean newsCenterTabBean;
    private ArrayList<ImageView> imageViews;

    @BindView(R.id.vp_switch_image)
    SwitchImageViewViewPager vpSwitchImage;

    @BindView(R.id.tv_title)
    TextView tvTitle;

    @BindView(R.id.ll_point_container)
    LinearLayout llPointContainer;

    //处理轮播图的自动切换  （消息机制）
    private Handler mHandler = new Handler();
    //判断是否在切换
    private boolean hasSwitch;

    public NewsCenterContentTabPager(Context context) {
        this.context = context;
        view = initView();
    }

    //加载布局
    private View initView() {
        View view = LayoutInflater.from(context).inflate(R.layout.newscenter_content_tab, null);
        ButterKnife.bind(this, view);
        return view;
    }

    //网络加载数据
    public void loadNetData(String url) {
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e, int id) {
                        ToastShow.showTextShortTime(context, "加载数据失败");
                    }

                    @Override
                    public void onResponse(String response, int id) {
                        MyLogger.i(TAG, response);
                        //解析数据
                        processData(response);
                    }
                });
    }

    //把json字符串转换成对应的数据模型
    public void processData(String json) {
        Gson gson = new Gson();
        newsCenterTabBean = gson.fromJson(json, NewsCenterTabBean.class);

        //把数据绑定给对应的控件
        bindDataToView();

        //把当前的NewsCenterContentTabPager对象传递给SwitchImageViewViewPager
        vpSwitchImage.setTabPager(this);

    }

    //绑定数据给控件
    private void bindDataToView() {
        initSwitchImageView();
        initPoint();
    }

    //加载图片，并绑定到Viewpager上
    private void initSwitchImageView() {
        imageViews = new ArrayList<>();
        int size = newsCenterTabBean.data.topnews.size();
        for (int i = -1; i < size+1; i++) {
            NewsCenterTabBean.TopNewsBean topNewsBean = null;
            if(i == -1){//
                //添加最后的一张图片
                topNewsBean = newsCenterTabBean.data.topnews.get(size-1);
            }else if(i == size){
                //添加第一张图片
                topNewsBean =  newsCenterTabBean.data.topnews.get(0);
            }else{
                topNewsBean = newsCenterTabBean.data.topnews.get(i);
            }
            ImageView iv = new ImageView(context);
            Picasso.with(context).load(fixImagUri(topNewsBean.topimage)).into(iv);
            imageViews.add(iv);
        }
        //创建轮播图适配器
        SwitchImageVPAdapter adapter = new SwitchImageVPAdapter(imageViews, newsCenterTabBean.data.topnews);
        //绑定ViewPager
        vpSwitchImage.setAdapter(adapter);

        //设置轮播图的文字显示
        tvTitle.setText(newsCenterTabBean.data.topnews.get(0).title);
        //给轮播图设置滑动监听
        vpSwitchImage.addOnPageChangeListener(this);

    }

    //初始化点
    private void initPoint() {
        //清空容器里面的布局
        llPointContainer.removeAllViews();
        for (int i = 0; i < newsCenterTabBean.data.topnews.size(); i++) {
            //小圆点
            View view = new View(context);
            //设置背景颜色
            view.setBackgroundResource(R.drawable.point_gray_bg);
            //布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
            //右边距
            params.rightMargin = 10;
            //添加布局
            llPointContainer.addView(view,params);
        }
        //让第一个点的背景为红色
        llPointContainer.getChildAt(0).setBackgroundResource(R.drawable.point_red_bg);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

        //修正下标
        int pageIndex = 0;
        //正确数据的大小
        int size = newsCenterTabBean.data.topnews.size();

        if(position == 0){
            pageIndex = size - 1;
            //切换到最后一个页面
            vpSwitchImage.setCurrentItem(size,false);
        }else if(position == size + 1){
            pageIndex = 0;
            //切换到第一个页面
            vpSwitchImage.setCurrentItem(1,false);
        }else{
            pageIndex = position - 1;
        }




        //设置轮播图的文字显示
        tvTitle.setText(newsCenterTabBean.data.topnews.get(pageIndex).title);

        //修改轮播图点的背景
        int childCount = llPointContainer.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = llPointContainer.getChildAt(i);
            if(pageIndex == i){
                //选中的页面
                child.setBackgroundResource(R.drawable.point_red_bg);
            }else{
                //未选中的页面
                child.setBackgroundResource(R.drawable.point_gray_bg);
            }
        }

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    //切换的任务
    private class SwitchTask implements  Runnable{

        @Override
        public void run() {
            if(vpSwitchImage != null){
                //切换逻辑
                int currentItem = vpSwitchImage.getCurrentItem();
                //判断是否在了最后一页
                if(currentItem == newsCenterTabBean.data.topnews.size() - 1){
                    currentItem = 0;
                }else{
                    currentItem++;
                }
                vpSwitchImage.setCurrentItem(currentItem);
            }
            mHandler.postDelayed(this,3000);
        }
    }

    //开始切换
    public void startSwitch(){
        //注意：如果轮播图未切换轮播，开始发送消失进行，否则反之
        if(!hasSwitch){
            //往Handler里面的消息队列里面发送一个延时的消息
            mHandler.postDelayed(new SwitchTask(),3000);
        }
    }

    //停止切换
    public void stopSwitch(){
        //清空消息队列
        mHandler.removeCallbacksAndMessages(null);
        hasSwitch = false;
    }
    public String fixImagUri(String url){
        String newUrl=null;
        if(url!=null){
            if(url.contains("10.0.2.2:8080")){
                newUrl=url.replace("10.0.2.2:8080","49.74.222.225:8080");
            }else{
                newUrl=url;
            }
        }
        return newUrl;
    }
}

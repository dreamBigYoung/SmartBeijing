package com.example.bigyoung.smartbeijing.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.bigyoung.smartbeijing.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by BigYoung on 2017/3/30.
 */

public abstract class HomeFragmentBase extends Fragment {
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ib_pic_type)
    ImageButton ibPicType;
    @BindView(R.id.frame_contain)
    FrameLayout frame_contain;
    Unbinder unbinder;
    @BindView(R.id.ib_menu)
    ImageButton ibMenu;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_base, container, false);
        unbinder = ButterKnife.bind(this, view);
        initEvent();
        return view;
    }

    private void initEvent() {
        ibMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickingMenuButton();
            }
        });
    }

    /**
     * 处理clickingMenu的点击事件
     */
    public void clickingMenuButton() {

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initTitle();
    }

    /**
     * 初始化fragment的title
     */
    public abstract void initTitle();

    public void setTitleText(String titleText) {
        tvTitle.setText(titleText);
    }

    public void setIbMenuVisible(boolean visibility) {
        ibMenu.setVisibility(visibility == true ? View.VISIBLE : View.GONE);
    }

    public void setIbPictypeVisible(boolean visibility) {
        ibPicType.setVisibility(visibility == true ? View.VISIBLE : View.GONE);
    }

    public void removeContainView(View view) {
        frame_contain.removeView(view);
    }
    public void removerAllView(){
        frame_contain.removeAllViews();
    }
    public void addContainView(View view) {
        frame_contain.addView(view);
    }

    /**
     * create view for frameLayout fraContain
     *
     * @return
     */
    public abstract View createContent();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}

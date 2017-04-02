package com.example.bigyoung.smartbeijing.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bigyoung.smartbeijing.R;
import com.example.bigyoung.smartbeijing.bean.HomeNewsCenter.NewsData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/4/2.
 */

public class SlipMenuAdapter extends RecyclerView.Adapter {

    private Context context;

    private List<NewsData> newsDataslist;

    private int defSelected;//默认被选中的item

    public SlipMenuAdapter(Context context) {
        this(context,null);
    }

    public SlipMenuAdapter(Context context, List<NewsData> newsDataslist) {
        this.context = context;
        this.newsDataslist=newsDataslist;
    }

    /**
     * 创造持有item_view引用的view_holder
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_slip_menu, parent, false);
        //view反射的结果是不变的，可以使用ButterKnife
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    /**
     *
     * @param holder 填充内容至holder持有的元素
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        NewsData newsData = newsDataslist.get(position);
        ViewHolder myHolder= (ViewHolder) holder;
        if(position==defSelected){//是否为默认选中items
            myHolder.itemText.setTextColor(Color.RED);
            myHolder.ivArrow.setImageResource(R.drawable.menu_arr_select);
        }else{
            //因为ViewHolder复用的原因
            myHolder.ivArrow.setImageResource(R.drawable.menu_arr_normal);
            myHolder.itemText.setTextColor(Color.WHITE);
        }
        myHolder.itemText.setText(newsData.title);
    }

    @Override
    public int getItemCount() {
        if(newsDataslist!=null)
            return  newsDataslist.size();
        return 0;
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.iv_arrow)
        ImageView ivArrow;
        @BindView(R.id.item_text)
        TextView itemText;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public void setNewsDataslist(List<NewsData> newsDataslist) {
        this.newsDataslist = newsDataslist;
    }
}

package com.example.talentvideoplayer.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.talentvideoplayer.R;
import com.example.talentvideoplayer.bean.VideoPlayerItemInfo;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by BigYoung on 2017/4/7.
 */

public class MyVideoAdapter extends RecyclerView.Adapter {
    private final List<VideoPlayerItemInfo> itemList;
    private final Context context;

    public MyVideoAdapter(Context context, List<VideoPlayerItemInfo> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_show, null);
        RecyclerView.ViewHolder viewHolder=new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder myHolder=(ViewHolder) holder;
    }

    @Override
    public int getItemCount() {
        return itemList==null?0:itemList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.show_content)
        FrameLayout showContent;
        @BindView(R.id.iv_author)
        ImageView ivAuthor;
        @BindView(R.id.tv_author_name)
        TextView tvAuthorName;
        @BindView(R.id.tv_play_count)
        TextView tvPlayCount;
        @BindView(R.id.iv_comment)
        ImageView ivComment;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.iv_comment_more)
        ImageView ivCommentMore;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}

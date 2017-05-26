package com.example.a360news.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.a360news.R;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by Apple on 2016/10/9.
 */
public class ShowActivity extends Activity {

    @BindView(R.id.photoView)
    PhotoView photoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show);
        ButterKnife.bind(this);

        String url = getIntent().getStringExtra("url");
        Toast.makeText(this,url,Toast.LENGTH_SHORT).show();

        //显示图片
        Picasso.with(this).load(url).into(photoView);
    }
}

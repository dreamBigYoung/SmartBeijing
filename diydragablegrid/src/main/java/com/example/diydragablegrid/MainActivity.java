package com.example.diydragablegrid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.diydragablegrid.view.MyGridLayout;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.top_grid)
    MyGridLayout topGrid;
    @BindView(R.id.bottom_grid)
    MyGridLayout bottomGrid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initTopGrid();
        initBottomGrid();
    }

    private void initBottomGrid() {
        bottomGrid.setMyId(2);
        bottomGrid.setDragable(true);
        bottomGrid.initGridLayout();
        bottomGrid.setOnItemClickListener(new MyGridLayout.OnItemClickListener() {
            @Override
            public void operationClick(View view) {
                bottomGrid.removeView(view);
                topGrid.myAddItem(0, (String) ((TextView)view).getText());
            }
        });
    }

    private void initTopGrid() {
        topGrid.setMyId(1);
        topGrid.setDragable(true);
        topGrid.initGridLayout();
        topGrid.setOnItemClickListener(new MyGridLayout.OnItemClickListener(){
            @Override
            public void operationClick(View view) {
                topGrid.removeView(view);
                bottomGrid.myAddItem(0, (String) ((TextView)view).getText());
            }
        });
    }
}

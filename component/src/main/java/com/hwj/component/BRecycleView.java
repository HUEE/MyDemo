package com.hwj.component;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

/**
 * Created by hwj on 2018/5/24.
 */

public class BRecycleView extends LinearLayout {
    RecyclerView recyclerView;
    SmartRefreshLayout refreshLayout;

    public BRecycleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.rv_main, this);
        refreshLayout = findViewById(R.id.ll_refresh);
        refreshLayout.setRefreshHeader(new ClassicsHeader(context));
        //列表默认布局
        recyclerView = findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
    }

    public SmartRefreshLayout getRefreshLayout() {
        return refreshLayout;
    }

    public RecyclerView getRecycleView() {
        return recyclerView;
    }

    public void setLayoutManager(RecyclerView.LayoutManager layout) {
        recyclerView.setLayoutManager(layout);
    }


}

package com.example.hwj.mydemo.network;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.base.IView;
import com.example.hwj.mydemo.base.DaggerBaseActivity;
import com.example.hwj.mydemo.network.Adapter.ListAdapter;

import java.util.List;

import butterknife.BindView;

import static com.example.hwj.mydemo.R.id.swipeRefreshLayout;

/**
 * 网络测试页面
 * Created by hwj on 16-9-8.
 */

public class MovieActivity extends DaggerBaseActivity<MoviePresenter> implements IView {
    @BindView(swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    ListAdapter adapter;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected IView attachView () {
        return this;
    }


    @Override
    protected int setLayout () {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void init () {
        adapter = new ListAdapter();
        gridRv.setLayoutManager(new LinearLayoutManager(mContext));
        gridRv.setAdapter(adapter);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setEnabled(false);
        adapter.setImages(null);
        presenter.loadData(this, 0, 30);
        presenter.text(this);
    }

    @Override
    public void getDataSuccess (Object model) {
        adapter.setImages((List) model);
    }

    @Override
    public void getDataFail () {

    }

}

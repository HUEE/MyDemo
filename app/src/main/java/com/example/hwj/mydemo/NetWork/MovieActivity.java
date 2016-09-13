package com.example.hwj.mydemo.NetWork;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hwj.mydemo.Base.MainView;
import com.example.hwj.mydemo.Base.MvpActivity;
import com.example.hwj.mydemo.NetWork.Adapter.ListAdapter;
import com.example.hwj.mydemo.R;

import java.util.List;

import butterknife.BindView;

import static com.example.hwj.mydemo.R.id.swipeRefreshLayout;

/**
 * 网络测试页面
 * Created by hwj on 16-9-8.
 */

public class MovieActivity extends MvpActivity<MoviePresenter> implements MainView {
    @BindView(swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    ListAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected MoviePresenter createPresenter() {
        return new MoviePresenter(this);
    }


    @Override
    protected int setLayout() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void init() {
        adapter = new ListAdapter();
        gridRv.setLayoutManager(new LinearLayoutManager(mContext));
        gridRv.setAdapter(adapter);
        refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setEnabled(false);
        adapter.setImages(null);
        mvpPresenter.loadData(this, 0, 30);
    }

    @Override
    public void getDataSuccess(Object model) {
        adapter.setImages((List) model);
    }

    @Override
    public void getDataFail() {

    }

}

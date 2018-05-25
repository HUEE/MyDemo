package com.example.hwj.mydemo.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.adapter.MainRvAdapter;
import com.example.hwj.mydemo.anim.AnimActivity;
import com.example.hwj.mydemo.base.DaggerBaseActivity;
import com.example.hwj.mydemo.base.IView;
import com.example.hwj.mydemo.dagger.bean.Login;
import com.example.hwj.mydemo.dagger.qualifiers.User;
import com.example.hwj.mydemo.network.MovieActivity;
import com.example.hwj.mydemo.network.MoviePresenter;
import com.hwj.component.BPopWindow;
import com.example.hwj.mydemo.rx.UnsubscribeTest;
import com.example.hwj.mydemo.selectList.TextSelectActivity;
import com.example.hwj.mydemo.utils.ToastUtils;
import com.hwj.component.BListView;

import java.util.Arrays;

import javax.inject.Inject;

import butterknife.BindView;

public class MainActivity extends DaggerBaseActivity<MoviePresenter> implements IView, BaseQuickAdapter.OnItemClickListener {
    @BindView(R.id.main_rv)
    BListView bListView;

    @Inject
    Login login;

    @Inject
    @User
    Login login1;

    @Inject
    SharedPreferences sharedPreferences;

    String[] items;

    BaseQuickAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected IView attachView() {
        return this;
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void init() {
        new UnsubscribeTest();
        presenter.getVisitor();
        items = getResources().getStringArray(R.array.main_items);
        adapter = new MainRvAdapter(Arrays.asList(items));
        adapter.setOnItemClickListener(this);
        //列表
        bListView.getRecycleView().setAdapter(adapter);
        //下拉刷新
//        bRecycleView.getRefreshLayout().setEnableRefresh(false);
        bListView.getRefreshLayout().setOnRefreshListener(v -> {

        });
    }

    @Override
    public void getDataSuccess(Object model) {
    }

    @Override
    public void getDataFail() {
    }

    @Override
    public void onItemClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        ToastUtils.showToast(this, items[i]);
        switch (i) {
            case 0:
                startActivity(new Intent(mContext, TextSelectActivity.class));
                break;
            case 1:
//                startActivity(new Intent(mContext, CollapsingToolbarLayoutActivity.class));
                break;
            case 2:
                startActivity(new Intent(mContext, MovieActivity.class));
                break;
            case 3:
                showPop(view);
                break;
            case 4:
                startActivity(new Intent(mContext, AnimActivity.class));
                break;
            case 5:
                break;
        }
    }

    private void showPop(View view) {
        final BPopWindow.Builder builder =
                new BPopWindow.Builder(this)
                        .setContentView(R.layout.pop)
                        .setBackgroundDimEnable(true)
                        .setFocusAndOutsideEnable(false)
                        .showAsDropDown(view);
        builder
                .getCurView()
                .findViewById(R.id.pop_bt)
                .setOnClickListener(
                        v -> {
                            Toast.makeText(mContext, "sucess", Toast.LENGTH_LONG).show();
                            builder.dismiss();

                        });
    }
}

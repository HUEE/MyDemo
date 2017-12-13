package com.example.hwj.mydemo.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.base.DaggerBaseActivity;
import com.example.hwj.mydemo.dagger.bean.Login;
import com.example.hwj.mydemo.dagger.qualifiers.User;
import com.example.hwj.mydemo.network.MovieActivity;
import com.example.hwj.mydemo.network.http.HttpService;
import com.example.hwj.mydemo.popwindow.PopWindow;
import com.example.hwj.mydemo.recyclerView.CollapsingToolbarLayoutActivity;
import com.example.hwj.mydemo.selectList.TextSelectActivity;
import com.example.hwj.mydemo.utils.ToastUtils;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends DaggerBaseActivity {
    @BindView(R.id.bt_selectlist)
    Button bt_selectlist;
    @BindView(R.id.bt_recyclerView1)
    Button bt_recyclerView1;
    @BindView(R.id.bt_retrofit)
    Button bt_retrofit;
    @BindView(R.id.bt_pop)
    Button bt_pop;

    //    @Inject
//    ApiService apiService;
    @Inject
    HttpService httpService;

    @Inject
    Login login;

    @Inject
    @User
    Login login1;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout () {
        return R.layout.activity_main;
    }

    @Override
    public void init () {
    }


    @OnClick({R.id.bt_selectlist, R.id.bt_recyclerView1, R.id.bt_retrofit,
            R.id.bt_pop, R.id.my_view})
    void btClick (View v) {
        switch (v.getId()) {
            case R.id.bt_selectlist:
                ToastUtils.showToast(this, "Main_btSelect---->hahahaha");
                startActivity(new Intent(mContext, TextSelectActivity.class));
                break;
            case R.id.bt_recyclerView1:
                ToastUtils.showToast(this, "RecyclerView1-->hahahahah");
                startActivity(new Intent(mContext, CollapsingToolbarLayoutActivity.class));
                break;
            case R.id.bt_retrofit:
                ToastUtils.showToast(mContext, "Retrofit--->hahahaha");
                startActivity(new Intent(mContext, MovieActivity.class));
                break;
            case R.id.bt_pop:
                final PopWindow.Builder builder = new PopWindow.Builder(this)
                        .setContentView(R.layout.pop)
                        .setBackgroundDimEnable(true)
                        .setFocusAndOutsideEnable(false)
                        .showAsDropDown(bt_pop);
                builder.getCurView().findViewById(R.id.pop_bt).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick (View view) {
                        Toast.makeText(mContext, "sucess", Toast.LENGTH_LONG).show();
                        builder.dismiss();
                    }
                });
                break;
            case R.id.my_view:
                break;

        }
    }
}

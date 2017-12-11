package com.example.hwj.mydemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.utils.Logger;
import com.example.hwj.mydemo.utils.Network;

import butterknife.ButterKnife;
import qiu.niorgai.StatusBarCompat;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hwj on 16-8-15.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected final String TAG = this.getClass().getSimpleName();
    public Network network;
    public Context mContext;
    private CompositeSubscription mCompositeSubscription;

    @Override
    protected void onCreate (@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "---> onCreate  <---");
        setContentView(setLayout());
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        ButterKnife.bind(this);
        network = Network.getInstance().init(getApplicationContext());
        mContext = getApplicationContext();
        init();
    }

    /**
     * 设置当前页面布局
     *
     * @return
     */
    protected abstract int setLayout ();

    /**
     * 页面初始化
     */
    protected abstract void init ();

    @Override
    protected void onStop () {
        Logger.d(TAG, "---> onStop    <---");
        super.onStop();
    }

    @Override
    protected void onDestroy () {
        Logger.d(TAG, "---> onDestroy <---");
        super.onDestroy();
    }

    @Override
    public void onBackPressed () {
        Logger.d(TAG, "---> onBackPressed <---");
        super.onBackPressed();
    }

    @Override
    protected void onPause () {
        Logger.d(TAG, "---> onPause   <---");
        super.onPause();
    }

    @Override
    protected void onResume () {
        Logger.d(TAG, "---> onResume  <---");
        super.onResume();
    }

    @Override
    protected void onStart () {
        Logger.d(TAG, "---> onStart   <---");
        super.onStart();
    }
}

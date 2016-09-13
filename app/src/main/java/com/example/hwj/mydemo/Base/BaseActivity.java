package com.example.hwj.mydemo.Base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.utils.tools.Network;

import butterknife.ButterKnife;
import qiu.niorgai.StatusBarCompat;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hwj on 16-8-15.
 */

public abstract class BaseActivity extends AppCompatActivity {
    /**
     * 是否输出日志信息
     **/
    private boolean isDebug = true;
    protected final String TAG = this.getClass().getSimpleName();
    public Network network;
    public Context mContext;
    private CompositeSubscription mCompositeSubscription;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "---> onCreate  <---");
        setContentView(setLayout());
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        ButterKnife.bind(this);
        network = Network.getInstance().init(getApplicationContext());
        mContext = getApplicationContext();
        init();
    }

    protected abstract int setLayout();

    protected abstract void init();

    protected void logD(String log) {
        if (isDebug) {
            Log.d(TAG, log);
        }
    }


    @Override
    protected void onStop() {
        Log.d(TAG, "---> onStop    <---");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "---> onDestroy <---");
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "---> onBackPressed <---");
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "---> onPause   <---");
        super.onPause();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "---> onResume  <---");
        super.onResume();
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "---> onStart   <---");
        super.onStart();
    }
}

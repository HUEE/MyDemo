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
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.subjects.BehaviorSubject;

/**
 * Created by hwj on 16-8-15.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected final String TAG = this.getClass().getSimpleName();
    private final BehaviorSubject<LifecycleEvent> lifecycleSubject = BehaviorSubject.create();
    public Observable<LifecycleEvent> lifecycle() {
        return lifecycleSubject;
    }

    public Network network;
    public Context mContext;

    public static Action1<Throwable> ACTION_THROWABLE_REPORT =
            throwable -> {
                Logger.d(throwable.getMessage());
            };

    public static Action0 ACTION0_NOTHING = () -> {
    };

    public void creates(Action1<Bundle> action) {
        creates(action, ACTION0_NOTHING);
    }

    public void creates(final Action1<Bundle> onCreate, Action0 onDestroy) {
        lifecycleSubject.subscribe(
                event -> {
                    if (event instanceof LifecycleEvent.Create) {
                        onCreate.call(((LifecycleEvent.Create) event).bundle);
                    } else if (event == LifecycleEvent.Destroy) {
                        onDestroy.call();
                    }
                },
                ACTION_THROWABLE_REPORT,
                ACTION0_NOTHING);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Logger.d(TAG, "---> onCreate  <---");
        setContentView(setLayout());
        lifecycleSubject.onNext(new LifecycleEvent.Create(savedInstanceState));
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary));
        ButterKnife.bind(this);
        network = Network.getInstance().init(getApplicationContext());
        mContext = getApplicationContext();
        init();
        lifecycleSubject.onNext(LifecycleEvent.AfterCreate);
    }

    /**
     * 设置当前页面布局
     *
     * @return
     */
    protected abstract int setLayout();

    /**
     * 页面初始化
     */
    protected abstract void init();

    @Override
    protected void onStop() {
        Logger.d(TAG, "---> onStop    <---");
        super.onStop();
        lifecycleSubject.onNext(LifecycleEvent.Stop);
    }

    @Override
    protected void onDestroy() {
        Logger.d(TAG, "---> onDestroy <---");
        super.onDestroy();
        lifecycleSubject.onNext(LifecycleEvent.Destroy);
        lifecycleSubject.onCompleted();
    }

    @Override
    public void onBackPressed() {
        Logger.d(TAG, "---> onBackPressed <---");
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        Logger.d(TAG, "---> onPause   <---");
        super.onPause();
        lifecycleSubject.onNext(LifecycleEvent.Pause);
    }

    @Override
    protected void onResume() {
        Logger.d(TAG, "---> onResume  <---");
        super.onResume();
        lifecycleSubject.onNext(LifecycleEvent.Resume);
    }

    @Override
    protected void onStart() {
        Logger.d(TAG, "---> onStart   <---");
        super.onStart();
        lifecycleSubject.onNext(LifecycleEvent.Start);
    }
}

package com.example.hwj.mydemo.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasFragmentInjector;
import dagger.android.support.HasSupportFragmentInjector;

/**
 * Created by hwj on 17-12-11.
 */

public abstract class DaggerBaseActivity<P extends BasePresenter> extends BaseActivity implements HasFragmentInjector, HasSupportFragmentInjector {
    @Inject
    DispatchingAndroidInjector<Fragment> supportFragmentInjector;
    @Inject
    DispatchingAndroidInjector<android.app.Fragment> frameworkFragmentInjector;

    @Inject
    P presenter;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        presenter.attachView(this);
    }

    protected abstract BaseView attachView ();

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector () {
        return supportFragmentInjector;
    }

    @Override
    public AndroidInjector<android.app.Fragment> fragmentInjector () {
        return frameworkFragmentInjector;
    }

    @Override
    protected void onDestroy () {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }
}

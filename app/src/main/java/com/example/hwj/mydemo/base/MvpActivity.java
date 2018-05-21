package com.example.hwj.mydemo.base;

public abstract class MvpActivity<P extends BasePresenter> extends BaseActivity {
    protected P mvpPresenter;
    protected abstract P createPresenter();

    {
        creates(sis -> {
            mvpPresenter = createPresenter();
        }, () -> {
            if (mvpPresenter != null) {
                mvpPresenter.detachView();
            }
        });
    }
}

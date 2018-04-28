package com.example.hwj.mydemo.base;

public interface IPresenter<V> {

    void attachView(V view);

    void detachView();

}

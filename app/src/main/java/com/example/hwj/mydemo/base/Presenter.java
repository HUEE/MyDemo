package com.example.hwj.mydemo.base;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}

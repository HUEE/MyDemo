package com.example.hwj.mydemo.Base;

public interface Presenter<V> {

    void attachView(V view);

    void detachView();

}

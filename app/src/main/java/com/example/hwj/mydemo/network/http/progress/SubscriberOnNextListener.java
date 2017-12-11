package com.example.hwj.mydemo.network.http.progress;

/**
 * Created by liukun on 16/3/10.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);

    void onError();
}

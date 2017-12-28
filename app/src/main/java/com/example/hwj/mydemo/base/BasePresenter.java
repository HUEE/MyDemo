package com.example.hwj.mydemo.base;

import com.example.hwj.mydemo.network.http.HttpMethods;
import com.example.hwj.mydemo.network.http.HttpService;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by hwj on 2016/9/13.
 */
public class BasePresenter<V extends BaseView> implements Presenter<V> {
    public V mvpView;
    public HttpMethods apiStores = HttpMethods.getInstance();
    private CompositeSubscription mCompositeSubscription;
    @Inject
    protected HttpService httpService;


    @Override
    public void attachView(V mvpView) {
        this.mvpView = mvpView;
    }


    @Override
    public void detachView() {
        this.mvpView = null;
        onUnsubscribe();
    }


    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }
}

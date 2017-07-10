package com.example.hwj.mydemo.NetWork;

import android.content.Context;

import com.example.hwj.mydemo.Base.BasePresenter;
import com.example.hwj.mydemo.Base.MainView;
import com.example.hwj.mydemo.NetWork.http.Bean.Subject;
import com.example.hwj.mydemo.NetWork.http.progress.ProgressSubscriber;
import com.example.hwj.mydemo.NetWork.http.progress.SubscriberOnNextListener;

import java.util.List;

import rx.Observable;

/**
 * Created by hwj
 * on 2016/9/13.
 */
public class MoviePresenter extends BasePresenter<MainView> {
    private SubscriberOnNextListener getTopMovieOnNext;
    private SubscriberOnNextListener textOnNext;

    public MoviePresenter(MainView view) {
        attachView(view);
        getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                mvpView.getDataSuccess(subjects);
            }

            @Override
            public void onError() {
                mvpView.getDataFail();
            }

        };

        textOnNext = new SubscriberOnNextListener() {
            @Override
            public void onNext(Object o) {
                mvpView.getDataSuccess(o);
            }

            @Override
            public void onError() {
                mvpView.getDataFail();
            }
        };


    }


    public void loadData(Context context, int start, int count) {
        Observable observable = apiStores.getTopMovie(start, count);
        addSubscription(observable, new ProgressSubscriber(getTopMovieOnNext, context));
    }

    public void text(Context context){
        addSubscription(apiStores.text(),new ProgressSubscriber(getTopMovieOnNext, context));
    }

}

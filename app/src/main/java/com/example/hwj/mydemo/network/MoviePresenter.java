package com.example.hwj.mydemo.network;

import android.content.Context;

import com.example.hwj.mydemo.base.BasePresenter;
import com.example.hwj.mydemo.base.IView;
import com.example.hwj.mydemo.network.http.Bean.Subject;
import com.example.hwj.mydemo.network.http.progress.ProgressSubscriber;
import com.example.hwj.mydemo.network.http.progress.SubscriberOnNextListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by hwj
 * on 2016/9/13.
 */
public class MoviePresenter extends BasePresenter<IView> {
    private SubscriberOnNextListener getTopMovieOnNext;
    private SubscriberOnNextListener textOnNext;

    @Inject
    public MoviePresenter () {
        getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext (List<Subject> subjects) {
                mvpView.getDataSuccess(subjects);
            }

            @Override
            public void onError () {
                mvpView.getDataFail();
            }

        };

        textOnNext = new SubscriberOnNextListener() {
            @Override
            public void onNext (Object o) {
                mvpView.getDataSuccess(o);
            }

            @Override
            public void onError () {
                mvpView.getDataFail();
            }
        };
    }

//    @Provides
//    @Singleton
//    public

    public void loadData (Context context, int start, int count) {
        Observable observable = httpService.getTopMovie(start, count)
                .map(new HttpResultFunc<List<Subject>>());
        addSubscription(observable, new ProgressSubscriber(getTopMovieOnNext, context));
    }


    public void getVisitor () {
        Map map = new HashMap();
        map.put("userId", "12");
        Observable observable = httpService.execPost("https://nbangbang.58.com/api/v2/info/get_user_footprint", map)
                .map(new ResultDecodeFunc<VisitorInfo>());
        addSubscription(observable, new BaseSubscriber<VisitorInfo>() {
            @Override
            public void onError (Throwable e) {
                super.onError(e);
            }

            @Override
            public void onNext (VisitorInfo httpResult) {
                super.onNext(httpResult);
            }
        });
    }


    public void text (Context context) {
        Map map = new HashMap();
        map.put("menu", "西红柿鸡蛋");
        map.put("key", "b4f9954e4a3efed73796b73faf47ffa3");
        map.put("dtype", "json");
        map.put("pn", "0");
        map.put("rn", "20");
        map.put("albums", "1");
        addSubscription(httpService.execPost("http://apis.juhe.cn/cook/query.php", map),
                new ProgressSubscriber(textOnNext, context));
    }


}

package com.example.hwj.mydemo.rx;

import android.os.Handler;
import android.os.Looper;

import com.example.hwj.mydemo.utils.Logger;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by hwj on 2018/5/10.
 */

public class RxTest {
    private static final Handler handler = new Handler(Looper.getMainLooper());

    public static void rxTest() {

        Observable.just("A", "B")
                .flatMap(s -> Observable.just(s).subscribeOn(Schedulers.newThread()))
                .subscribeOn(Schedulers.newThread())
                .switchMap(s -> {
                            Logger.d("===Observable<String> switch call: " + Thread.currentThread()
                                    .getName());
                            return Observable.just(s);
                        }
                )
                .subscribe(
                        s -> {
                            Logger.d("===Observable<String> subscribe call: " + Thread.currentThread()
                                    .getName());
                            Logger.d("------>onNext:" + s);
                        },

                        e -> {
                            Logger.d("------>onError()" + e);
                        },
                        () -> {
                            Logger.d("------>onCompleted()");
                        }
                );
    }

    public static void rxTest2() {
        PublishSubject<String> subject = PublishSubject.create();
        subject.switchMap(
                s -> {
                    return Observable.just(s).map(str -> {
                        Logger.d("===Observable<String> map call: " + Thread.currentThread()
                                .getName());
//                        try {
//                            Thread.currentThread().sleep(500);
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
                        for (int i = 0; i < 10000000; i++) ;
                        return str + "_";
                    }).subscribeOn(Schedulers.io());
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s -> {
                    Logger.d("===Observable<String> subscribe call: " + Thread.currentThread()
                            .getName());
                    Logger.d("------>onNext:" + s);
                });

        handler.postDelayed(() -> subject.onNext("A"), 11);
        handler.postDelayed(() -> subject.onNext("B"), 10);
        handler.postDelayed(() -> subject.onNext("C"), 2000);
        handler.postDelayed(() -> subject.onNext("D"), 2000);
    }


}

package com.example.hwj.mydemo.rx;

import com.example.hwj.mydemo.utils.Logger;

import rx.Observable;
import rx.Subscription;

/**
 * Created by hwj on 2018/5/18.
 */

public class UnsubscribeTest {
    public UnsubscribeTest() {
        Subscription subscription = Observable.just("q", "w", "e", "r")
                .subscribe(s -> {
                    Logger.d(s);
                });
        Logger.d(String.valueOf(subscription.isUnsubscribed()));
    }


}

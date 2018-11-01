package com.example.hwj.mydemo.rx;

import com.example.hwj.mydemo.utils.Logger;

import rx.Subscription;
import rx.subscriptions.Subscriptions;

/** Created by hwj on 2018/5/18. */
public class UnsubscribeTest {
  public UnsubscribeTest() {
    //        Subscription subscription = Observable.just("q", "w", "e", "r")
    //                .subscribe(s -> {
    //                    Logger.d(s);
    //                });
    //        Logger.d(String.valueOf(subscription.isUnsubscribed()));

    Subscription rx1 =
        Subscriptions.create(
            () -> {
              Logger.d("UnsubscribeTest", "1-->");
            });
    Logger.d("UnsubscribeTest", "2-->");
    rx1.unsubscribe();
  }
}

package com.example.hwj.mydemo.rx;

import rx.subjects.BehaviorSubject;

/**
 * Created by hwj on 2018/6/1.
 */

public class BehaviorSubjectTest {

    BehaviorSubject<String> subject = BehaviorSubject.create();

    public void OnNext(String next) {
        subject.onNext(next);
    }

}

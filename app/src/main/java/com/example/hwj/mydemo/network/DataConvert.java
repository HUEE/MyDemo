package com.example.hwj.mydemo.network;

import javax.inject.Inject;

import rx.Observable;

/**
 * 数据转换
 * Created by hwj on 17-12-14.
 */

public class DataConvert {
    private HttpResultFunc resultFunc;

    @Inject
    public DataConvert (HttpResultFunc resultFunc) {
        this.resultFunc = resultFunc;
    }

    public Observable add (Observable observable) {
        return observable.map(resultFunc);
    }
}

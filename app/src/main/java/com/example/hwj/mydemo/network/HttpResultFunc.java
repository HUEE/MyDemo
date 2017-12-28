package com.example.hwj.mydemo.network;

import com.example.hwj.mydemo.network.http.ApiException;
import com.example.hwj.mydemo.network.http.Bean.HttpResult;

import javax.inject.Inject;

import rx.functions.Func1;

/**
 * 网络数据转换器
 * Created by hwj on 17-12-13.
 */

public class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
    @Inject
    public HttpResultFunc () {

    }

    @Override
    public T call (HttpResult<T> httpResult) {
        if (httpResult.getCount() == 0) {
            try {
                throw new ApiException(100);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return httpResult.getSubjects();
    }
}

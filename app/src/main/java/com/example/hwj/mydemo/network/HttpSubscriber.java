package com.example.hwj.mydemo.network;

import android.content.Context;

import com.example.hwj.mydemo.network.http.ApiException;
import com.example.hwj.mydemo.utils.DataUtils;
import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import retrofit2.Response;


/**
 * Created by hwj on 17-3-22.
 * 远程数据数据Subscriber
 */

public abstract class HttpSubscriber<T> extends BaseSubscriber<Response<HttpResult>> implements ParameterizedType {

    public static final int TOSTRING = 1;

    private int toFlag = -1;

    public HttpSubscriber () {
        super();
    }

    public HttpSubscriber (Context context) {
        super(context);
    }

    public HttpSubscriber (int toFlag) {
        super();
        this.toFlag = toFlag;
    }

    public HttpSubscriber (Context context, int toFlag) {
        super(context);
        this.toFlag = toFlag;
    }

    @Override
    public void onNext (Response<HttpResult> httpResult) {
        super.onNext(httpResult);
//        Logger.d("OkHttp", "key-->" + httpResult.getKey());
//        Logger.d("OkHttp", "data-->" + httpResult.getData());
        String data = DataUtils.decryptData(httpResult.body().getKey(), httpResult.body().getData());
        if (TOSTRING == toFlag) {
            onSuccess((T) data);
            onCompleted();
            return;
        }
        Type gsonType = this;
        ResponseData<T> typeResponse = new Gson().fromJson(data, gsonType);
        if (0 == typeResponse.ret) {
            //成功返回对应数据
            onSuccess(typeResponse.data);
            onCompleted();
        } else {
            //抛出服务器返回异常
            onFailure(new ApiException(typeResponse.getMsg()));
        }
    }

    @Override
    public void onError (Throwable e) {
        super.onError(e);
        onFailure(getApiException(e));
    }

    /**
     * 请求对应回调接口
     */
    public abstract void onSuccess (T t);

    public abstract void onFailure (ApiException t);

    /**
     * 获取传入泛型类型
     */
    @Override
    public Type[] getActualTypeArguments () {
        Class clz = this.getClass();
        Type superclass = clz.getGenericSuperclass(); //getGenericSuperclass()获得带有泛型的父类
        if (superclass instanceof Class) {
            throw new RuntimeException("Missing type parameter.");
        }
        ParameterizedType parameterized = (ParameterizedType) superclass;
        return parameterized.getActualTypeArguments();
    }

    @Override
    public Type getOwnerType () {
        return null;
    }

    @Override
    public Type getRawType () {
        return ResponseData.class;
    }
}

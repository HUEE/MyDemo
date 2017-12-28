package com.example.hwj.mydemo.network;

import com.example.hwj.mydemo.network.http.ApiException;
import com.example.hwj.mydemo.utils.DataUtils;
import com.google.gson.Gson;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Request;
import retrofit2.Response;
import rx.functions.Func1;

/**
 * Created by hwj on 17-12-26.
 */

public class ResultDecodeFunc<T> implements Func1<Response<HttpResult>, T>, ParameterizedType {
    ApiException apiException = new ApiException();

    @Override
    public T call (Response<HttpResult> response) {
        if (response.isSuccessful()) {

            Request request = response.raw().networkResponse().request();
            apiException = new ApiException();
            apiException.setUrl(request.url().toString());
            apiException.setRequestHeader(response.headers().toString());
            apiException.setResponseTimestamp(System.currentTimeMillis());
            apiException.setRequestMethod(request.method());

            HttpResult httpResult = response.body();
//            httpResult.setRet(-1);
            if (httpResult.getRet() == 0) {
                //正确拿到数据
                String data = DataUtils.decryptData(httpResult.getKey(), httpResult.getData());
                Type gsonType = this;
                ResponseData<T> typeResponse = new Gson().fromJson(data, gsonType);
                if (0 == typeResponse.ret && null != typeResponse.data) {
                    //成功返回对应数据
                    return typeResponse.data;
                } else {
                    throwErrMsg(typeResponse.ret, typeResponse.getMsg(), data);
                }
            } else {
                //获取数据异常
                throwErrMsg(httpResult.getRet(), httpResult.getMsg(), "");
            }
        } else {
            String errContent = null;
            try {
                errContent = response.errorBody().string();
                throwErrMsg(response.code(), errContent, "");
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * @param code
     * @param msg
     * @param content
     */
    private void throwErrMsg (int code, String msg, String content) {
        //抛出服务器返回异常
        apiException.setStutusCode(code);
        apiException.setErrorMessage(msg);
        apiException.setOriginalResponse(content);
        throw apiException;
    }

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

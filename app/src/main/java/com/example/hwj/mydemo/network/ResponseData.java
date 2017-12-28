package com.example.hwj.mydemo.network;


/**
 * 服务器响应数据,解密后范型数据可解析至此
 *
 * @param <T>
 */
public class ResponseData<T> {
    //请求状态码
    public int ret;
    //请求信息
    public String msg;
    //范型数据
    public T data;
    //刷新次数
    private int bizCode;
    //错误码
    private int errorCode;

    public int getBizCode () {
        return bizCode;
    }

    public void setBizCode (int bizCode) {
        this.bizCode = bizCode;
    }

    public int getErrorCode () {
        return errorCode;
    }

    public void setErrorCode (int errorCode) {
        this.errorCode = errorCode;
    }

    public int getRet () {
        return ret;
    }

    public void setRet (int ret) {
        this.ret = ret;
    }

    public String getMsg () {
        return msg;
    }

    public void setMsg (String msg) {
        this.msg = msg;
    }

    public T getResult () {
        return data;
    }

    public void setResult (T result) {
        this.data = result;
    }
}

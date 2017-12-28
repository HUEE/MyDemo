package com.example.hwj.mydemo.network;


/**
 * 服务器返回未解密数据
 */

public class HttpResult {
    //为解密数据源
    private String data;
    //解密Key
    private String key;
    //请求消息提示
    private String msg;
    //请求状态码
    private int ret;


    public String getData () {
        return data;
    }

    public void setData (String data) {
        this.data = data;
    }

    public String getKey () {
        return key;
    }

    public void setKey (String key) {
        this.key = key;
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

}

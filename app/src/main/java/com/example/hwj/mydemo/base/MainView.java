package com.example.hwj.mydemo.base;

/**
 * Created by WuXiaolong on 2015/9/23.
 * 处理业务需要哪些方法
 */
public interface MainView<T> {

    void getDataSuccess(T model);

    void getDataFail();

}

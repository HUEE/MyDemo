package com.example.hwj.mydemo.NetWork.http;

import com.example.hwj.mydemo.NetWork.http.Bean.HttpResult;
import com.example.hwj.mydemo.NetWork.http.Bean.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * 网络请求接口
 * Created by hwj on 16/9/9.
 */
public interface HttpService {

    //获取豆瓣top250电影
    @GET("top250")
    Observable<HttpResult<List<Subject>>> getTopMovie(@Query("start") int start, @Query("count") int count);



}

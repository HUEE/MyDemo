package com.example.hwj.mydemo.NetWork.http;

import com.example.hwj.mydemo.NetWork.http.Bean.HttpResult;
import com.example.hwj.mydemo.NetWork.http.Bean.MyResult;
import com.example.hwj.mydemo.NetWork.http.Bean.ShenFen;
import com.example.hwj.mydemo.NetWork.http.Bean.Subject;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Header;
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

    //获取身份证信息接口
    @GET("idservice/id")
    Observable<MyResult<ShenFen>> getShenFen(@Header("apikey") String apikey, @Query("id") String id);


}

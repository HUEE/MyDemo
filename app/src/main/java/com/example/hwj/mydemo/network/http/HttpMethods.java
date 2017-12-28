package com.example.hwj.mydemo.network.http;

import com.example.hwj.mydemo.network.http.Bean.HttpResult;
import com.example.hwj.mydemo.network.http.Bean.Subject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * 网络请求封装及数据处理
 * Created by hwj on 16/9/9.
 */
public class HttpMethods {

    public static final String BASE_URL = "https://api.douban.com/v2/movie/";

    public static final String BASE_URL1 = "http://apis.baidu.com/apistore/";

    private static final int DEFAULT_TIMEOUT = 5;

    private Retrofit retrofit;
    private HttpService httpService;
    private static OkHttpClient okHttpClient = new OkHttpClient();

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        //builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        okHttpClient = new OkHttpClient.Builder().addInterceptor(logging).build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        httpService = retrofit.create(HttpService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    //获取单例
    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

//    /**
//     * 用于获取豆瓣电影Top250的数据
//     * @param start      起始位置
//     * @param count      获取长度
//     */
//    public Observable getTopMovie(int start, int count) {
//        return httpService.getTopMovie(start, count)
//                .map(new HttpResultFunc<List<Subject>>());
//    }

    public Observable text(){
        Map map = new HashMap();
        map.put("menu","西红柿鸡蛋");
        map.put("key","b4f9954e4a3efed73796b73faf47ffa3");
        map.put("dtype","json");
        map.put("pn","0");
        map.put("rn","20");
        map.put("albums","1");
        return httpService.execPost("http://apis.juhe.cn/cook/query.php",map);
    }

//    /**
//     * 用来统一处理Http的resultCode,并将HttpResult的Data部分剥离出来返回给subscriber
//     *
//     * @param <T> Subscriber真正需要的数据类型，也就是Data部分的数据类型
//     */
//    private class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
//
//        @Override
//        public T call(HttpResult<T> httpResult) {
//            if (httpResult.getCount() == 0) {
//                throw new ApiException(100);
//            }
//            return httpResult.getSubjects();
//        }
//    }

}

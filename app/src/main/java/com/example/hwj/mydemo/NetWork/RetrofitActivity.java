package com.example.hwj.mydemo.NetWork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hwj.mydemo.Base.BaseActivity;
import com.example.hwj.mydemo.NetWork.Adapter.ListAdapter;
import com.example.hwj.mydemo.NetWork.http.Bean.ShenFen;
import com.example.hwj.mydemo.NetWork.http.Bean.Subject;
import com.example.hwj.mydemo.NetWork.http.HttpMethods;
import com.example.hwj.mydemo.NetWork.http.progress.ProgressSubscriber;
import com.example.hwj.mydemo.NetWork.http.progress.SubscriberOnNextListener;
import com.example.hwj.mydemo.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import rx.Subscription;

import static com.example.hwj.mydemo.R.id.swipeRefreshLayout;

/**
 * 网络测试页面
 * Created by hwj on 16-9-8.
 */

public class RetrofitActivity extends BaseActivity {
    @BindView(swipeRefreshLayout)
    SwipeRefreshLayout refreshLayout;
    @BindView(R.id.gridRv)
    RecyclerView gridRv;
    ListAdapter adapter;
    protected Subscription subscription;
    private SubscriberOnNextListener getTopMovieOnNext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int setLayout() {
        return R.layout.activity_retrofit;
    }

    @Override
    protected void init() {
        adapter = new ListAdapter();
        gridRv.setLayoutManager(new LinearLayoutManager(mContext));
        gridRv.setAdapter(adapter);
        // refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setEnabled(false);
        unsubscribe();
        adapter.setImages(null);

        // getMovie();
        getShen();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void getMovie() {
        getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                adapter.setImages(subjects);
            }
        };
        HttpMethods.getInstance().getTopMovie(new ProgressSubscriber(getTopMovieOnNext, RetrofitActivity.this), 0, 30);
    }

    private void getShen() {
        getTopMovieOnNext = new SubscriberOnNextListener<ShenFen>() {
            @Override
            public void onNext(ShenFen subjects) {
                List list = new ArrayList();
                list.add(subjects);
                adapter.setImages(list);
            }
        };
        HttpMethods.getInstance().getShenFen(new ProgressSubscriber<ShenFen>(getTopMovieOnNext, RetrofitActivity.this), "d7a2ccff4fd864d842f39320337444c9", "422201199205101816");
    }


}

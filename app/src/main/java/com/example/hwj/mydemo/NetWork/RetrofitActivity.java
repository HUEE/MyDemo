package com.example.hwj.mydemo.NetWork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.hwj.mydemo.Base.BaseActivity;
import com.example.hwj.mydemo.NetWork.Adapter.GvListAdapter;
import com.example.hwj.mydemo.NetWork.http.HttpMethods;
import com.example.hwj.mydemo.NetWork.http.Subject;
import com.example.hwj.mydemo.NetWork.http.progress.ProgressSubscriber;
import com.example.hwj.mydemo.NetWork.http.progress.SubscriberOnNextListener;
import com.example.hwj.mydemo.R;

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
    GvListAdapter adapter;
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
        adapter = new GvListAdapter();
        gridRv.setLayoutManager(new LinearLayoutManager(mContext));
        gridRv.setAdapter(adapter);
        // refreshLayout.setColorSchemeColors(Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW);
        refreshLayout.setEnabled(false);
        unsubscribe();
        adapter.setImages(null);
        getTopMovieOnNext = new SubscriberOnNextListener<List<Subject>>() {
            @Override
            public void onNext(List<Subject> subjects) {
                adapter.setImages(subjects);
            }
        };
        getMovie();
    }

    protected void unsubscribe() {
        if (subscription != null && !subscription.isUnsubscribed()) {
            subscription.unsubscribe();
        }
    }

    private void getMovie() {
        HttpMethods.getInstance().getTopMovie(new ProgressSubscriber(getTopMovieOnNext, RetrofitActivity.this), 0, 30);
    }


}

package com.example.hwj.mydemo.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by hwj on 2018/5/18.
 */

public class BaseFragment extends Fragment {

    private final BehaviorSubject<LifecycleEvent> lifecycleSubject = BehaviorSubject.create();
    public Observable<LifecycleEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }


    public void preCreateView(Bundle sis) {
    }

    protected void initView(Bundle sis) {
    }

    protected View inflateView(LayoutInflater inflater, ViewGroup container) {
        return null;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        preCreateView(savedInstanceState);
        View view = inflateView(inflater, container);
        if (view != null) {
            initView(savedInstanceState);
        }
        lifecycleSubject.onNext(new LifecycleEvent.CreateView(savedInstanceState));
        lifecycleSubject.onNext(LifecycleEvent.AfterCreateView);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        lifecycleSubject.onNext(LifecycleEvent.DestroyView);
        super.onDestroyView();

    }

    @Override
    public void onStop() {
        lifecycleSubject.onNext(LifecycleEvent.Stop);
        super.onStop();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(new LifecycleEvent.Create(savedInstanceState));
    }

    @Override
    public void onDestroy() {
        lifecycleSubject.onNext(LifecycleEvent.Destroy);
        super.onDestroy();
    }

    @Override
    public void onPause() {
        lifecycleSubject.onNext(LifecycleEvent.Pause);
        super.onPause();

    }

    @Override
    public void onStart() {
        super.onStart();
        lifecycleSubject.onNext(LifecycleEvent.Start);
    }

    @Override
    public void onDetach() {
        lifecycleSubject.onNext(LifecycleEvent.Detach);
        lifecycleSubject.onCompleted();
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        lifecycleSubject.onNext(LifecycleEvent.Resume);
    }


    @Override
    public void onAttach(Context context) {
        lifecycleSubject.onNext(LifecycleEvent.Attach);
        super.onAttach(context);
    }
}

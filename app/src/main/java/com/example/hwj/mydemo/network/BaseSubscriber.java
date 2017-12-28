package com.example.hwj.mydemo.network;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.network.http.ApiException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import rx.Subscriber;

import static com.example.hwj.mydemo.network.http.ApiException.CONNECT_EXCEPTION;
import static com.example.hwj.mydemo.network.http.ApiException.SOCKET_TIMEOUT_EXCEPTION;


/**
 * Created by hwj on 17-3-22.
 * 数据加载Subscriber
 */

public class BaseSubscriber<T> extends Subscriber<T> implements DialogInterface.OnCancelListener {

    private Context mContext;

    private ProgressDialog pd;

    /**
     * 空构造函数则不会显示loading
     */
    public BaseSubscriber () {

    }

    public BaseSubscriber (Context context) {
        this.mContext = context;
        pd = new ProgressDialog(mContext, R.style.CustomProgressDialog);
        //取消监听
        pd.setOnCancelListener(this);
    }

    @Override
    public void onCompleted () {
        dismissDialog();
    }

    @Override
    public void onStart () {
        super.onStart();
        showDialog(true, null);
    }

    @Override
    public void onError (Throwable e) {
        dismissDialog();
    }

    @Override
    public void onNext (T httpResult) {
        dismissDialog();
    }


    public void detachSubscriber () {
        if (null != pd) {
            pd.dismiss();
            mContext = null;
        }
    }

    /**
     * 获取网络错误
     */
    protected ApiException getApiException (Throwable e) {
        if (e instanceof SocketTimeoutException) {
            return new ApiException(SOCKET_TIMEOUT_EXCEPTION);
        } else if (e instanceof ConnectException) {
            return new ApiException(CONNECT_EXCEPTION);
        }
        return new ApiException(e.getMessage());
    }


    /**
     * 显示网络处理对话框
     *
     * @param canceledOnTouchOutside
     * @param messageText
     */
    public void showDialog (final boolean canceledOnTouchOutside, final String messageText) {
        if (mContext == null || ((Activity) mContext).isFinishing()) {
            if (null != pd) {
                pd.dismiss();
            }
            return;
        }
        ((Activity) mContext).runOnUiThread(new Runnable() {
            @Override
            public void run () {
                if (null != messageText) {
                    pd.setMessage(messageText);
                }
                pd.setCancelable(canceledOnTouchOutside);
                if (!pd.isShowing()) {
                    pd.show();
                }
            }
        });
    }

    /**
     * 关闭网络处理对话框
     */
    public void dismissDialog () {
        if (mContext == null || ((Activity) mContext).isFinishing()) {
            if (null != pd) {
                pd.dismiss();
            }
            return;
        }
        if (mContext != null) {
            if (pd != null && pd.isShowing()) {
                ((Activity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run () {
                        pd.dismiss();
                    }
                });
            }
        }
    }


    @Override
    public void onCancel (DialogInterface dialog) {
        if (!this.isUnsubscribed()) {
            //取消监听
            this.unsubscribe();
        }
    }
}

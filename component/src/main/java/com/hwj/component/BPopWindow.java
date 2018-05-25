package com.hwj.component;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroupOverlay;
import android.widget.PopupWindow;


/**
 * 自定义popwindow
 * Created by hwj on 17-8-8.
 */

public class BPopWindow {

    /**
     * PopWindow创建者
     */
    public static class Builder implements PopupWindow.OnDismissListener {
        //界面View
        private View mContentView;
        //上下文
        private Context mContext;
        //Pop弹窗
        private PopupWindow mPopWindow;
        //背景变暗时透明度
        protected float mDimValue = 0.7f;
        //背景变暗颜色
        @ColorInt
        protected int mDimColor = Color.BLACK;
        //弹出pop时，背景是否变暗,默认为否
        protected boolean isBackgroundDim = false;
        //背景变暗的view
        @NonNull
        protected ViewGroup mDimView;

        /**
         * Creates a new instance of Builder.
         *
         * @param context context
         */
        public Builder (Context context) {
            this.mContext = context;
        }

        /**
         * 设置内容界面
         *
         * @param id Int
         * @return Builder
         */
        public Builder setContentView (int id) {
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            mContentView = inflater.inflate(id, null);
            mPopWindow = new PopupWindow(mContentView,
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
            mPopWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            mPopWindow.setOnDismissListener(this);
            return this;
        }

        /**
         * 获取当前Pop的视图，便于获取其中子控件
         *
         * @return
         */
        public View getCurView () {
            return mContentView;
        }

        /**
         * 设置当前ＰopWindow显示位置
         *
         * @param rootview
         * @return
         */
        public Builder showAsDropDown (View rootview) {
            if (null != mPopWindow) {
                handleBackgroundDim();
                mPopWindow.showAsDropDown(rootview);
            }
            return this;
        }

        public void showAsDropDown (View anchor, int offsetX, int offsetY) {
            if (null != mPopWindow) {
                handleBackgroundDim();
                mPopWindow.showAsDropDown(anchor, offsetX, offsetY);
            }
        }

        public void showAtLocation (View parent, int gravity, int offsetX, int offsetY) {
            if (null != mPopWindow) {
                handleBackgroundDim();
                mPopWindow.showAtLocation(parent, gravity, offsetX, offsetY);
            }
        }

        /**
         * 设置是否背景变暗
         *
         * @param isDim
         * @return
         */
        public Builder setBackgroundDimEnable (boolean isDim) {
            this.isBackgroundDim = isDim;
            return this;
        }

        /**
         * 是否可以点击PopupWindow之外的地方dismiss
         *
         * @param focusAndOutsideEnable
         * @return
         */
        public Builder setFocusAndOutsideEnable (boolean focusAndOutsideEnable) {
            if (focusAndOutsideEnable) {
                mPopWindow.setOutsideTouchable(focusAndOutsideEnable);
                mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                mPopWindow.setFocusable(true);
            } else {
                mPopWindow.setFocusable(false);
                mPopWindow.setOutsideTouchable(false);
                mPopWindow.setBackgroundDrawable(null);
            }
            return this;
        }

        /**
         * 取消Pop弹窗
         *
         * @return
         */
        public Builder dismiss () {
            if (null != mPopWindow) {
                mPopWindow.dismiss();
            }
            return this;
        }

        /**
         * 设置背景变暗颜色,不设置则为默认
         *
         * @param color
         * @return
         */
        public Builder setDimColor (@ColorInt int color) {
            this.mDimColor = color;
            return this;
        }

        /**
         * 设置背景变暗
         */
        private void handleBackgroundDim () {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                if (isBackgroundDim) {
                    Activity activity = (Activity) mContext;
                    ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
                    Drawable dim = new ColorDrawable(mDimColor);
                    dim.setBounds(0, 0, parent.getWidth(), parent.getHeight());
                    dim.setAlpha((int) (255 * mDimValue));
                    ViewGroupOverlay overlay = parent.getOverlay();
                    overlay.add(dim);
                }

            }
        }

        /**
         * 清除背景变暗
         */
        private void clearBackgroundDim () {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                if (isBackgroundDim) {
                    Activity activity = (Activity) mContext;
                    ViewGroup parent = (ViewGroup) activity.getWindow().getDecorView().getRootView();
                    ViewGroupOverlay overlay = parent.getOverlay();
                    overlay.clear();
                }
            }
        }

        @Override
        public void onDismiss () {
            //清楚背景变暗
            clearBackgroundDim();
        }

    }


}

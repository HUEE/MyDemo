package com.example.hwj.mydemo.utils;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import rx.functions.Action1;
import rx.functions.Action2;

/** Created by hwj on 2018/4/20. */
public class Keybord {
  /**
   * detect softInput keyboard showing state
   *
   * <p>sometimes when changing the input method, the keyboard state will quickly change from show
   * -> hide -> show, make sure you handle this situation yourself
   *
   * @param root should be the root view of the activity
   * @param action the action when keyboard show or dismiss
   */
  public void setKeyboardListener(final View root, final Action1<Boolean> action) {
    root.getViewTreeObserver()
        .addOnGlobalLayoutListener(
            new ViewTreeObserver.OnGlobalLayoutListener() {
              int rootHeight = -1;

              @Override
              public void onGlobalLayout() {
                if (rootHeight == -1) rootHeight = root.getMeasuredHeight();
                int heightDiff = rootHeight - root.getMeasuredHeight();
                if (heightDiff
                    > Metrics.dp(100)) { // if more than 100 pixels, its probably a keyboard...
                  action.call(true);
                } else if (heightDiff < -Metrics.dp(100)) {
                  action.call(false);
                }
                rootHeight = root.getMeasuredHeight();
              }
            });
  }

  public View setUpKeyboardDetectorLayout(
      final View topView,
      final View content,
      final Action2<Boolean, Integer> onChange,
      final Activity activity) {
    ViewGroup decor = (ViewGroup) activity.getWindow().getDecorView();
    topView.setPadding(
        0,
        (((AppCompatActivity) activity).getSupportActionBar() == null ? 0 : Metrics.dp(56))
            + Vu.statusBarHeight(),
        0,
        0);
    decor.addView(topView, 0);
    content.addOnLayoutChangeListener(
        new View.OnLayoutChangeListener() {
          public int contentHeight;
          public boolean keyboardShowing;
          public int keyboardHeight;

          @Override
          public void onLayoutChange(
              View vvvv,
              int left,
              int top,
              int right,
              int bottom,
              int oldLeft,
              int oldTop,
              int oldRight,
              int oldBottom) {
            int diff = contentHeight - (bottom - top);
            boolean keyboardShowing = diff > Metrics.dp(100); // status bar + action har is 73
            if (!keyboardShowing) {
              contentHeight = bottom - top;
              Object v = vvvv;
              int padding = 0;
              while (true) {
                if (v instanceof View && v != activity.getWindow().getDecorView()) {
                  padding += ((View) v).getTop();
                  v = ((View) v).getParent();
                } else {
                  break;
                }
              }
              if (topView.getMeasuredHeight() - padding != contentHeight
                  || padding != topView.getPaddingTop()) {
                topView.getLayoutParams().height = contentHeight + padding;
                topView.setPadding(0, padding, 0, 0);
                topView.requestLayout();
              }
            }
            if (keyboardShowing != this.keyboardShowing || (diff != keyboardHeight)) {
              this.keyboardShowing = keyboardShowing;
              if (this.keyboardShowing) {
                keyboardHeight = diff;
                //                                savedKeyboardHeight.put(keyboardHeight);
              }
              if (onChange != null) onChange.call(this.keyboardShowing, keyboardHeight);
            }
          }
        });
    return content;
  }
}

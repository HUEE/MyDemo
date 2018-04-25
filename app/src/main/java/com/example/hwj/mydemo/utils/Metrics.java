package com.example.hwj.mydemo.utils;

import android.content.Context;
import android.graphics.Point;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.WindowManager;

import com.example.hwj.mydemo.MyApplication;

public class Metrics {

  public static DisplayMetrics displayMetrics() {
    return MyApplication.me.getResources().getDisplayMetrics();
  }

  private static Point displayRealSize;

  @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
  public static Point displayRealSize() {
    if (displayRealSize == null) {
      displayRealSize = new Point();
    }

    WindowManager wm = (WindowManager) MyApplication.me.getSystemService(Context.WINDOW_SERVICE);
    wm.getDefaultDisplay().getRealSize(displayRealSize);
    return displayRealSize;
  }

  public static int dp(float p) {
    return (int) (displayMetrics().density * p);
  }

  public static int sp(int p) {
    return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, p, displayMetrics());
  }
}

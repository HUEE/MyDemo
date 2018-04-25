package com.example.hwj.mydemo.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.os.SystemClock;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.KeyCharacterMap;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hwj.mydemo.MyApplication;
import com.example.hwj.mydemo.utils.joor.Reflect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;

/** User: molikto Date: 12/29/14 Time: 18:25 */
public class Vu {

  public static final boolean getBigOverscroll() {
    try {
      return Build.BRAND.equals("Meizu")
          || Math.max(
                  ViewConfiguration.get(MyApplication.me).getScaledOverscrollDistance(),
                  ViewConfiguration.get(MyApplication.me).getScaledOverflingDistance())
              > Metrics.dp(12);
    } catch (Exception e) {
      //      MyApplication.me.logError(e);
    }
    return false;
  }

  public static final boolean BIG_OVERSCROLL = getBigOverscroll();

  public static void doubleClickListener(View v, final Action1<View> onDoubleClick) {
    v.setOnClickListener(
        new View.OnClickListener() {
          long lastClick = SystemClock.uptimeMillis();

          @Override
          public void onClick(View v) {
            if (Math.abs(SystemClock.uptimeMillis() - lastClick) < 500) {
              onDoubleClick.call(v);
            } else {
              lastClick = SystemClock.uptimeMillis();
            }
          }
        });
  }

  public static TextView textView(View view) {
    if (view instanceof ViewGroup) {
      ViewGroup v = (ViewGroup) view;
      for (int i = 0; i < v.getChildCount(); i++) {
        TextView r = textView(v.getChildAt(i));
        if (r != null) return r;
      }
    } else if (view instanceof TextView) {
      return (TextView) view;
    }
    return null;
  }

  public static int MP = ViewGroup.LayoutParams.MATCH_PARENT;
  public static int WC = ViewGroup.LayoutParams.WRAP_CONTENT;

  public static void drawText(Canvas canvas, String t) {
    TextPaint p = new TextPaint();
    p.setColor(Color.BLACK);
    p.setAlpha(255);
    p.setTextSize(Metrics.dp(48));
    canvas.drawText(t, 0, canvas.getHeight(), p);
  }

  public static void gone(View v, boolean show) {
    v.setVisibility(show ? View.VISIBLE : View.GONE);
  }

  public static void textOrGone(TextView v, CharSequence chars) {
    v.setText(chars);
    gone(v, !TextUtils.isEmpty(chars));
  }

  public static boolean visible(View v) {
    return v.getVisibility() == View.VISIBLE;
  }

  public static void visibility(View v, boolean show) {
    if (show) {
      if (v.getVisibility() != View.VISIBLE) v.setVisibility(View.VISIBLE);
    } else if (v.getVisibility() == View.VISIBLE) {
      v.setVisibility(View.INVISIBLE);
    }
  }

  public static void enabled(MenuItem item, boolean enabled) {
    item.setEnabled(enabled);
    Drawable d = item.getIcon();
    d.setAlpha((int) (255 * (!enabled ? 0.3f : 1)));
    item.setIcon(d);
  }

  public static void remove(View view) {
    if (view.getParent() instanceof ViewGroup) {
      ((ViewGroup) view.getParent()).removeView(view);
    }
  }

  public static void scale(View v, float scale) {
    v.setScaleX(scale);
    v.setScaleY(scale);
  }

  public static int visibleChildCount(ViewGroup g) {
    int i = 0;
    for (int j = 0; j < g.getChildCount(); j++) {
      if (g.getChildAt(j).getVisibility() == View.VISIBLE) {
        i++;
      }
    }
    return i;
  }

  public static boolean isAbove(View v, View c) {
    ArrayList<Integer> pathA = path(v);
    ArrayList<Integer> pathB = path(c);
    for (int i = 0; i < Math.min(pathA.size(), pathB.size()); i++) {
      if (pathA.get(i) < pathB.get(i)) {
        return true;
      }
    }
    return false;
  }

  public static ArrayList<Integer> path(View v) {
    ArrayList<Integer> arr = new ArrayList<>();
    View c = v;
    while (c != v.getRootView()) {
      ViewGroup p = (ViewGroup) c.getParent();
      arr.add(0, indexOf(p, c));
      c = p;
    }
    return arr;
  }

  public static int indexOf(View c) {
    return indexOf((ViewGroup) c.getParent(), c);
  }

  public static int indexOf(ViewGroup p, View c) {
    for (int i = 0; i < p.getChildCount(); i++) {
      if (p.getChildAt(i) == c) {
        return i;
      }
    }
    return -1;
  }

  public static void transformByPos(View view, Pos fromPos, Pos toPos) {
    transformByPos(view, fromPos, toPos, false);
  }

  public static void transformByPos(View view, Pos fromPos, Pos toPos, boolean keepSize) {
    view.setPivotX(0);
    view.setPivotY(0);
    float scaleX = 1f * fromPos.w / toPos.w;
    float scaleY = 1f * fromPos.h / toPos.h;
    if (keepSize) {
      if (scaleY >= scaleX) {
        view.setScaleX(scaleY);
        view.setScaleY(scaleY);
        view.setTranslationX(fromPos.x - toPos.x - (toPos.w * scaleY - fromPos.w) / 2);
        view.setTranslationY(fromPos.y - toPos.y);
      } else {
        view.setScaleX(scaleX);
        view.setScaleY(scaleX);
        view.setTranslationX(fromPos.x - toPos.x);
        view.setTranslationY(fromPos.y - toPos.y - (toPos.h * scaleX - fromPos.h) / 2);
      }
    } else {
      view.setScaleX(scaleX);
      view.setScaleY(scaleY);
      view.setTranslationX(fromPos.x - toPos.x);
      view.setTranslationY(fromPos.y - toPos.y);
    }
  }

  public static void noClipUntilContent(View view) {
    Object v = view.getParent();
    while (v instanceof ViewGroup && ((ViewGroup) v).getId() != android.R.id.content) {
      ((ViewGroup) v).setClipChildren(false);
      ((ViewGroup) v).setClipToPadding(false);
      v = ((ViewGroup) v).getParent();
    }
  }

  public static void noClipAll(View view) {
    Object v = view.getParent();
    while (v instanceof ViewGroup) {
      ((ViewGroup) v).setClipChildren(false);
      ((ViewGroup) v).setClipToPadding(false);
      v = ((ViewGroup) v).getParent();
    }
  }

  public static void setDrawableEnabledState(Drawable d, boolean b) {
    if (d == null) return;
    if (d instanceof LayerDrawable) {
      LayerDrawable layer = (LayerDrawable) d;
      for (int i = 0; i < layer.getNumberOfLayers(); i++) {
        setDrawableEnabledState(layer.getDrawable(i), b);
      }
    } else if (d instanceof StateListDrawable) {
      StateListDrawable sld = (StateListDrawable) d;
      int[] state = sld.getState();
      if (b && Arrays.binarySearch(state, android.R.attr.state_enabled) < 0) {
        state = Arrays.copyOf(state, state.length + 1);
        state[state.length - 1] = android.R.attr.state_enabled;
      } else if (!b && Arrays.binarySearch(state, android.R.attr.state_enabled) >= 0) {
        int[] newState = new int[state.length - 1];
        int io = 0;
        int in = 0;
        while (io < state.length) {
          if (state[io] != android.R.attr.state_enabled) {
            newState[in] = state[io];
            in++;
          }
          io++;
        }
        state = newState;
      }
      sld.setState(state);
    }
  }

  public static void setBackgroundEnabledState(View v, boolean b) {
    setDrawableEnabledState(v.getBackground(), b);
  }

  public static void downAndCancelTouchEvent(View scroll) {
    try {
      long time = SystemClock.uptimeMillis();
      scroll.dispatchTouchEvent(
          MotionEvent.obtain(
              time,
              time,
              MotionEvent.ACTION_DOWN,
              scroll.getWidth() / 2,
              scroll.getHeight() / 2,
              0));
      scroll.dispatchTouchEvent(
          MotionEvent.obtain(
              time,
              time,
              MotionEvent.ACTION_CANCEL,
              scroll.getWidth() / 2,
              scroll.getHeight() / 2,
              0));
    } catch (Exception e) {
    }
  }

  public static void downTouchEvent(View scroll) {
    try {
      long time = SystemClock.uptimeMillis();
      scroll.dispatchTouchEvent(
          MotionEvent.obtain(
              time,
              time,
              MotionEvent.ACTION_DOWN,
              scroll.getWidth() / 2,
              scroll.getHeight() / 2,
              0));
      scroll.dispatchTouchEvent(
          MotionEvent.obtain(
              time,
              time,
              MotionEvent.ACTION_MOVE,
              scroll.getWidth() / 2,
              scroll.getHeight() / 2,
              0));
    } catch (Exception e) {

    }
  }

  public static void downAndUpEvent(View scroll) {
    try {
      long time = SystemClock.uptimeMillis();
      scroll.dispatchTouchEvent(
          MotionEvent.obtain(
              time,
              time,
              MotionEvent.ACTION_DOWN,
              scroll.getWidth() / 2,
              scroll.getHeight() / 2,
              0));
      scroll.dispatchTouchEvent(
          MotionEvent.obtain(
              time, time, MotionEvent.ACTION_UP, scroll.getWidth() / 2, scroll.getHeight() / 2, 0));
    } catch (Exception e) {

    }
  }

  public static void reLayoutNowWrapContentWithOldLeftTop(View view) {
    view.measure(Measure.unspecified(), Measure.unspecified());
    view.layout(
        view.getLeft(),
        view.getTop(),
        view.getLeft() + view.getMeasuredWidth(),
        view.getTop() + view.getMeasuredHeight());
  }

  public static void reLayoutNowByExactOriginalSize(View view) {
    view.measure(Measure.exactly(view.getWidth()), Measure.exactly(view.getHeight()));
    view.layout(view.getLeft(), view.getTop(), view.getRight(), view.getBottom());
  }

  public static void ensureFocus(View v) {
    if (!v.hasFocus()) v.requestFocus();
  }

  public static void imageSrc(ImageView view, int i) {
    Drawable d = MyApplication.me.getResources().getDrawable(i);
    if (view.getDrawable() == null || view.getDrawable() != d) {
      view.setImageDrawable(d);
    }
  }

  public static void addPadding(View v, int i, int i1, int dp, int i2) {
    v.setPadding(
        v.getPaddingLeft() + i,
        v.getPaddingTop() + i1,
        v.getPaddingRight() + dp,
        v.getPaddingBottom() + i2);
  }

  public static Bitmap bitmap(ImageView image) {
    if (image.getDrawable() instanceof BitmapDrawable) {
      return ((BitmapDrawable) image.getDrawable()).getBitmap();
    } else {
      return null;
    }
  }

  public static void addCompoundDrawables(
      TextView view, Drawable left, Drawable top, Drawable right, Drawable bottom) {
    Drawable[] old = view.getCompoundDrawables();
    if (old == null) {
      view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    } else {
      view.setCompoundDrawablesWithIntrinsicBounds(
          left == null ? old[0] : left,
          top == null ? old[1] : top,
          right == null ? old[2] : right,
          bottom == null ? old[3] : bottom);
    }
  }

  public static void addCompoundDrawableLeft(TextView view, Drawable drawable) {
    addCompoundDrawables(view, drawable, null, null, null);
  }

  static int statusBarHeight = -1;

  public static int statusBarHeight() {
    if (statusBarHeight == -1) {
      Context c = MyApplication.me;
      int resourceId = c.getResources().getIdentifier("status_bar_height", "dimen", "android");
      if (resourceId > 0) {
        statusBarHeight = c.getResources().getDimensionPixelSize(resourceId);
      }
    }
    return statusBarHeight;
  }

  public static void marginRight(View v, int px) {
    ViewGroup.LayoutParams lp = v.getLayoutParams();
    if (lp instanceof ViewGroup.MarginLayoutParams) {
      ((ViewGroup.MarginLayoutParams) lp).rightMargin = px;
    }
    v.setLayoutParams(lp);
  }

  public static void marginLeft(View v, int px) {
    ViewGroup.LayoutParams lp = v.getLayoutParams();
    if (lp instanceof ViewGroup.MarginLayoutParams) {
      ((ViewGroup.MarginLayoutParams) lp).leftMargin = px;
    }
    v.setLayoutParams(lp);
  }

  public static void paddings(View login_new, int dp) {
    login_new.setPadding(dp, dp, dp, dp);
  }

  public static class Pos {
    public int x;
    public int y;
    public int w;
    public int h;
  }

  public static Pos pos(View v) {
    View root = ((Activity) v.getContext()).getWindow().getDecorView();
    return pos(v, root);
  }

  public static Pos pos(View v, View root) {
    Pos pos = new Pos();
    View c = v;
    pos.w = v.getWidth();
    pos.h = v.getHeight();
    while (c != null && c != root) {
      pos.x += c.getLeft() - c.getScrollX() + c.getTranslationX();
      pos.y += c.getTop() - c.getScrollY() + c.getTranslationY();
      c = (View) c.getParent();
    }
    return pos;
  }

  public static int relativeTop(View c, View root) {
    int top = 0;
    while (c != root) {
      top += c.getTop();
      c = (View) c.getParent();
    }
    return top;
  }

  public static int relativeBottom(View c, View root) {
    int bottom = 0;
    while (c != root) {
      bottom += c.getBottom();
      c = (View) c.getParent();
    }
    return bottom;
  }

  public static void visibilityByText(TextView t) {
    gone(t, t.getText() != null && t.getText().length() > 0);
  }

  public static boolean text(TextView t, String s) {
    if (!t.getText().equals(s)) {
      t.setText(s);
      return true;
    }
    return false;
  }

  public static int screenWidth() {
    return MyApplication.me.getResources().getDisplayMetrics().widthPixels;
  }

  public static int screenHeight() {
    return MyApplication.me.getResources().getDisplayMetrics().heightPixels;
  }

  public static boolean inBound(View view, MotionEvent event) {
    return event.getX() > 0
        && event.getY() > 0
        && event.getX() < view.getWidth()
        && event.getY() < view.getHeight();
  }

  public static void enableAndClickable(View v, boolean b) {
    v.setEnabled(b);
    v.setClickable(b);
  }

  public static void paddingTop(View v, int padding) {
    v.setPadding(v.getPaddingLeft(), padding, v.getPaddingRight(), v.getPaddingBottom());
  }

  public static void paddingBottom(View v, int padding) {
    v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), v.getPaddingRight(), padding);
  }

  public static void paddingRight(View v, int padding) {
    v.setPadding(v.getPaddingLeft(), v.getPaddingTop(), padding, v.getPaddingBottom());
  }

  public static void paddingLeft(View v, int padding) {
    v.setPadding(padding, v.getPaddingTop(), v.getPaddingRight(), v.getPaddingBottom());
  }


  public static View childsFindRecursive(ViewGroup v, Func1<View, Boolean> filter) {
    return childsFindRecursiveInner(v, filter);
  }

  public static void getChildRectRaw(View v, Rect window, Rect temp) {
    v.getGlobalVisibleRect(temp);
    temp.top = Math.abs(temp.top);
    temp.bottom = Math.abs(temp.bottom);
    temp.left = Math.abs(temp.left);
    temp.right = Math.abs(temp.right);
    if (temp.top > temp.bottom) {
      int t = temp.top;
      temp.top = temp.bottom;
      temp.bottom = t;
    }
    if (temp.left > temp.right) {
      int t = temp.left;
      temp.left = temp.right;
      temp.right = t;
    }
    temp.top += window.top;
    temp.left += window.left;
    temp.bottom += window.top;
    temp.right += window.left;
  }

  public static View childsFindRecursiveInner(View v, Func1<View, Boolean> filter) {
    if (filter.call(v)) {
      return v;
    } else if (v instanceof ViewGroup) {
      ViewGroup g = (ViewGroup) v;
      for (int i = 0; i < g.getChildCount(); i++) {
        View k = childsFindRecursiveInner(g.getChildAt(i), filter);
        if (k != null) {
          return k;
        }
      }
      return null;
    }
    return null;
  }

  public static Bitmap drawViewToBitmap(View view, int downSampling) {
    float scale = 1f / downSampling;
    int vwidth = view.getMeasuredWidth();
    int vheight = view.getMeasuredHeight();
    int bmpWidth = (int) (vwidth * scale);
    int bmpHeight = (int) (vheight * scale);
    Bitmap dest = Bitmap.createBitmap(bmpWidth, bmpHeight, Bitmap.Config.ARGB_8888);
    Canvas c = new Canvas(dest);
    if (downSampling > 1) {
      c.scale(scale, scale);
    }
    view.draw(c);
    return dest;
  }


  public static ImageView imageView(View v) {
    if (v instanceof ViewGroup) {
      ViewGroup g = (ViewGroup) v;
      for (int i = 0; i < g.getChildCount(); i++) {
        ImageView image = imageView(g.getChildAt(i));
        if (image != null) {
          return image;
        }
      }
    } else if (v instanceof ImageView) {
      return (ImageView) v;
    }
    return null;
  }

  public static ColorFilter saturationColorFilter(float i) {
    ColorMatrix matrix = new ColorMatrix();
    matrix.setSaturation(i);
    return new ColorMatrixColorFilter(matrix);
  }

  public static View findVisibleViewBy(Activity act, Func1<View, Boolean> pred) {
    View root = act.getWindow().getDecorView();
    return findVisibleViewBy(root, pred);
  }

  private static View findVisibleViewBy(View root, Func1<View, Boolean> pred) {
    if (root.getVisibility() == View.VISIBLE) {
      if (pred.call(root)) {
        return root;
      } else if (root instanceof ViewGroup) {
        ViewGroup g = (ViewGroup) root;
        for (int i = 0; i < g.getChildCount(); i++) {
          View v = findVisibleViewBy(g.getChildAt(i), pred);
          if (v != null) return v;
        }
      }
    }
    return null;
  }

  public static void callOnClick(View v) {
    if (Build.VERSION.SDK_INT >= 15) {
      v.callOnClick();
    } else {
      Object o = Reflect.on(v).field("mListenerInfo").get();
      if (o != null) {
        Object l = Reflect.on(o).field("mOnClickListener");
        if (l != null) {
          ((View.OnClickListener) l).onClick(v);
        }
      }
    }
  }

  public static void drawSingleDrawableCentered(Canvas canvas, Drawable drawable) {
    int mw = canvas.getWidth() / 2;
    int mh = canvas.getHeight() / 2;
    int playSize = Math.min(Math.min(drawable.getIntrinsicWidth() / 2, mw), mh);
    drawable.setBounds(mw - playSize, mh - playSize, mw + playSize, mh + playSize);
    drawable.draw(canvas);
  }

  public static Drawable replaceWindowContentOverlay(Activity act, Drawable d) {
    View contentView = act.findViewById(android.R.id.content);

    // Make sure it's a valid instance of a FrameLayout
    if (contentView instanceof FrameLayout) {
      FrameLayout fl = (FrameLayout) contentView;
      Drawable res = fl.getForeground();
      fl.setForeground(d);
      return res;
    }
    return null;
  }

  public static boolean isNavigationBarAvailable() {
    boolean hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK);
    boolean hasHomeKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_HOME);
    return (!(hasBackKey && hasHomeKey));
  }
}

package com.example.hwj.mydemo.anim;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/** Created by hwj on 2018/4/26. */
public class MyAnimView extends View {
  public static final int RADIUS = 50;
  private MyPoint currentPoint;

  private Paint mPaint;

  private String color;

  public String getColor() {
    return color;
  }

  public void setColor(String color) {
    this.color = color;
    mPaint.setColor(Color.parseColor(color));
    invalidate();
  }

  public MyAnimView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    mPaint.setColor(Color.BLUE);
  }

  @Override
  protected void onDraw(Canvas canvas) {
    if (currentPoint == null) {
      currentPoint = new MyPoint(RADIUS, RADIUS);
      drawCircle(canvas);
      startAnimation();
    } else {
      drawCircle(canvas);
    }
  }

  private void drawCircle(Canvas canvas) {
    float x = currentPoint.getX();
    float y = currentPoint.getY();
    canvas.drawCircle(x, y, RADIUS, mPaint);
  }

  private void startAnimation() {
    MyPoint startPoint = new MyPoint(RADIUS, RADIUS);
    MyPoint endPoint = new MyPoint(this.getWidth() - RADIUS, this.getHeight() - RADIUS);
    ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(), startPoint, endPoint);
    anim.addUpdateListener(
        animation -> {
          currentPoint = (MyPoint) animation.getAnimatedValue();
          invalidate();
        });
    ObjectAnimator objectAnimator =
        ObjectAnimator.ofObject(this, "color", new ColorEvaluator(), "#0000FF", "#FF0000");
    AnimatorSet animatorSet = new AnimatorSet();
    animatorSet.play(anim).with(objectAnimator);
    animatorSet.setDuration(5000);
    animatorSet.start();
  }
}

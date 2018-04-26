package com.example.hwj.mydemo.anim;

import android.animation.TypeEvaluator;

/** Created by hwj on 2018/4/26. */
public class PointEvaluator implements TypeEvaluator {
  @Override
  public Object evaluate(float fraction, Object startValue, Object endValue) {
    MyPoint startPoint = (MyPoint) startValue;
    MyPoint endPoint = (MyPoint) endValue;
    int x = (int) (startPoint.getX() + fraction * (endPoint.getX() - startPoint.getX()));
    int y = (int) (startPoint.getY() + fraction * (endPoint.getY() - startPoint.getY()));
    return new MyPoint(x, y);
  }
}

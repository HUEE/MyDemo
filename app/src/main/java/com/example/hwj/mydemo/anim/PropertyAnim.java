package com.example.hwj.mydemo.anim;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;

/** 属性动画相关 Created by hwj on 2018/4/17. */
public class PropertyAnim {
  public void valueOfInt(int... value) {
    ValueAnimator.ofInt(value);
  }

  public void valueOfFloat(float... value) {
    ValueAnimator.ofFloat(value);
  }

  public void valueOfObject(TypeEvaluator evaluator, Object... value) {
    ValueAnimator.ofObject(evaluator, value);
  }
}

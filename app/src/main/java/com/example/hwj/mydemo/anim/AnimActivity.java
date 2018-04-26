package com.example.hwj.mydemo.anim;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ValueAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.hwj.mydemo.R;
import com.example.hwj.mydemo.base.BaseActivity;

import butterknife.BindView;

/** Created by hwj on 2018/4/26. */
public class AnimActivity extends BaseActivity {
  @BindView(R.id.bt_anim)
  TextView bt_anim;

  @BindView(R.id.im_anim)
  ImageButton im_anim;

  @Override
  protected int setLayout() {
    return R.layout.anim_main_activity;
  }

  @Override
  protected void init() {
    // 补间动画
    //    Animation anim = AnimationUtils.loadAnimation(this, R.anim.main_bt_anim);
    //    bt_anim.startAnimation(anim);

    // 帧动画
    AnimationDrawable animationDrawable = (AnimationDrawable) im_anim.getBackground();
    animationDrawable.start();

    // 属性动画
    ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 10, 4);
    valueAnimator.setDuration(1000);
    valueAnimator.addUpdateListener(
        animation -> {
          Log.d(TAG, animation.getAnimatedValue().toString());
        });
    valueAnimator.start();

    //
    //    ObjectAnimator objectAnimator1 = ObjectAnimator.ofFloat(im_anim, "alpha", 1f, 0f, 1f);
    //    objectAnimator1.setDuration(3000);
    //    ObjectAnimator objectAnimator2 = ObjectAnimator.ofFloat(bt_anim, "rotation", 0f, 360f);
    //    objectAnimator2.setDuration(5000);
    //    AnimatorSet animatorSet = new AnimatorSet();
    //    animatorSet.play(objectAnimator1).after(objectAnimator2);
    //    animatorSet.addListener(new AnimatorListenerAdapter() {
    //      @Override
    //      public void onAnimationEnd(Animator animation) {
    //        super.onAnimationEnd(animation);
    //      }
    //
    //      @Override
    //      public void onAnimationStart(Animator animation) {
    //        super.onAnimationStart(animation);
    //      }
    //    });
    //    animatorSet.start();

    Animator animator = AnimatorInflater.loadAnimator(this, R.animator.bt_animator);
    animator.setTarget(bt_anim);
    animator.start();
  }
}

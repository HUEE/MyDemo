 package com.example.hwj.mydemo.anim;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import com.example.hwj.mydemo.base.BaseView;
import com.example.hwj.mydemo.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

/** Created by hwj on 2018/4/28. */
public class AlterCircleView extends BaseView {
  private int mNumber = 5;

  // 圆的属性
  private int mRadius = ScreenUtils.getInstance(getContext()).dp2px(10);
  private Circle mCurrCircle, mNextCircle;
  private Circle[] mCircles = new Circle[mNumber];
  private List<Path> mPaths = new ArrayList<>();

  // 测量围绕的中心圆的属性
  float distance = 0.0f;
  float pos[] = new float[2];
  float tan[] = new float[2];

  private PathMeasure measure;
  private int index = 0;

  // 黑色圆形， 去为 false  ，回来 为 true
  private boolean mBack = false;

  public AlterCircleView(Context context) {
    super(context);
  }

  public AlterCircleView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
    List<Circle> list = new CircleFactory(mRadius).generateCircles(mNumber);
    for (int i = 0; i < list.size(); i++) {
      mCircles[i] = list.get(i);
    }
    mCurrCircle = mCircles[0]; // 黑色圆
    mNextCircle = mCircles[1];
    measure = new PathMeasure();
  }

  @Override
  protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    int widthMode = MeasureSpec.getMode(widthMeasureSpec);
    int heightMode = MeasureSpec.getMode(heightMeasureSpec);

    int width = MeasureSpec.getSize(widthMeasureSpec);
    int height = MeasureSpec.getSize(heightMeasureSpec);

    int measuredWidth = width, measuredHeight = height;
    if (widthMode != MeasureSpec.EXACTLY) {
      measuredWidth = mRadius * 2 * mNumber + mRadius * (mNumber - 1) + mMargin * 2 + mPadding * 2;
    }
    if (heightMode != MeasureSpec.EXACTLY) {
      // 当两个圆，交替到 垂直状态，+ 2个半径 也就是 最大的高度。
      int r = mNextCircle.getX() - mCurrCircle.getX();
      measuredHeight = r + mRadius * 2 + mMargin + mPadding;
    }
    setMeasuredDimension(measuredWidth, measuredHeight);
  }

  @Override
  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    // 两个圆形 中间的 旋转圆，Path 路径
    for (int i = 0; i < mCircles.length - 1; i++) {
      Circle currCircle = mCircles[i];
      Circle nextCircle = mCircles[i + 1];
      int r = (nextCircle.getX() + currCircle.getX()) / 2;
      int radius = r - currCircle.getX();
      Path path = new Path();
      path.addCircle(r, 0, radius, Path.Direction.CW);
      mPaths.add(path);
    }
  }

  @Override
  protected void onDraw(Canvas canvas) {
    super.onDraw(canvas);
    canvas.translate(mMargin + mPadding + mRadius, mViewHeight);
    canvas.save();
    for (Circle c : mCircles) {
      boolean solid = c.getSolid();
      mPaint.setStyle(Paint.Style.STROKE);
      if (solid) {
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
      }
      canvas.drawCircle(c.getX(), c.getY(), c.getRadius(), mPaint);
    }
    // index 来 设置 要 测量的path
    measure.setPath(mPaths.get(index), false);
    // 获取当前 点的 xy坐标，以及正切（此处没用）
    measure.getPosTan(distance, pos, tan);

    mNextCircle.setX((int) pos[0]);
    mNextCircle.setY((int) pos[1]);

    // 黑色圆-去：
    if (!mBack) {
      if (distance < measure.getLength() / 2) {
        float f = measure.getLength() / 2 + distance;
        float pos[] = new float[2];
        float tan[] = new float[2];
        measure.getPosTan(f, pos, tan);
        mCurrCircle.setX((int) pos[0]);
        mCurrCircle.setY((int) pos[1]);
        distance += 2;
      } else {
        // 交换2个圆位置
        Circle temp = mCircles[index];
        mCircles[index] = mCircles[index + 1];
        mCircles[index + 1] = temp;
        if (index < mPaths.size() - 1) {
          index++;
          distance = 0;
          mCurrCircle = mCircles[index];
          mNextCircle = mCircles[index + 1];
        } else {
          distance = measure.getLength() / 2;
          mBack = true;
        }
      }

    }
    // 黑色圆-回：
    else {
      if (distance < measure.getLength()) {
        float f = distance - measure.getLength() / 2;
        float pos[] = new float[2];
        float tan[] = new float[2];
        measure.getPosTan(f, pos, tan);
        mCurrCircle.setX((int) pos[0]);
        mCurrCircle.setY((int) pos[1]);
        distance += 2;
      } else {
        Circle temp = mCircles[index];
        mCircles[index] = mCircles[index + 1];
        mCircles[index + 1] = temp;
        if (index > 0) {
          index--;
          distance = measure.getLength() / 2;
          mCurrCircle = mCircles[index + 1];
          mNextCircle = mCircles[index];
        } else {
          distance = 0;
          mBack = false;
        }
      }
    }
    postInvalidate();
    canvas.restore();
  }
}

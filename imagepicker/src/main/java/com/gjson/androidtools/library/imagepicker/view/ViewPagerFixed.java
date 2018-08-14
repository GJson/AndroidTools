package com.gjson.androidtools.library.imagepicker.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by gjson on 2018/6/22.
 * 描    述：修复图片在ViewPager控件中缩放报错的BUG
 */
public class ViewPagerFixed extends ViewPager {

  public ViewPagerFixed(Context context) {
    super(context);
  }

  public ViewPagerFixed(Context context, AttributeSet attrs) {
    super(context, attrs);
  }

  @Override public boolean onTouchEvent(MotionEvent ev) {
    try {
      return super.onTouchEvent(ev);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    return false;
  }

  @Override public boolean onInterceptTouchEvent(MotionEvent ev) {
    try {
      return super.onInterceptTouchEvent(ev);
    } catch (IllegalArgumentException ex) {
      ex.printStackTrace();
    }
    return false;
  }
}

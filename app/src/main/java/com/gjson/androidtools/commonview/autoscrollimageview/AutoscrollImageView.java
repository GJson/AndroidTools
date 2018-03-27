package com.gjson.androidtools.commonview.autoscrollimageview;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.os.Build;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import com.gjson.androidtools.R;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Math.abs;
import static java.util.Collections.singletonList;

/**
 * Created by gjson on 2018/3/27.
 * Name AutoscrollImageView
 * Version 1.0
 */

public class AutoscrollImageView extends View {
  public static AutoScrollImageViewBitmapLoader BITMAP_LOADER =
      new AutoScrollImageViewBitmapLoader() {
        @Override public Bitmap loadBitmap(Context context, int resourceId) {
          return BitmapFactory.decodeResource(context.getResources(), resourceId);
        }
      };

  private List<Bitmap> bitmaps;
  private float speed;
  private int[] scene;
  private int arrayIndex = 0;
  private int maxBitmapHeight = 100;

  private Rect clipBounds = new Rect();
  private float offset = 0;

  private boolean isStarted;

  public AutoscrollImageView(Context context, AttributeSet attrs) {
    super(context, attrs);
    TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ParallaxView, 0, 0);
    int initialState = 0;
    try {
      initialState = ta.getInt(R.styleable.ParallaxView_initialState, 0);
      speed = ta.getDimension(R.styleable.ParallaxView_speed, 10);
      int sceneLength = ta.getInt(R.styleable.ParallaxView_sceneLength, 1000);
      final int randomnessResourceId = ta.getResourceId(R.styleable.ParallaxView_randomness, 0);
      int[] randomness = new int[0];
      if (randomnessResourceId > 0) {
        randomness = getResources().getIntArray(randomnessResourceId);
      }

      int type =
          isInEditMode() ? TypedValue.TYPE_STRING : ta.peekValue(R.styleable.ParallaxView_src).type;
      if (type == TypedValue.TYPE_REFERENCE) {
        int resourceId = ta.getResourceId(R.styleable.ParallaxView_src, 0);
        TypedArray typedArray = getResources().obtainTypedArray(resourceId);
        try {
          int bitmapsSize = 0;
          for (int r : randomness) {
            bitmapsSize += r;
          }

          bitmaps = new ArrayList<>(Math.max(typedArray.length(), bitmapsSize));

          for (int i = 0; i < typedArray.length(); i++) {
            int multiplier = 1;
            if (randomness.length > 0 && i < randomness.length) {
              multiplier = Math.max(1, randomness[i]);
            }

            Bitmap bitmap = BITMAP_LOADER.loadBitmap(getContext(), typedArray.getResourceId(i, 0));
            for (int m = 0; m < multiplier; m++) {
              bitmaps.add(bitmap);
            }

            maxBitmapHeight = Math.max(bitmap.getHeight(), maxBitmapHeight);
          }

          Random random = new Random();
          this.scene = new int[sceneLength];
          for (int i = 0; i < this.scene.length; i++) {
            this.scene[i] = random.nextInt(bitmaps.size());
          }
        } finally {
          typedArray.recycle();
        }
      } else if (type == TypedValue.TYPE_STRING) {
        final Bitmap bitmap = BITMAP_LOADER.loadBitmap(getContext(),
            ta.getResourceId(R.styleable.ParallaxView_src, 0));
        if (bitmap != null) {
          bitmaps = singletonList(bitmap);
          scene = new int[] { 0 };
          maxBitmapHeight = bitmaps.get(0).getHeight();
        } else {
          bitmaps = Collections.emptyList();
        }
      }
    } finally {
      ta.recycle();
    }

    if (initialState == 0) {
      start();
    }
  }

  @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), maxBitmapHeight);
  }

  @Override public void onDraw(Canvas canvas) {
    if (!isInEditMode()) {
      super.onDraw(canvas);
      if (canvas == null || bitmaps.isEmpty()) {
        return;
      }

      canvas.getClipBounds(clipBounds);

      while (offset <= -getBitmap(arrayIndex).getWidth()) {
        offset += getBitmap(arrayIndex).getWidth();
        arrayIndex = (arrayIndex + 1) % scene.length;
      }

      float left = offset;
      for (int i = 0; left < clipBounds.width(); i++) {
        Bitmap bitmap = getBitmap((arrayIndex + i) % scene.length);
        int width = bitmap.getWidth();
        canvas.drawBitmap(bitmap, getBitmapLeft(width, left), 0, null);
        left += width;
      }

      if (isStarted && speed != 0) {
        offset -= abs(speed);
        postInvalidateOnAnimation();
      }
    }
  }

  private Bitmap getBitmap(int sceneIndex) {
    return bitmaps.get(scene[sceneIndex]);
  }

  private float getBitmapLeft(float layerWidth, float left) {
    if (speed < 0) {
      return clipBounds.width() - layerWidth - left;
    } else {
      return left;
    }
  }

  /**
   * Start the animation
   */
  @TargetApi(Build.VERSION_CODES.JELLY_BEAN)  public void start() {
    if (!isStarted) {
      isStarted = true;
      postInvalidateOnAnimation();
    }
  }

  /**
   * Stop the animation
   */
  public void stop() {
    if (isStarted) {
      isStarted = false;
      invalidate();
    }
  }

  @TargetApi(Build.VERSION_CODES.JELLY_BEAN) public void setSpeed(float speed) {
    this.speed = speed;
    if (isStarted) {
      postInvalidateOnAnimation();
    }
  }
}
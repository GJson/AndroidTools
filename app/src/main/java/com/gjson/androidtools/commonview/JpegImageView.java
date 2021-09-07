package com.gjson.androidtools.commonview;

/**
 * 创建时间: 2021/09/07 16:40 <br>
 * 作者: Gjson <br>
 * 描述:JpegImageView
 */

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.util.AttributeSet;
import com.jpegkit.Jpeg;
import org.jetbrains.annotations.Nullable;

public class JpegImageView extends android.support.v7.widget.AppCompatImageView {
  private Jpeg mJpeg;
  private int mInSampleSize = 1;
  private Bitmap mBitmap;

  public JpegImageView(Context context) {
    super(context);
  }

  public JpegImageView(Context context, @Nullable AttributeSet attrs) {
    super(context, attrs);
  }

  public JpegImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
  }

  protected void onSizeChanged(int w, int h, int oldw, int oldh) {
    super.onSizeChanged(w, h, oldw, oldh);
    this.adjustSize(w, h);
  }

  private void adjustSize(int w, int h) {
    if (w > 0 && h > 0 && this.mJpeg != null) {
      int jpegWidth = this.mJpeg.getWidth();
      int jpegHeight = this.mJpeg.getHeight();
      if ((float) (jpegWidth * jpegHeight) > (float) (w * h) * 1.5F) {
        float widthRatio = (float) w / (float) jpegWidth;
        float heightRatio = (float) h / (float) jpegHeight;
        Options options = new Options();
        if (widthRatio <= heightRatio) {
          options.inSampleSize = (int) (1.0F / widthRatio);
        } else {
          options.inSampleSize = (int) (1.0F / heightRatio);
        }

        if (options.inSampleSize != this.mInSampleSize || this.mBitmap == null) {
          this.mInSampleSize = options.inSampleSize;
          byte[] jpegBytes = this.mJpeg.getJpegBytes();
          this.mBitmap = BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.length, options);
          this.setImageBitmap(this.mBitmap);
        }
      } else if (this.mBitmap == null) {
        byte[] jpegBytes = this.mJpeg.getJpegBytes();
        this.mBitmap = BitmapFactory.decodeByteArray(jpegBytes, 0, jpegBytes.length);
        this.setImageBitmap(this.mBitmap);
      }
    }
  }

  public void setJpeg(Jpeg jpeg) {
    this.setImageBitmap((Bitmap) null);
    this.mJpeg = jpeg;
    this.mInSampleSize = 1;
    this.mBitmap = null;
    this.adjustSize(this.getWidth(), this.getHeight());
  }
}

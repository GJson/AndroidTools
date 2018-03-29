package com.gjson.androidtools.commonview.autoscrollimageview;

import android.content.Context;
import android.graphics.Bitmap;

/**
 * Created by gjson on 2018/3/27.
 * Name AutoScrollImageViewBitmapLoader
 * Version 1.0
 */

public interface AutoScrollImageViewBitmapLoader {

  Bitmap loadBitmap(Context context, int resourceId);
}

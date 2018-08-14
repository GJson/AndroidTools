package com.gjson.androidtools.library.imagepicker.util;

import android.content.Context;

/**
 * 用于解决provider冲突的util
 *
 * Created by gjson on 2018/6/22.
 */

public class ProviderUtil {

  public static String getFileProviderName(Context context) {
    return context.getPackageName() + ".provider";
  }
}

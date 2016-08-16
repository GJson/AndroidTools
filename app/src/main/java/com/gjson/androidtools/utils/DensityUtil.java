package com.gjson.androidtools.utils;

import android.content.Context;

/**
 * Created by gjson on 16/7/20.
 * Name DensityUtil
 * Version 1.0
 */
public class DensityUtil {


    /**
     * 将单位为dip的值转换成单位为px的值
     *
     * @param context Context
     * @param dipValue dip值
     * @return px值
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

}

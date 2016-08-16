package com.gjson.androidtools.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by gjson on 16/8/3.
 * Name AutoListView
 *
 * ScrollView中嵌套ListView，重写ListView计算高度
 *
 * Version 1.0
 */
public class AutoListView extends ListView{


    public AutoListView(Context context) {
        super(context);
    }

    public AutoListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AutoListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,

                MeasureSpec.AT_MOST);

        super.onMeasure(widthMeasureSpec, expandSpec);

    }
}

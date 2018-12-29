package com.gjson.androidtools.commonview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by gjson on 2018/11/18.
 * Name AutoListView
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

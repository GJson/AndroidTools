package com.gjson.androidtools.activity;

import android.os.Bundle;

import com.gjson.androidtools.R;
import com.gjson.androidtools.commonview.BezierView;
import com.gjson.androidtools.utils.ScreenUtil;

/**
 * Created by gjson on 16/9/6.
 * Name BezierActivity
 * Version 1.0
 */
public class BezierActivity extends BaseActivity {

    private BezierView mBezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bezier;
    }

    @Override
    protected void setupView() {

        mBezierView = getView(R.id.bezier_view);
    }

    @Override
    protected void initializedData() {

        mBezierView.setDefaultResourceImgList();

        mBezierView.startAnimation(ScreenUtil.getScreenWidth(mContext), ScreenUtil.getScreenHeight(mContext), 1000);
    }
}

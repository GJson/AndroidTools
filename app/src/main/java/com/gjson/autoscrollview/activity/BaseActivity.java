package com.gjson.autoscrollview.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

/**
 * Created by gjson on 16/7/26.
 * Name BaseActivity
 * Version 1.0
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;

    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }

    /**
     * 布局文件ID
     *
     * @return
     */
    protected abstract int getLayoutId();

    /**
     * 初始化组件
     */
//    protected abstract void setupView();

    /**
     * 初始化数据
     */
//    protected abstract void initializedData();
}

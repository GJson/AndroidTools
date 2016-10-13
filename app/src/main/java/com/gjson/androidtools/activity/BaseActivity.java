package com.gjson.androidtools.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import com.gjson.androidtools.autocheckpermisson.PermissionActivity;

/**
 * Created by gjson on 16/7/26.
 * Name BaseActivity
 * Version 1.0
 */
public abstract class BaseActivity extends PermissionActivity {
    protected Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(getLayoutId());
        setupView();
        initializedData();

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
    protected abstract void setupView();

    /**
     * 初始化数据
     */
    protected abstract void initializedData();
}

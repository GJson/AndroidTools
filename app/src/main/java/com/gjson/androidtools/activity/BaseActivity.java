package com.gjson.androidtools.activity;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gjson.androidtools.autocheckpermisson.PermissionActivity;
import com.gjson.androidtools.utils.LogUtil;

import java.lang.reflect.Method;

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


    /**
     * 判断MIUI的悬浮窗权限
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public boolean isMiuiFloatWindowOpAllowed(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24);  // AppOpsManager.OP_SYSTEM_ALERT_WINDOW
        } else {
            if ((context.getApplicationInfo().flags & 1 << 27) == 1 << 27) {
                return true;
            } else {
                return false;
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;

        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {

                Class<?> spClazz = Class.forName(manager.getClass().getName());
                Method method = manager.getClass().getDeclaredMethod("checkOp", int.class, int.class, String.class);
                int property = (Integer) method.invoke(manager, op,
                        Binder.getCallingUid(), context.getPackageName());
                Log.e("checkOp", AppOpsManager.MODE_ALLOWED + " invoke " + property);

                if (AppOpsManager.MODE_ALLOWED == property) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                LogUtil.e("checkOp", e.getMessage());
            }
        } else {
            LogUtil.e("checkOp", "Below API 19 cannot invoke!");
        }
        return false;
    }
}

package com.gjson.androidtools;

import android.app.Application;
import com.gjson.androidtools.utils.Constant;
//import com.qihoo360.replugin.RePluginApplication;
//import com.taobao.hotfix.HotFixManager;
import com.taobao.hotfix.HotFixManager;
import com.taobao.hotfix.PatchLoadStatusListener;
import com.taobao.hotfix.util.PatchStatusCode;

/**
 * Created by gjson on 16/11/9.
 * Name MyApplication
 * Version 1.0
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        initHotfix();
    }

    // TODO: 11/05/16 初始化hotfix
    private void initHotfix() {

        HotFixManager.getInstance().initialize(this, BuildConfig.VERSION_NAME, Constant.ALIBAICHUAN_HOTFIX_APP_ID, true, new PatchLoadStatusListener() {
            @Override
            public void onload(int mode, int code, String info, int handlePatchVersion) {

                // 补丁加载回调通知
                if (code == PatchStatusCode.CODE_SUCCESS_LOAD) {
                    //   表明补丁加载成功

                } else if (code == PatchStatusCode.CODE_ERROR_NEEDRESTART) {
                    //  表明新补丁生效需要重启. 业务方可自行实现逻辑, 提示用户或者强制重启, 可以监听应用进入后台事件, 然后应用自杀
                    killProcess();

                } else {
                    // 其它信息

                }

            }
        });
        HotFixManager.getInstance().queryNewHotPatch();
    }

    /**
     * 杀死当前进程
     */
    private void killProcess() {
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}

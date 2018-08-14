package com.gjson.androidtools.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;

import com.gjson.androidtools.R;
import com.gjson.androidtools.commonview.VerticalAutoScrollView;
import com.gjson.androidtools.entity.AdInfo;
import com.gjson.androidtools.rxjavaoretrofit.view.activities.WeatherActivity;
import com.gjson.androidtools.utils.GjsonJni;
import com.gjson.androidtools.utils.ToastManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private VerticalAutoScrollView mAutoScrollView;
    private Button mRxRetrofitBtn;
    private Button mBezierBtn, mCheckPermisBtn,mLubanBtn;

    private Activity activity = this;

    // 退出时间
    private long exitTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ToastManager.showToast(mContext, GjsonJni.sayHello(), ToastManager.TOAST_FLAG_SUCCESS);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void setupView() {

        mAutoScrollView = getView(R.id.top_autoscrllo);
        mRxRetrofitBtn = getView(R.id.rxretro_btn);
        mBezierBtn = getView(R.id.bezier_btn);
        mCheckPermisBtn = getView(R.id.check_permission_btn);
        mLubanBtn=getView(R.id.luban_btn);
    }

    @Override
    protected void initializedData() {
        mAutoScrollView.setData(getData());
        mAutoScrollView.setClickListener(new VerticalAutoScrollView.OnItemClickListener<AdInfo>() {
            @Override
            public void onItemClick(View v, AdInfo adInfo) {

                startActivity(new Intent(mContext, CouponActivity.class));
            }
        });
        mRxRetrofitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, WeatherActivity.class));
            }
        });

        mBezierBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, BezierActivity.class));

            }
        });

        mCheckPermisBtn.setOnClickListener(new View.OnClickListener() {
                                               @Override
                                               public void onClick(View v) {
                                                   if (!isMiuiFloatWindowOpAllowed(mContext)) {
//                                                       Settings.ACTION_MANAGE_OVERLAY_PERMISSION
                                                       Intent intent1 = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                       Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                                                       intent1.setData(uri);
                                                       startActivity(intent1);
                                                   }
//                                                   startActivity(new Intent(mContext, ImageMakeActivity.class));
                                               }
                                           }
        );

        mLubanBtn.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                startActivity(new Intent(mContext, ImageMakeActivity.class));
            }
        });

    }

    /*获取数据*/
    private List<AdInfo> getData() {
        List<AdInfo> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            AdInfo item;
            if (i % 2 == 0) {
                item = new AdInfo("this is WELCOME I= ", "s");
            } else {
                item = new AdInfo("电视专场 客户一张优惠券点击查看详情 ", "s");
            }

            datas.add(item);
        }
        return datas;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            if ((System.currentTimeMillis() - exitTime) > 2000) {
                ToastManager.showToast(mContext, "再按一次退出程序", 1);
                exitTime = System.currentTimeMillis();
            } else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }



}

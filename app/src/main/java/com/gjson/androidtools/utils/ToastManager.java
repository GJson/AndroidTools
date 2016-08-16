package com.gjson.androidtools.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.gjson.androidtools.R;


/**
 * Created by gjson on 16/7/26.
 * Name ToastManager
 * Version 1.0
 */
public class ToastManager {

    private static Toast mToast;

    public static final int TOAST_FLAG_SUCCESS = 0;// 操作成功
    public static final int TOAST_FLAG_FAILED = 1;// 操作失败
    public static final int TOAST_FLAG_EXCEPTION = 2;// 操作异常

    public static void showToast(Context context, String message, int flag) {
        if (context == null) {
//            context = MyApplication.getInstance().mContext;
        }

        if (mToast == null) {
            initToast(context);
        }

        View view = mToast.getView();
        ImageView iconImg = (ImageView) view.findViewById(R.id.toast_icon_img);

        if (flag == TOAST_FLAG_EXCEPTION) {
            iconImg.setImageResource(R.mipmap.toast_icon_prompt);
        } else if (flag == TOAST_FLAG_FAILED) {
            iconImg.setImageResource(R.mipmap.toast_icon_fail);
        } else {
            iconImg.setImageResource(R.mipmap.toast_icon_normal);
        }
        mToast.setText(message);
        mToast.show();
    }

    /**
     * 初始化toast
     *
     * @param context
     */
    public static void initToast(Context context) {
        LayoutInflater inflate = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflate.inflate(R.layout.common_view_toast, null);
        mToast = new Toast(context);
        mToast.setView(view);

    }
}

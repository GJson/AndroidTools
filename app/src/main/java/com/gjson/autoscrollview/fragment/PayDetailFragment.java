package com.gjson.autoscrollview.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjson.autoscrollview.R;

/**
 * Created by gjson on 16/8/3.
 * Name PayDetailFragment
 * 底部弹窗Fragment
 * Version 1.0
 */
public class PayDetailFragment extends DialogFragment {


    private RelativeLayout rePayWay, rePayDetail, reBalance;
    private LinearLayout LinPayWay, linPass;
    private ListView lv;
    private Button btnPay;
    private EditText gridPasswordView;
    private ImageView mPardetailCloseImg, mPayTypeCloseImg, mInputPdCloseImg;


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // 使用不带Theme的构造器, 获得的dialog边框距离屏幕仍有几毫米的缝隙。
        Dialog dialog = new Dialog(getActivity(), R.style.BottomDialog);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // 设置Content前设定
        dialog.setContentView(R.layout.fragment_pay_detail);
        dialog.setCanceledOnTouchOutside(true); // 外部点击取消
        // 设置宽度为屏宽, 靠近屏幕底部。
        final Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.AnimBottom);
        final WindowManager.LayoutParams lp = window.getAttributes();
        lp.gravity = Gravity.BOTTOM; // 紧贴底部
        lp.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度持平
        lp.height = getActivity().getWindowManager().getDefaultDisplay().getHeight() * 3 / 5;
        window.setAttributes(lp);
        rePayWay = (RelativeLayout) dialog.findViewById(R.id.re_pay_way);
        rePayDetail = (RelativeLayout) dialog.findViewById(R.id.re_pay_detail);//付款详情
        LinPayWay = (LinearLayout) dialog.findViewById(R.id.lin_pay_way);//付款方式
        lv = (ListView) dialog.findViewById(R.id.lv_bank);//付款方式（银行卡列表）
        reBalance = (RelativeLayout) dialog.findViewById(R.id.re_balance);//付款方式（余额）
        btnPay = (Button) dialog.findViewById(R.id.btn_confirm_pay);
        gridPasswordView = (EditText) dialog.findViewById(R.id.pass_view);
        linPass = (LinearLayout) dialog.findViewById(R.id.lin_pass);
        mPardetailCloseImg = (ImageView) dialog.findViewById(R.id.pardetail_close_img);
        mPayTypeCloseImg = (ImageView) dialog.findViewById(R.id.paytype_close_img);
        mInputPdCloseImg = (ImageView) dialog.findViewById(R.id.inputpd_close_img);

        mPardetailCloseImg.setOnClickListener(listener);
        mPayTypeCloseImg.setOnClickListener(listener);
        mInputPdCloseImg.setOnClickListener(listener);
        rePayWay.setOnClickListener(listener);
        reBalance.setOnClickListener(listener);
        btnPay.setOnClickListener(listener);
        return dialog;
    }

    View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Animation slide_left_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_left);
            Animation slide_right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_right_to_left);
            Animation slide_left_to_right = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_right);
            Animation slide_left_to_left_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_left_to_left_in);
            switch (view.getId()) {
                case R.id.re_pay_way:
                    rePayDetail.startAnimation(slide_left_to_left);
                    rePayDetail.setVisibility(View.GONE);
                    LinPayWay.startAnimation(slide_right_to_left);
                    LinPayWay.setVisibility(View.VISIBLE);
                    break;
                case R.id.re_balance:
                    rePayDetail.startAnimation(slide_left_to_left_in);
                    rePayDetail.setVisibility(View.VISIBLE);
                    LinPayWay.startAnimation(slide_left_to_right);
                    LinPayWay.setVisibility(View.GONE);
                    break;
                case R.id.btn_confirm_pay:
                    rePayDetail.startAnimation(slide_left_to_left);
                    rePayDetail.setVisibility(View.GONE);
                    linPass.startAnimation(slide_right_to_left);
                    linPass.setVisibility(View.VISIBLE);
                    break;
                case R.id.pardetail_close_img:
                case R.id.paytype_close_img:
                case R.id.inputpd_close_img:
                    dismissAllowingStateLoss();
                    break;
                default:
                    break;
            }
        }
    };

}

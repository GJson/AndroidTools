package com.gjson.autoscrollview.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.gjson.autoscrollview.R;
import com.gjson.autoscrollview.adapter.CouponListAdapter;

/**
 * Created by gjson on 16/7/26.
 * Name CouponActivity
 * Version 1.0
 */
public class CouponActivity extends BaseActivity {

    private ListView mCouponLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        mCouponLv = getView(R.id.coupon_lv);
        mCouponLv.setAdapter(new CouponListAdapter(mContext));

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }
}

package com.gjson.autoscrollview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.gjson.autoscrollview.R;
import com.gjson.autoscrollview.adapter.CouponListAdapter;
import com.gjson.autoscrollview.fragment.PayDetailFragment;

/**
 * Created by gjson on 16/7/26.
 * Name CouponActivity
 * Version 1.0
 */
public class CouponActivity extends BaseActivity {

    private ListView mCouponLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(getLayoutId());
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_coupon;
    }

    @Override
    protected void setupView() {
        mCouponLv = getView(R.id.coupon_lv);
    }

    @Override
    protected void initializedData() {
        mCouponLv.setAdapter(new CouponListAdapter(mContext));
        mCouponLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PayDetailFragment payDetailFragment = new PayDetailFragment();
//                getSupportFragmentManager().beginTransaction().replace();
                payDetailFragment.show(getFragmentManager(), "payDetailFragment");
            }
        });
    }
}

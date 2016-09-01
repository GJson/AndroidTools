package com.gjson.androidtools.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.gjson.androidtools.R;
import com.gjson.androidtools.adapter.CouponListAdapter;
import com.gjson.androidtools.fragment.PayDetailFragment;
import com.gjson.androidtools.utils.AnimationUtils;

/**
 * Created by gjson on 16/7/26.
 * Name CouponActivity
 * Version 1.0
 */
public class CouponActivity extends BaseActivity implements AdapterView.OnItemClickListener {

    private RelativeLayout mRootRel;
    private ImageView mCartImg;
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
        mRootRel = getView(R.id.root_rl);
        mCouponLv = getView(R.id.coupon_lv);
        mCartImg = getView(R.id.cart_img);

    }

    @Override
    protected void initializedData() {
        mCouponLv.setAdapter(new CouponListAdapter(mContext, this));
        mCouponLv.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (view.getId()) {
            case R.id.horn_img:
//                CouponListAdapter.ViewHolder viewHolder = new CouponListAdapter.ViewHolder();
//                viewHolder = (CouponListAdapter.ViewHolder) view.getTag();
                AnimationUtils.AddToShopingCart((ImageView) view, mCartImg, mContext, mRootRel, 1);
                break;
            default:
                PayDetailFragment payDetailFragment = new PayDetailFragment();
//                getSupportFragmentManager().beginTransaction().replace();
                payDetailFragment.show(getFragmentManager(), "payDetailFragment");
                break;
        }


    }
}

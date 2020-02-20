package com.gjson.androidtools.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.gjson.androidtools.R;
import com.gjson.androidtools.adapter.CouponListAdapter;

/**
 * Created by gjson on 2020-02-19.
 * Name LottieActivity
 * Version 1.0
 */
public class LottieActivity extends BaseActivity implements View.OnClickListener {

  Button lottieBtn;
  LottieAnimationView likeAnimationView;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  protected int getLayoutId() {
    return R.layout.activity_lottie;
  }

  @Override
  protected void setupView() {

    lottieBtn = (Button) findViewById(R.id.btn_lottie);
    likeAnimationView = (LottieAnimationView) findViewById(R.id.animation_view);

    likeAnimationView.setImageAssetsFolder("zd/");
    likeAnimationView.setAnimation("lottie_zan.json");
    //likeAnimationView.loop(true);

  }

  @Override
  protected void initializedData() {

    lottieBtn.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {

      case R.id.btn_lottie:
        likeAnimationView.playAnimation();
        break;
    }
  }
}

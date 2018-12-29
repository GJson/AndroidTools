package com.gjson.androidtools.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import com.gjson.androidtools.R;
import com.gjson.androidtools.commonview.loadingview.LoadingIndicatorView;

/**
 * @Author Created by gjson on 2018/12/29.
 */
public class LoadingSampleActivity extends BaseActivity {
  private RecyclerView mRecycler;

  @Override protected int getLayoutId() {
    return R.layout.activity_loadingview_sample;
  }

  @Override protected void setupView() {

  }

  @Override protected void initializedData() {

    mRecycler = (RecyclerView) findViewById(R.id.recycler);

    GridLayoutManager layoutManager = new GridLayoutManager(this, 4);
    mRecycler.setLayoutManager(layoutManager);
    mRecycler.setAdapter(new RecyclerView.Adapter<IndicatorHolder>() {
      @Override public IndicatorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = getLayoutInflater().inflate(R.layout.item_indicator, parent, false);
        return new IndicatorHolder(itemView);
      }

      @Override public void onBindViewHolder(IndicatorHolder holder, final int position) {
        holder.indicatorView.setIndicator(INDICATORS[position]);
        holder.itemLayout.setOnClickListener(new View.OnClickListener() {
          @Override public void onClick(View v) {
            //Intent intent = new Intent(LoadingSampleActivity.this, IndicatorActivity.class);
            //intent.putExtra("indicator", INDICATORS[position]);
            //startActivity(intent);
          }
        });
      }

      @Override public int getItemCount() {
        return INDICATORS.length;
      }
    });
  }

  final static class IndicatorHolder extends RecyclerView.ViewHolder {

    public LoadingIndicatorView indicatorView;
    public View itemLayout;

    public IndicatorHolder(View itemView) {
      super(itemView);
      itemLayout = itemView.findViewById(R.id.itemLayout);
      indicatorView = (LoadingIndicatorView) itemView.findViewById(R.id.indicator);
    }
  }

  private static final String[] INDICATORS = new String[] {
      "BallPulseIndicator", "BallGridPulseIndicator", "BallClipRotateIndicator",
      "BallClipRotatePulseIndicator", "SquareSpinIndicator", "BallClipRotateMultipleIndicator",
      "BallPulseRiseIndicator", "BallRotateIndicator", "CubeTransitionIndicator",
      "BallZigZagIndicator", "BallZigZagDeflectIndicator", "BallTrianglePathIndicator",
      "BallScaleIndicator", "LineScaleIndicator", "LineScalePartyIndicator",
      "BallScaleMultipleIndicator", "BallPulseSyncIndicator", "BallBeatIndicator",
      "LineScalePulseOutIndicator", "LineScalePulseOutRapidIndicator", "BallScaleRippleIndicator",
      "BallScaleRippleMultipleIndicator", "BallSpinFadeLoaderIndicator",
      "LineSpinFadeLoaderIndicator", "TriangleSkewSpinIndicator", "PacmanIndicator",
      "BallGridBeatIndicator", "SemiCircleSpinIndicator", "com.wang.avi.sample.MyCustomIndicator"
  };
}

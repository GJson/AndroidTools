package com.gjson.androidtools.library.imagepicker.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.format.Formatter;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Toast;
import com.gjson.androidtools.library.R;
import com.gjson.androidtools.library.imagepicker.ImagePicker;
import com.gjson.androidtools.library.imagepicker.bean.ImageItem;
import com.gjson.androidtools.library.imagepicker.util.NavigationBarChangeListener;
import com.gjson.androidtools.library.imagepicker.util.Utils;
import com.gjson.androidtools.library.imagepicker.view.SuperCheckBox;

/**
 * Created by gjson on 2018/6/22.
 */
public class ImagePreviewActivity extends ImagePreviewBaseActivity
    implements ImagePicker.OnImageSelectedListener, View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {

  public static final String ISORIGIN = "isOrigin";

  private boolean isOrigin;                      //是否选中原图
  private SuperCheckBox mCbCheck;                //是否选中当前图片的CheckBox
  private SuperCheckBox mCbOrigin;               //原图
  private Button mBtnOk;                         //确认图片的选择
  private View bottomBar;
  private View marginView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    isOrigin = getIntent().getBooleanExtra(ImagePreviewActivity.ISORIGIN, false);
    imagePicker.addOnImageSelectedListener(this);
    mBtnOk = (Button) findViewById(R.id.btn_ok);
    mBtnOk.setVisibility(View.VISIBLE);
    mBtnOk.setOnClickListener(this);

    bottomBar = findViewById(R.id.bottom_bar);
    bottomBar.setVisibility(View.VISIBLE);

    mCbCheck = (SuperCheckBox) findViewById(R.id.cb_check);
    mCbOrigin = (SuperCheckBox) findViewById(R.id.cb_origin);
    marginView = findViewById(R.id.margin_bottom);
    mCbOrigin.setText(getString(R.string.ip_origin));
    mCbOrigin.setOnCheckedChangeListener(this);
    mCbOrigin.setChecked(isOrigin);

    //初始化当前页面的状态
    onImageSelected(0, null, false);
    ImageItem item = mImageItems.get(mCurrentPosition);
    boolean isSelected = imagePicker.isSelect(item);
    mTitleCount.setText(
        getString(R.string.ip_preview_image_count, mCurrentPosition + 1, mImageItems.size()));
    mCbCheck.setChecked(isSelected);
    //滑动ViewPager的时候，根据外界的数据改变当前的选中状态和当前的图片的位置描述文本
    mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
      @Override public void onPageSelected(int position) {
        mCurrentPosition = position;
        ImageItem item = mImageItems.get(mCurrentPosition);
        boolean isSelected = imagePicker.isSelect(item);
        mCbCheck.setChecked(isSelected);
        mTitleCount.setText(
            getString(R.string.ip_preview_image_count, mCurrentPosition + 1, mImageItems.size()));
      }
    });
    //当点击当前选中按钮的时候，需要根据当前的选中状态添加和移除图片
    mCbCheck.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        ImageItem imageItem = mImageItems.get(mCurrentPosition);
        int selectLimit = imagePicker.getSelectLimit();
        if (mCbCheck.isChecked() && selectedImages.size() >= selectLimit) {
          Toast.makeText(ImagePreviewActivity.this,
              getString(R.string.ip_select_limit, selectLimit), Toast.LENGTH_SHORT).show();
          mCbCheck.setChecked(false);
        } else {
          imagePicker.addSelectedImageItem(mCurrentPosition, imageItem, mCbCheck.isChecked());
        }
      }
    });
    NavigationBarChangeListener.with(this)
        .setListener(new NavigationBarChangeListener.OnSoftInputStateChangeListener() {
          @Override public void onNavigationBarShow(int orientation, int height) {
            marginView.setVisibility(View.VISIBLE);
            ViewGroup.LayoutParams layoutParams = marginView.getLayoutParams();
            if (layoutParams.height == 0) {
              layoutParams.height = Utils.getNavigationBarHeight(ImagePreviewActivity.this);
              marginView.requestLayout();
            }
          }

          @Override public void onNavigationBarHide(int orientation) {
            marginView.setVisibility(View.GONE);
          }
        });
    NavigationBarChangeListener.with(this, NavigationBarChangeListener.ORIENTATION_HORIZONTAL)
        .setListener(new NavigationBarChangeListener.OnSoftInputStateChangeListener() {
          @Override public void onNavigationBarShow(int orientation, int height) {
            topBar.setPadding(0, 0, height, 0);
            bottomBar.setPadding(0, 0, height, 0);
          }

          @Override public void onNavigationBarHide(int orientation) {
            topBar.setPadding(0, 0, 0, 0);
            bottomBar.setPadding(0, 0, 0, 0);
          }
        });
  }

  /**
   * 图片添加成功后，修改当前图片的选中数量
   * 当调用 addSelectedImageItem 或 deleteSelectedImageItem 都会触发当前回调
   */
  @Override public void onImageSelected(int position, ImageItem item, boolean isAdd) {
    if (imagePicker.getSelectImageCount() > 0) {
      mBtnOk.setText(getString(R.string.ip_select_complete, imagePicker.getSelectImageCount(),
          imagePicker.getSelectLimit()));
    } else {
      mBtnOk.setText(getString(R.string.ip_complete));
    }

    if (mCbOrigin.isChecked()) {
      long size = 0;
      for (ImageItem imageItem : selectedImages)
        size += imageItem.size;
      String fileSize = Formatter.formatFileSize(this, size);
      mCbOrigin.setText(getString(R.string.ip_origin_size, fileSize));
    }
  }

  @Override public void onClick(View v) {
    int id = v.getId();
    if (id == R.id.btn_ok) {
      if (imagePicker.getSelectedImages().size() == 0) {
        mCbCheck.setChecked(true);
        ImageItem imageItem = mImageItems.get(mCurrentPosition);
        imagePicker.addSelectedImageItem(mCurrentPosition, imageItem, mCbCheck.isChecked());
      }
      Intent intent = new Intent();
      intent.putExtra(ImagePicker.EXTRA_RESULT_ITEMS, imagePicker.getSelectedImages());
      setResult(ImagePicker.RESULT_CODE_ITEMS, intent);
      finish();
    } else if (id == R.id.btn_back) {
      Intent intent = new Intent();
      intent.putExtra(ImagePreviewActivity.ISORIGIN, isOrigin);
      setResult(ImagePicker.RESULT_CODE_BACK, intent);
      finish();
    }
  }

  @Override public void onBackPressed() {
    Intent intent = new Intent();
    intent.putExtra(ImagePreviewActivity.ISORIGIN, isOrigin);
    setResult(ImagePicker.RESULT_CODE_BACK, intent);
    finish();
    super.onBackPressed();
  }

  @Override public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
    int id = buttonView.getId();
    if (id == R.id.cb_origin) {
      if (isChecked) {
        long size = 0;
        for (ImageItem item : selectedImages)
          size += item.size;
        String fileSize = Formatter.formatFileSize(this, size);
        isOrigin = true;
        mCbOrigin.setText(getString(R.string.ip_origin_size, fileSize));
      } else {
        isOrigin = false;
        mCbOrigin.setText(getString(R.string.ip_origin));
      }
    }
  }

  @Override protected void onDestroy() {
    imagePicker.removeOnImageSelectedListener(this);
    super.onDestroy();
  }

  /**
   * 单击时，隐藏头和尾
   */
  @Override public void onImageSingleTap() {
    if (topBar.getVisibility() == View.VISIBLE) {
      topBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_out));
      bottomBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_out));
      topBar.setVisibility(View.GONE);
      bottomBar.setVisibility(View.GONE);
      tintManager.setStatusBarTintResource(Color.TRANSPARENT);//通知栏所需颜色
      //给最外层布局加上这个属性表示，Activity全屏显示，且状态栏被隐藏覆盖掉。
      //            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN);
    } else {
      topBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.top_in));
      bottomBar.setAnimation(AnimationUtils.loadAnimation(this, R.anim.fade_in));
      topBar.setVisibility(View.VISIBLE);
      bottomBar.setVisibility(View.VISIBLE);
      tintManager.setStatusBarTintResource(R.color.ip_color_primary_dark);//通知栏所需颜色
      //Activity全屏显示，但状态栏不会被隐藏覆盖，状态栏依然可见，Activity顶端布局部分会被状态遮住
      //            if (Build.VERSION.SDK_INT >= 16) content.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
    }
  }
}

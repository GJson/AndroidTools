package com.gjson.androidtools.library.imagepicker.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import com.gjson.androidtools.library.imagepicker.ImagePicker;
import com.gjson.androidtools.library.imagepicker.bean.ImageItem;
import com.gjson.androidtools.library.imagepicker.util.Utils;
import java.util.ArrayList;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by gjson on 2018/6/22.
 * Name ImagePageAdapter
 * Version 1.0
 */
public class ImagePageAdapter extends PagerAdapter {

  private int screenWidth;
  private int screenHeight;
  private ImagePicker imagePicker;
  private ArrayList<ImageItem> images = new ArrayList<>();
  private Activity mActivity;
  public PhotoViewClickListener listener;

  public ImagePageAdapter(Activity activity, ArrayList<ImageItem> images) {
    this.mActivity = activity;
    this.images = images;

    DisplayMetrics dm = Utils.getScreenPix(activity);
    screenWidth = dm.widthPixels;
    screenHeight = dm.heightPixels;
    imagePicker = ImagePicker.getInstance();
  }

  public void setData(ArrayList<ImageItem> images) {
    this.images = images;
  }

  public void setPhotoViewClickListener(PhotoViewClickListener listener) {
    this.listener = listener;
  }

  @Override
  public Object instantiateItem(ViewGroup container, int position) {
    PhotoView photoView = new PhotoView(mActivity);
    ImageItem imageItem = images.get(position);
    imagePicker.getImageLoader().displayImagePreview(mActivity, imageItem.path, photoView, screenWidth, screenHeight);
    photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
      @Override
      public void onPhotoTap(View view, float x, float y) {
        if (listener != null) listener.OnPhotoTapListener(view, x, y);
      }
    });
    container.addView(photoView);
    return photoView;
  }

  @Override
  public int getCount() {
    return images.size();
  }

  @Override
  public boolean isViewFromObject(View view, Object object) {
    return view == object;
  }

  @Override
  public void destroyItem(ViewGroup container, int position, Object object) {
    container.removeView((View) object);
  }

  @Override
  public int getItemPosition(Object object) {
    return POSITION_NONE;
  }

  public interface PhotoViewClickListener {
    void OnPhotoTapListener(View view, float v, float v1);
  }
}

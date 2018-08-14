package com.gjson.androidtools.library.imagepicker.ui;

import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import com.gjson.androidtools.library.R;
import com.gjson.androidtools.library.imagepicker.ImagePicker;
import com.gjson.androidtools.library.imagepicker.view.SystemBarTintManager;

/**
 * Created by gjson on 2018/6/22.
 */
public class ImageBaseActivity extends AppCompatActivity {

  protected SystemBarTintManager tintManager;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      setTranslucentStatus(true);
    }
    tintManager = new SystemBarTintManager(this);
    tintManager.setStatusBarTintEnabled(true);
    tintManager.setStatusBarTintResource(R.color.ip_color_primary_dark);  //设置上方状态栏的颜色
  }

  @TargetApi(19) private void setTranslucentStatus(boolean on) {
    Window win = getWindow();
    WindowManager.LayoutParams winParams = win.getAttributes();
    final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
    if (on) {
      winParams.flags |= bits;
    } else {
      winParams.flags &= ~bits;
    }
    win.setAttributes(winParams);
  }

  public boolean checkPermission(@NonNull String permission) {
    return ActivityCompat.checkSelfPermission(this, permission)
        == PackageManager.PERMISSION_GRANTED;
  }

  public void showToast(String toastText) {
    Toast.makeText(getApplicationContext(), toastText, Toast.LENGTH_SHORT).show();
  }

  @Override protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    ImagePicker.getInstance().restoreInstanceState(savedInstanceState);
  }

  @Override protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    ImagePicker.getInstance().saveInstanceState(outState);
  }
}

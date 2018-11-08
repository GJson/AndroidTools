package com.gjson.androidtools.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.gjson.androidtools.R;
import com.gjson.androidtools.utils.LogUtil;
import com.gjson.androidtools.utils.ToastManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * Created by gjson on 2017/5/2.
 * Name ImageMakeActivity
 * Version 1.0
 */

public class ImageMakeActivity extends BaseActivity {

    /**
     * 打开相机
     */
    public final static int GO_CAMERA = 0;

    /**
     * 存储路径
     */
    private static final String PICTURE_HEAD = Environment.getExternalStorageDirectory() + "/DCIM";

    private String mPictureName;

    /**
     * 图片路径
     */
    private String mPicturePath;

    private ImageView mPicImg;
    private Button mTakePicBtn;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_imagemake;
    }

    @Override
    protected void setupView() {
        mPicImg = getView(R.id.img_pic);
        mTakePicBtn = getView(R.id.takephoto_btn);

    }


    @Override
    protected void initializedData() {
        mTakePicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoCamera();
            }
        });

    }

    public void gotoCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        mPictureName = DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA)) + ".jpg";
        Uri imageUri = Uri.fromFile(new File(PICTURE_HEAD, mPictureName));
        intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);

        startActivityForResult(intent, GO_CAMERA);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        switch (requestCode) {

            case GO_CAMERA:
                //获取图片
                mPicturePath = PICTURE_HEAD + "/" + mPictureName;
                compressImage(mPicturePath);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void compressImage(String imagepath) {
        Luban.get(this)
                .load(new File(imagepath))      //传人要压缩的图片
                .putGear(Luban.THIRD_GEAR)      //设定压缩档次，默认三挡
                .setCompressListener(new OnCompressListener() { //设置回调

                    @Override
                    public void onStart() {

                        LogUtil.e("start: ", System.currentTimeMillis() + "");
                        ToastManager.showToast(mContext, "开始压缩图片……", ToastManager.TOAST_FLAG_SUCCESS);
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                    }

                    @Override
                    public void onSuccess(File file) {
                        LogUtil.e("stop: ", file.getPath() + "");
                        LogUtil.e("stop: ", System.currentTimeMillis() + "");

                        try {

                            copyFile(file.getPath(), PICTURE_HEAD + "/new" + mPictureName);

//                            getFileSize(file);
                        } catch (Exception e) {

                        }

                        ToastManager.showToast(mContext, "压缩图片完成……", ToastManager.TOAST_FLAG_SUCCESS);
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                    }

                    @Override
                    public void onError(Throwable e) {

                        ToastManager.showToast(mContext, "开始压缩图片……", ToastManager.TOAST_FLAG_FAILED);
                    }
                }).launch();    //启动压缩

    }

    /**
     * Des:获取指定文件大小
     *
     * @param file
     * @return size
     * @throws Exception
     */
    private static long getFileSize(File file) throws Exception {
        long size = 0;
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
        } else {
            file.createNewFile();
        }
        return size;
    }

    /**
     * Des:复制单个文件
     *
     * @param oldFilePath String 原文件路径
     * @param newFilePath String 复制后路径
     */
    public void copyFile(String oldFilePath, String newFilePath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldFilePath);
            if (oldfile.exists()) {
                InputStream inStream = new FileInputStream(oldFilePath);
                FileOutputStream fs = new FileOutputStream(newFilePath);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }

    }
}

package com.gjson.androidtools.activity;

import android.os.Bundle;

import com.gjson.androidtools.R;
import com.gjson.androidtools.commonview.BezierView;
import com.gjson.androidtools.utils.LogUtil;
import com.gjson.androidtools.utils.ScreenUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by gjson on 16/9/6.
 * Name BezierActivity
 * Version 1.0
 */
public class BezierActivity extends BaseActivity {

    private BezierView mBezierView;

    private Document document;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bezier;
    }

    @Override
    protected void setupView() {

        mBezierView = getView(R.id.bezier_view);
    }

    @Override
    protected void initializedData() {

        mBezierView.setDefaultResourceImgList();

        mBezierView.startAnimation(ScreenUtil.getScreenWidth(mContext), ScreenUtil.getScreenHeight(mContext), 1000);

        new Thread(new Runnable() {
            @Override
            public void run() {
                JsoUpFrom36Kr();
            }
        }).start();
    }


    private void JsoUpFrom36Kr() {
        try {
            document = Jsoup.connect("http://www.36kr.com/").timeout(8000).get();
            Elements elements = document.select("ul.J_navList");
            Elements lis = elements.select("li");
            for (int i = 0; i < lis.size(); i++) {

//                if (i < 5) continue;

                String intro = lis.get(i).select("s.undefined mark h5_mark").text();

                LogUtil.e("title", intro + "\\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

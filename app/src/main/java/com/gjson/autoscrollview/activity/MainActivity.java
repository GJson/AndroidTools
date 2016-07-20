package com.gjson.autoscrollview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gjson.autoscrollview.R;
import com.gjson.autoscrollview.commonview.VerticalAutoScrollView;
import com.gjson.autoscrollview.entity.AdInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private VerticalAutoScrollView mAutoScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAutoScrollView = getView(R.id.top_autoscrllo);
        mAutoScrollView.setData(getData());

    }

    private List<AdInfo> getData() {
        List<AdInfo> datas = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            AdInfo item;
            if (i % 2 == 0) {
                item = new AdInfo("this is WELCOME I= " , "s");
            } else {
                item = new AdInfo("电视专场 客户一张优惠券点击查看详情 " , "s");
            }

            datas.add(item);
        }
        return datas;
    }

    protected <T extends View> T getView(int id) {
        return (T) findViewById(id);
    }
}

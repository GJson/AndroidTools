package com.gjson.androidtools.activity;

import com.gjson.androidtools.utils.LogUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * Created by gjson on 2017/7/5.
 * Name KrJsoUpActivity
 * Version 1.0
 */

public class KrJsoUpActivity extends BaseActivity {

    private Document document;


    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void setupView() {

    }

    @Override
    protected void initializedData() {

    }


    private void JsoUpFrom36Kr() {
        try {
            document = Jsoup.connect("http://36kr.com/").timeout(8000).get();
            Elements elements = document.select("ul.feed_ul");
            Elements lis = elements.select("li");
            for (int i = 0; i < lis.size(); i++) {

                if (i<5)continue;

                String intro=lis.get(i).select("div.intro").text();

                LogUtil.e("intro",intro);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

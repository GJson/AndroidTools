package com.gjson.androidtools.commonview;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.gjson.androidtools.R;
import com.gjson.androidtools.entity.AdInfo;
import com.gjson.androidtools.utils.DensityUtil;

import java.util.List;

/**
 * Created by gjson on 16/7/20.
 * Name VerticalAutoScrollView
 * Version 1.0
 */
public class VerticalAutoScrollView extends LinearLayout {


    private Scroller mScroller;  //滚动实例

    private List<AdInfo> meaageList;  //存放数据集合
    private final int DURING_TIME = 3000;  //滚动延迟
    private OnItemClickListener<AdInfo> click;
    private Context mContext;
    private float HEIGHT = 56;//SCROLL ITEM  HEIGHT


    public VerticalAutoScrollView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public VerticalAutoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
    }

    /**
     * 设置数据
     *
     * @param meaageList
     */
    public void setData(List<AdInfo> meaageList) {
        this.meaageList = meaageList;
        if (meaageList != null) {
            removeAllViews();
            Log.i("tag", meaageList.size() + "");
            int size = meaageList.size();
            for (int i = 0; i < size; i++) {
                addContentView(i);
            }
            if (meaageList.size() > 1) {
                getLayoutParams().height = DensityUtil.dip2px(mContext, HEIGHT);  //调节滚动数据的高度
                // 滚动
                cancelAuto();
                mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
                smoothScrollBy(0, DensityUtil.dip2px(mContext, HEIGHT));
            }
        }
    }

    /**
     * 设置列表点击事件
     *
     * @param click
     */
    public void setClickListener(OnItemClickListener<AdInfo> click) {
        this.click = click;
    }

    /**
     * 重置数据
     */
    private void resetView() {
        AdInfo article = meaageList.get(0);
        meaageList.remove(0);
        meaageList.add(article);

        for (int i = 0; i < 2; i++) {
            addContentView(i);
        }
    }

    /**
     * 取消滚动
     */
    public void cancelAuto() {
        mHandler.removeMessages(0);
    }

    private void addContentView(int position) {
        ViewHolder mHolder;
        if (position >= getChildCount()) {
            mHolder = new ViewHolder();
            View v = View.inflate(getContext(), R.layout.item_view, null);
            mHolder.lin = (LinearLayout) v.findViewById(R.id.parent_view);
            mHolder.message_txt = (TextView) v.findViewById(R.id.txt_user_message);
            mHolder.usericon_img = (ImageView) v.findViewById(R.id.img_user_icon);
            v.setTag(mHolder);
            addView(v, LinearLayout.LayoutParams.MATCH_PARENT, DensityUtil.dip2px(mContext, HEIGHT));
        } else {
            mHolder = (ViewHolder) getChildAt(position).getTag();
        }
        final AdInfo message = meaageList.get(position);
        mHolder.message_txt.setText(message.name);
        mHolder.lin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (click != null) {
                    click.onItemClick(v, message);
                }
            }
        });
//        String picurl = String.format(employeePhoto, message.url);
//        ImageLoaderUtils.loadImage(picurl, mHolder.usericon_img, true);
//        mHolder.message_txt.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View arg0) {
//                if (click != null) {
//                    click.onItemClick(null, article);
//                }
//
//            }
//        });
    }

    private class ViewHolder {
        TextView message_txt;
        ImageView usericon_img;
        LinearLayout lin;
    }

    Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            mHandler.removeMessages(0);
            mHandler.sendEmptyMessageDelayed(0, DURING_TIME);
            smoothScrollBy(0, DensityUtil.dip2px(mContext, HEIGHT));
            resetView();
        }

    };

    // 调用此方法设置滚动的相对偏移
    public void smoothScrollBy(int dx, int dy) {
        // 设置mScroller的滚动偏移量
        mScroller.startScroll(mScroller.getFinalX(), 0, dx, dy, 1500);
        invalidate();// 这里必须调用invalidate()才能保证computeScroll()会被调用，否则不一定会刷新界面，看不到滚动效果
    }

    @Override
    public void computeScroll() {

        // 先判断mScroller滚动是否完成
        if (mScroller.computeScrollOffset()) {

            // 这里调用View的scrollTo()完成实际的滚动
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            // 必须调用该方法，否则不一定能看到滚动效果
            postInvalidate();

        }
        super.computeScroll();
    }

    public interface OnItemClickListener<T> {
        public void onItemClick(View v, T t);
    }
}

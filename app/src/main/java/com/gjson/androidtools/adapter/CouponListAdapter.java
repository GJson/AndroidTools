package com.gjson.androidtools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjson.androidtools.R;


/**
 * Created by gjson on 16/7/26.
 * Name CouponListAdapter
 * Version 1.0
 */
public class CouponListAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;

    public CouponListAdapter(Context context) {

        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_couponview, null);
            viewHolder = new ViewHolder();
            viewHolder.img=(ImageView)convertView.findViewById(R.id.horn_img) ;
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }
        return convertView;
    }

    static class ViewHolder {
        TextView tv;
        ImageView img;
    }
}

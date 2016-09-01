package com.gjson.androidtools.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
    private AdapterView.OnItemClickListener mOnitemClicklis;

    public CouponListAdapter(Context context, AdapterView.OnItemClickListener itemClickListener) {

        mContext = context;
        mInflater = LayoutInflater.from(mContext);
        this.mOnitemClicklis = itemClickListener;
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
    public View getView(final int position, View convertView, final ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_couponview, null);
            viewHolder = new ViewHolder();
            viewHolder.img = (ImageView) convertView.findViewById(R.id.horn_img);
            convertView.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) convertView.getTag();

        }
//        viewHolder.img.setTag(viewHolder);
        viewHolder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnitemClicklis.onItemClick(null, v, position, 0);
            }
        });
        return convertView;
    }

    public static class ViewHolder {
        public TextView tv;
        public ImageView img;
    }
}

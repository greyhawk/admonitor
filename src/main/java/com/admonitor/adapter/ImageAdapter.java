package com.admonitor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.admonitor.R;

import java.util.List;

/**
 * Created by admin on 2017/7/3.
 */
public class ImageAdapter extends ArrayAdapter {
    private List list;
    private View view;
    private int i;
    public ImageAdapter(Context context, List list) {
        super(context, 0, list);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        Bitmap bitmap = (Bitmap) list.get(position);
        String picPath = (String) list.get(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.testone,null);
        ImageView image1 = (ImageView) view.findViewById(R.id.test1);
        Bitmap bitmap = BitmapFactory.decodeFile(picPath);
        image1.setImageBitmap(bitmap);
        return view;
    }
}

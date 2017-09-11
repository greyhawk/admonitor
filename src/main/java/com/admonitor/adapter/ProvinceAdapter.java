package com.admonitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.admonitor.R;
import com.admonitor.tools.Provinces;

import java.util.List;

/**
 * Created by admin on 2017/6/2.
 */
public class ProvinceAdapter extends ArrayAdapter {
    private View view;
    private List list;
    public ProvinceAdapter(Context context, List list) {
        super(context, 0, list);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Provinces p = (Provinces) list.get(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.activity_city_item,null);
        TextView t = (TextView) view.findViewById(R.id.title);
        t.setText(p.getProvince());
        return view;
    }
}

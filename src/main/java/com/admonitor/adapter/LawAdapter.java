package com.admonitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.admonitor.R;
import com.admonitor.tools.Law1;

import java.util.List;

/**
 * Created by admin on 2017/7/17.
 */
public class LawAdapter extends ArrayAdapter {
    private List list;
    private View view;
    public LawAdapter(Context context,List list) {
        super(context, 0, list);
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Law1 law1 = (Law1) list.get(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.law_item_one,null);
        TextView title = (TextView) view.findViewById(R.id.title);
        title.setText(law1.getW1_title());
        return view;
    }
}

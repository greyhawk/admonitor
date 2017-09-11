package com.admonitor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.admonitor.R;
import com.admonitor.tools.News;

import java.util.List;

/**
 * Created by admin on 2017/5/24.
 */
public class NewsAdapter extends ArrayAdapter {
    private List list;
    private View view;
    public NewsAdapter(Context context,List list) {
        super(context, 0, list);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        News n = (News) list.get(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.activity_news_item,null);
        TextView title = (TextView) view.findViewById(R.id.title);
        TextView date = (TextView) view.findViewById(R.id.date);
        title.setText(n.getN_title());
        date.setText(n.getN_release_date());
        return view;
    }
}

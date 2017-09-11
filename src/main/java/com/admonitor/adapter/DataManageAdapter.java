package com.admonitor.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.admonitor.R;
import com.admonitor.tools.Ad;

import java.util.List;

/**
 * Created by admin on 2017/6/1.
 */
public class DataManageAdapter extends ArrayAdapter {
    private List list;
    private View view;
    public DataManageAdapter(Context context, List list) {
        super(context, 0, list);
        this.list=list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Ad ad = (Ad) list.get(position);
        view = LayoutInflater.from(getContext()).inflate(R.layout.datamanage_item,null);
        TextView name = (TextView) view.findViewById(R.id.name);
        name.setText(ad.getA_name());
        TextView weifa = (TextView) view.findViewById(R.id.weifa);
        weifa.setText(ad.getA_Illegal());
        TextView type = (TextView) view.findViewById(R.id.type);
        type.setText(ad.getA_type());
        TextView name1 = (TextView) view.findViewById(R.id.name1);
        name1.setText(ad.getA_address());
        TextView name111 = (TextView) view.findViewById(R.id.name111);
        name111.setText(ad.getA_city());
        ImageView tu = (ImageView) view.findViewById(R.id.tu);
        String url = ad.getA_image();
        if(!url.equals("")){
            String[] _url = url.split(",");
            Bitmap bitmap = BitmapFactory.decodeFile(_url[0]);
            tu.setImageBitmap(bitmap);
        }else{
            tu.setImageResource(R.drawable.citm);
        }

        return view;
    }
}

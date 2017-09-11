package com.admonitor.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ExpandableListView;
import android.widget.TextView;
import android.widget.Toast;

import com.admonitor.R;
import com.admonitor.activity.LawActivity;
import com.admonitor.tools.Law1;
import com.admonitor.tools.Law2;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */
public class ExpandableListAdapter extends BaseExpandableListAdapter {
    Law1[] law1;
    Law2[] law2;
    public List list1 = new ArrayList();
    private ArrayList<Group> groups = new ArrayList<Group>();
    List list = new ArrayList();
    private Law2[][] groupItems;
    private Context context;

    public ExpandableListAdapter(Context context, Law1[] a, Law2[] b) {
        this.context = context;
        this.law1 = a;
        this.law2 = b;
        groupItems = new Law2[law1.length][];
        for (int i = 0; i < law1.length; i++) {
            list.clear();
            Law1 law = law1[i];
            for (int j = 0; j < law2.length; j++) {
                Law2 law22 = law2[j];
                if (law.getW1_id() == law22.getW1_id()) {
                    list.add(law22);
                }
            }
            groupItems[i] = new Law2[list.size()];
            for (int j = 0; j < list.size(); j++) {
                Law2 aa = (Law2) list.get(j);
                groupItems[i][j] = aa;
            }

        }

    }

    public List getList1() {
        list1.clear();
        for (int i = 0; i < groupItems.length; i++) {
            for (int j = 0; j < groupItems[i].length; j++) {
                Law2 law22 = groupItems[i][j];
                if (law22.getFlag()) {
                    list1.add(law22.getW2_id());
                }
            }
        }
        return list1;
    }


    @Override
    public int getGroupCount()
    {
        return  law1.length;
    }

    @Override
    public int getChildrenCount(int groupPosition)
    {
        return groupItems[groupPosition].length;
    }

    @Override
    public Object getGroup(int groupPosition)
    {
        return  law1[groupPosition];
    }

    @Override
    public Object getChild(int groupPosition, int childPosition)
    {
        return groupItems[groupPosition][childPosition];
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition)
    {
        return childPosition;
    }

    @Override
    public boolean hasStableIds()
    {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent)
    {
        Law1 law = law1[groupPosition];
        View view = LayoutInflater.from(context).inflate(
                R.layout.law_item_one, null);
			TextView tv = (TextView) view.findViewById(R.id.title);
			tv.setText(law.getW1_title());
        return view;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent)
    {
        Law2 law = groupItems[groupPosition][childPosition];
        View view = LayoutInflater.from(context).inflate(
                R.layout.law_item_two, null);
			TextView tv1 = (TextView) view.findViewById(R.id.t1);
			tv1.setText(law.getW2_title()+"");
            TextView tv2 = (TextView) view.findViewById(R.id.t2);
            tv2.setText(law.getW2_content()+"");
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.checkBox);
        checkBox.setChecked(law.getFlag());
        checkBox.setTag(law.getW2_id());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    Law2 law = groupItems[groupPosition][childPosition];
                            law.setFlag(true);
                }else{
                    Law2 law = groupItems[groupPosition][childPosition];
                            law.setFlag(false);
                }
            }

        });
        if(law.getFlag()){
            list1.add(law.getW2_id());
        }
        return view;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition)
    {
        return true;
    }

}

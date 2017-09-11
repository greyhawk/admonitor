package com.admonitor.activity;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import com.admonitor.R;
import com.admonitor.adapter.MyViewPagerAdapter;
import com.admonitor.fragment.FindFragment;
import com.admonitor.fragment.IndexFragment;
import com.admonitor.fragment.NewsFragment;
import com.admonitor.fragment.SetFragment;
import com.admonitor.tools.ActivityManager;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements OnPageChangeListener {
    public ViewPager pager;
    private PagerAdapter mAdapter;
    private ArrayList<Fragment> fragments;
    public ArrayList<RadioButton> title = new ArrayList<RadioButton>();
    SQLiteDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = openOrCreateDatabase("admonitor.db", Context.MODE_PRIVATE, null);
        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String _flag = sharedPreferences.getString("flag", "");
        if(_flag.equals("")) {
            db.execSQL("create table t_ad(a_id integer primary key autoincrement,a_name varchar,a_moderator varchar,a_address varchar,a_category varchar," +
                    "a_type varchar,a_size varchar,a_Illegal varchar,a_law varchar,a_date varchar,a_approval varchar," +
                    "a_remarks varchar,a_longitude varchar,a_latitude varchar,a_image varchar,a_city varchar)");
            SharedPreferences mySharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
            SharedPreferences.Editor editor = mySharedPreferences.edit();
            editor.putString("flag", "1");
            editor.commit();
        }
        ActivityManager.getInstance().addActivity(this);
        initView();
        initViewPager();
        initTitle();
    }
    private void initView() {
        pager = (ViewPager) findViewById(R.id.pager);
        fragments = new ArrayList<Fragment>();
        fragments.add(new IndexFragment());
        fragments.add(new FindFragment());
        fragments.add(new NewsFragment());
        fragments.add(new SetFragment());
    }

    private void initViewPager() {
        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager(),
                fragments);
        pager.setAdapter(mAdapter);
		pager.setOnPageChangeListener(this);
        pager.setCurrentItem(0);//
    }

    private void initTitle() {
        title.add((RadioButton) findViewById(R.id.title1));
        title.add((RadioButton) findViewById(R.id.title2));
        title.add((RadioButton) findViewById(R.id.title3));
        title.add((RadioButton) findViewById(R.id.title4));
        title.get(0).setOnClickListener(new MyOnClickListener(0));
        title.get(1).setOnClickListener(new MyOnClickListener(1));
        title.get(2).setOnClickListener(new MyOnClickListener(2));
        title.get(3).setOnClickListener(new MyOnClickListener(3));
        Drawable drawableWeiHui = getResources().getDrawable(R.drawable.tab_activity_selector1);
        drawableWeiHui.setBounds(0, 0, 40, 40);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        RadioButton r1 = (RadioButton) findViewById(R.id.title1);
        r1 .setCompoundDrawables(null, drawableWeiHui, null, null);

        Drawable drawableWeiHui1 = getResources().getDrawable(R.drawable.tab_activity_selector2);
        drawableWeiHui1.setBounds(0, 0, 40, 40);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        RadioButton r2 = (RadioButton) findViewById(R.id.title2);
        r2 .setCompoundDrawables(null, drawableWeiHui1, null, null);

        Drawable drawableWeiHui2 = getResources().getDrawable(R.drawable.tab_activity_selector3);
        drawableWeiHui2.setBounds(0, 0, 40, 40);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        RadioButton r3 = (RadioButton) findViewById(R.id.title3);
        r3 .setCompoundDrawables(null, drawableWeiHui2, null, null);

        Drawable drawableWeiHui3 = getResources().getDrawable(R.drawable.tab_activity_selector4);
        drawableWeiHui3.setBounds(0, 0, 40, 40);//第一0是距左右边距离，第二0是距上下边距离，第三69长度,第四宽度
        RadioButton r4 = (RadioButton) findViewById(R.id.title4);
        r4 .setCompoundDrawables(null, drawableWeiHui3, null, null);
    }

    private class MyOnClickListener implements View.OnClickListener {
        private int index;

        public MyOnClickListener(int index) {
            this.index = index;
        }

        @Override
        public void onClick(View v) {
           pager.setCurrentItem(index);//
            title.get(index).setChecked(true);
        }

    }
    @Override
    public void onPageScrollStateChanged(int arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    @Override
    public void onPageSelected(int arg0) {
        // TODO Auto-generated method stub
        pager.setCurrentItem(arg0);
        title.get(arg0).setChecked(true);
    }
}

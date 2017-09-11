package com.admonitor.activity;


import android.content.Context;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.admonitor.R;
import com.admonitor.adapter.DataManageAdapter;

import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.Ad;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/6/1.
 */
public class DataManageActivity extends BaseActivity {
    private LinearLayout back;
    private ListView listView;
    private List list = new ArrayList();
    private DataManageAdapter dataManageAdapter;
    SQLiteDatabase db;
    private int p,p1;
    private EditText content;
    private Button search;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datamanage);
        back = (LinearLayout) findViewById(R.id.back);
        content = (EditText) findViewById(R.id.content);
        search = (Button) findViewById(R.id.search);
        listView = (ListView)findViewById(R.id.content_view1);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Ad ad = (Ad) list.get((int)id);
                p = (int)id;
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("ad", ad);
                intent.putExtras(bundle);
                intent.setClass(DataManageActivity.this,SaveInfoActivity.class);
                startActivityForResult(intent,1);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                p1=(int)l;
                new AlertDialog.Builder(DataManageActivity.this).setTitle("系统提示")//设置对话框标题
                        .setMessage("确定删除该条数据？")//设置显示的内容
                        .setPositiveButton("确定",new DialogInterface.OnClickListener() {//添加确定按钮
                            @Override
                            public void onClick(DialogInterface dialog, int which) {//确定按钮的响应事件
                                // TODO Auto-generated method stub
                                Ad ad = (Ad) list.get(p1);
                                String sql = "delete from t_ad where a_id = "+ad.getA_id();
                                db.execSQL(sql);
                                list.remove(p1);
                                dataManageAdapter.notifyDataSetChanged();
                            }
                        }).setNegativeButton("返回",new DialogInterface.OnClickListener() {//添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {//响应事件
                        // TODO Auto-generated method stub

                    }
                }).show();//在按键响应事件中显示此对话框
        return true;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String _content = content.getText().toString();
                Cursor cr;
                cr = db.rawQuery("select * from t_ad where a_name||a_moderator||a_category||a_type||a_size||a_Illegal like '%'||'"+_content+"'||'%'", null);
                list.clear();
                while (cr.moveToNext()) {
                    Ad ad = new Ad();
                    ad.setA_id(cr.getInt(0));
                    ad.setA_name(cr.getString(1));
                    ad.setA_moderator(cr.getString(2));
                    ad.setA_address(cr.getString(3));
                    ad.setA_category(cr.getString(4));
                    ad.setA_type(cr.getString(5));
                    ad.setA_size(cr.getString(6));
                    ad.setA_Illegal(cr.getString(7));
                    ad.setA_law(cr.getString(8));
                    ad.setA_date(cr.getString(9));
                    ad.setA_approval(cr.getString(10));
                    ad.setA_remarks(cr.getString(11));
                    ad.setA_longitude(cr.getString(12));
                    ad.setA_latitude(cr.getString(13));
                    ad.setA_image(cr.getString(14));
                    ad.setA_city(cr.getString(15));
                    list.add(ad);
                }
                dataManageAdapter.notifyDataSetChanged();
            }
        });
        db = openOrCreateDatabase("admonitor.db", Context.MODE_PRIVATE, null);
                Cursor cr;
                cr = db.rawQuery("select * from t_ad", null);
                list.clear();
                while (cr.moveToNext()) {
                    Ad ad = new Ad();
                    ad.setA_id(cr.getInt(0));
                    ad.setA_name(cr.getString(1));
                    ad.setA_moderator(cr.getString(2));
                    ad.setA_address(cr.getString(3));
                    ad.setA_category(cr.getString(4));
                    ad.setA_type(cr.getString(5));
                    ad.setA_size(cr.getString(6));
                    ad.setA_Illegal(cr.getString(7));
                    ad.setA_law(cr.getString(8));
                    ad.setA_date(cr.getString(9));
                    ad.setA_approval(cr.getString(10));
                    ad.setA_remarks(cr.getString(11));
                    ad.setA_longitude(cr.getString(12));
                    ad.setA_latitude(cr.getString(13));
                    ad.setA_image(cr.getString(14));
                    ad.setA_city(cr.getString(15));
                    list.add(ad);
                }
        ActivityManager.getInstance().addActivity(this);
//        setData();
        dataManageAdapter=new DataManageAdapter(this,list);
        listView.setAdapter(dataManageAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                list.remove(p);
                dataManageAdapter.notifyDataSetChanged();
            }
        }
    }
}

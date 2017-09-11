package com.admonitor.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.admonitor.R;
import com.admonitor.adapter.LawAdapter;
import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.Dynamic;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.Law1;
import com.admonitor.tools.Law2;
import com.admonitor.tools.myApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.admonitor.adapter.ExpandableListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/7/17.
 */
public class LawActivity extends BaseActivity {
    private String id,token;
    private LinearLayout back;
    private JSONObject json;
    private JSONArray jsonArray, jsonArray1;
    private List list = new ArrayList();
    private List list1 = new ArrayList();
    private LawAdapter lawAdapter;
    private ListView listView;
    ExpandableListView expandableListView;
    Law1[] law1;
    Law2[] law2;
    private LinearLayout xia;
    private ExpandableListAdapter eA;
    private String lawStr="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_law);
        back = (LinearLayout) findViewById(R.id.back);
        expandableListView = (ExpandableListView)findViewById(R.id.law);
        xia = (LinearLayout) findViewById(R.id.xia);
        ActivityManager.getInstance().addActivity(this);
        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
        id=sharedPreferences.getString("userId","");
        token=sharedPreferences.getString("token","");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        expandableListView.setGroupIndicator(null);
//        setData();
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Law1 la = (Law1) list.get((int)l);
//                Bundle b = new Bundle();
//                b.putString("www",la.getW1_id()+"");
//                setResult(RESULT_OK,getIntent().putExtra("qqq",b));
//                Log.e("1","1");
//                finish();
//            }
//        });
        xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List a=eA.getList1();
                for(int i=0;i<a.size();i++){
                    lawStr = lawStr+a.get(i)+",";
                }
                String _lawStr=lawStr.substring(0,lawStr.length()-1);
                Bundle b = new Bundle();
                b.putString("www",_lawStr);
                setResult(RESULT_OK,getIntent().putExtra("qqq",b));
                finish();
            }
        });
        initExpandableListView();
        setData();

    }


    /**
     * ExpandableListView初始化方法
     */
    private void initExpandableListView()
    {

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener()
        {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id)
            {
                Toast.makeText(
                        LawActivity.this,
                        " Click on group " + groupPosition + " item "
                                + childPosition, Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        expandableListView
                .setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
                {

                    @Override
                    public boolean onItemLongClick(AdapterView<?> parent,
                                                   View view, int position, long id)
                    {
                        Toast.makeText(
                                LawActivity.this,
                                "LongClick on "
                                        + parent.getAdapter().getItemId(
                                        position), Toast.LENGTH_SHORT)
                                .show();
                        return true;
                    }
                });
        expandableListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener()
        {

            @Override
            public boolean onGroupClick(ExpandableListView parent, View v,
                                        int groupPosition, long id)
            {
                if (parent.isGroupExpanded(groupPosition))
                {
                    // 如果展开则关闭
                    parent.collapseGroup(groupPosition);
                } else
                {
                    // 如果关闭则打开，注意这里是手动打开不要默认滚动否则会有bug
                    parent.expandGroup(groupPosition);
                }
                return true;
            }
        });
    }

    public void setData(){
        new Thread(){
            public void run(){
                String url = myApp.url+"getLaw1";
                Map map = new HashMap();
                map.put("u_id",id);
                map.put("u_token",token);
                String res = new HttpRequest().postRequest(url,map);
                Message msg = new Message();
                msg.obj = res;
                handler.sendMessage(msg);
            }
        }.start();
    }
    public void setData1(){
        new Thread(){
            public void run(){
                String url = myApp.url+"getLaw2";
                Map map = new HashMap();
                map.put("u_id",id);
                map.put("u_token",token);
                String res = new HttpRequest().postRequest(url,map);
                Message msg = new Message();
                msg.obj = res;
                handler1.sendMessage(msg);
            }
        }.start();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String res = (String) msg.obj;
            try {
                if(res==null){
                    Toast.makeText(LawActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                }else {
                    json = new JSONObject(res);
                    if (json.getString("flag").equals("1")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", "");
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(LawActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ActivityManager.getInstance().exit();
                    } else if (json.getString("flag").equals("0")) {
                        jsonArray = json.getJSONArray("result");
                        if(jsonArray.length()!=0){
//                            list.clear();
                            law1 = new Law1[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Law1 d = new Law1();
                                d.setW1_id(jsonArray.getJSONObject(i).getInt("w1_id"));
                                d.setW1_title(jsonArray.getJSONObject(i).getString("w1_title"));
//                                list.add(d);
                                law1[i]=d;
                            }
                            setData1();
                        }else{
                            Toast.makeText(LawActivity.this, "暂无数据，稍后重试", Toast.LENGTH_LONG).show();
                        }
                    } else {

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    Handler handler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String res = (String) msg.obj;
            try {
                if(res==null){
                    Toast.makeText(LawActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                }else {
                    json = new JSONObject(res);
                    if (json.getString("flag").equals("1")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", "");
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(LawActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ActivityManager.getInstance().exit();
                    } else if (json.getString("flag").equals("0")) {
                        jsonArray = json.getJSONArray("result");
                        if(jsonArray.length()!=0){
//                            list1.clear();
                            law2 = new Law2[jsonArray.length()];
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Law2 d = new Law2();
                                d.setW2_id(jsonArray.getJSONObject(i).getInt("w2_id"));
                                d.setW2_title(jsonArray.getJSONObject(i).getString("w2_title"));
                                d.setW2_content(jsonArray.getJSONObject(i).getString("w2_content"));
                                d.setW1_id(jsonArray.getJSONObject(i).getJSONObject("law1").getInt("w1_id"));
                                d.setW1_title(jsonArray.getJSONObject(i).getJSONObject("law1").getString("w1_title"));
                                d.setFlag(false);
//                                list1.add(d);
                                law2[i]=d;
                            }
                            eA=new ExpandableListAdapter(LawActivity.this,law1,law2);
                            expandableListView.setAdapter(eA);
                        }else{
                            Toast.makeText(LawActivity.this, "暂无数据，稍后重试", Toast.LENGTH_LONG).show();
                        }
                    } else {

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}

package com.admonitor.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.admonitor.R;
import com.admonitor.adapter.CityAdapter;
import com.admonitor.adapter.ProvinceAdapter;
import com.admonitor.fragment.IndexFragment;
import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.Cities;
import com.admonitor.tools.CityActivityManager;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.News;
import com.admonitor.tools.Provinces;
import com.admonitor.tools.myApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by admin on 2017/6/2.
 */
public class CityActivity extends BaseActivity {
    private LinearLayout back;
    private ListView listView;
    private List list = new ArrayList();
    private CityAdapter cityAdapter;
    private String id,token;
    private JSONObject json;
    private JSONArray jsonArray;
    private String provinceid,province;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        back = (LinearLayout) findViewById(R.id.back);
        ActivityManager.getInstance().addActivity(this);
        listView = (ListView)findViewById(R.id.content_view1);
        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
        id=sharedPreferences.getString("userId","");
        token=sharedPreferences.getString("token","");
        provinceid=getIntent().getStringExtra("pid");
        province = getIntent().getStringExtra("pname");
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                News news = (News) list.get((int)id);
//                Intent intent = new Intent();
//                intent.putExtra("imageUrl",news.getUrl());
//                intent.putExtra("title","11111111");
//                intent.putExtra("date","2017-03-17");
//                intent.putExtra("content","3333333333");
//                intent.setClass(getActivity(),DynamicActivity.class);
//                startActivity(intent);
                Cities c = (Cities) list.get((int)id);
                IndexFragment a = new IndexFragment();
                if((int)id==0){
                    a.city.setText(c.getProvinces());
                }else {
                    a.city.setText(c.getCity());
                }
                finish();
            }
        });
        setData();
//        provinceAdapter=new ProvinceAdapter(this,list);
//        listView.setAdapter(provinceAdapter);
    }
    public void setData(){
        new Thread(){
            public void run(){
                String url = myApp.url+"cities";
                Map map = new HashMap();
                map.put("provinceid",provinceid);
                map.put("u_id",id);
                map.put("u_token",token);
                String res = new HttpRequest().postRequest(url,map);
                Message msg = new Message();
                msg.obj = res;
                handler.sendMessage(msg);
            }
        }.start();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String res = (String) msg.obj;
            try {
                if(res==null){
                    Toast.makeText(CityActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                }else {
                    json = new JSONObject(res);
                    if (json.getString("flag").equals("1")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", "");
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(CityActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ActivityManager.getInstance().exit();
                    } else if (json.getString("flag").equals("0")) {
                        jsonArray = json.getJSONArray("result");
                        if(jsonArray.length()!=0){
                            list.clear();
                            Cities c1 = new Cities();
                            c1.setProvinces(province);
                            list.add(c1);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Cities c = new Cities();
                                c.setC_id(jsonArray.getJSONObject(i).getInt("c_id"));
                                c.setCity(jsonArray.getJSONObject(i).getString("city"));
                                c.setCityid(jsonArray.getJSONObject(i).getString("cityid"));
                                list.add(c);
                            }
                            cityAdapter=new CityAdapter(CityActivity.this,list);
                            listView.setAdapter(cityAdapter);
                        }else{
                            Toast.makeText(CityActivity.this, "暂无数据，稍后重试", Toast.LENGTH_LONG).show();
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

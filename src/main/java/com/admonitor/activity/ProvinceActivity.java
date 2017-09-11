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
import com.admonitor.adapter.DataManageAdapter;
import com.admonitor.adapter.ProvinceAdapter;
import com.admonitor.fragment.IndexFragment;
import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.CityActivityManager;
import com.admonitor.tools.Dynamic;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.News;
import com.admonitor.tools.Provinces;
import com.admonitor.tools.myApp;
import com.listview.pulltorefresh.PullToRefreshLayout;

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
public class ProvinceActivity extends BaseActivity {
    private LinearLayout back;
    private ListView listView;
    private List list = new ArrayList();
    private ProvinceAdapter provinceAdapter;
    private String id,token;
    private JSONObject json;
    private JSONArray jsonArray;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_province);
        back = (LinearLayout) findViewById(R.id.back);
        listView = (ListView)findViewById(R.id.content_view1);
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Provinces p = (Provinces) list.get((int)id);
                if(p.getProvince().equals("北京市")||p.getProvince().equals("天津市")||p.getProvince().equals("上海市")||p.getProvince().equals("重庆市")){
                    IndexFragment a = new IndexFragment();
                    a.city.setText(p.getProvince());
                }else{
                    Intent intent = new Intent();
                    intent.putExtra("pid", p.getProvinceid());
                    intent.putExtra("pname", p.getProvince());
//                intent.putExtra("title","11111111");
//                intent.putExtra("date","2017-03-17");
//                intent.putExtra("content","3333333333");
                    intent.setClass(ProvinceActivity.this, CityActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        });
        setData();

    }
//    public void setData(){
//        list.clear();
//        for(int i=0;i<20;i++){
//            News news = new News();
//            news.setId(i);
//            news.setUrl("http://pic113.nipic.com/file/20161020/19302950_183756269001_2.jpg");
//            list.add(news);
//        }
//    }
    public void setData(){
        new Thread(){
            public void run(){
                String url = myApp.url+"provinces";
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
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String res = (String) msg.obj;
            try {
                if(res==null){
                    Toast.makeText(ProvinceActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                }else {
                    json = new JSONObject(res);
                    if (json.getString("flag").equals("1")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", "");
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(ProvinceActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ActivityManager.getInstance().exit();
                    } else if (json.getString("flag").equals("0")) {
                        jsonArray = json.getJSONArray("result");
                        if(jsonArray.length()!=0){
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Provinces p = new Provinces();
                                p.setId(jsonArray.getJSONObject(i).getInt("id"));
                                p.setProvince(jsonArray.getJSONObject(i).getString("province"));
                                p.setProvinceid(jsonArray.getJSONObject(i).getString("provinceid"));
                                list.add(p);
                            }
                            provinceAdapter=new ProvinceAdapter(ProvinceActivity.this,list);
                            listView.setAdapter(provinceAdapter);
                        }else{
                            Toast.makeText(ProvinceActivity.this, "暂无数据，稍后重试", Toast.LENGTH_LONG).show();
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

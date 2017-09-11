package com.admonitor.fragment;










import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.admonitor.R;
import com.admonitor.activity.LineActivity;
import com.admonitor.activity.LoginActivity;
import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.Dynamic;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.Performance;
import com.admonitor.tools.myApp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsFragment extends Fragment{
	private View view;
	private String id,token;
	private JSONObject json;
	private JSONArray jsonArray, jsonArray1;
	private List list = new ArrayList();
	private TextView ws,ys,zs,notice4;
	SQLiteDatabase db;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	view = inflater.inflate(R.layout.fragment_news, null);
	SharedPreferences sharedPreferences = getActivity().getSharedPreferences("adctim", Activity.MODE_PRIVATE);
	id=sharedPreferences.getString("userId","");
	token=sharedPreferences.getString("token","");
	ws = (TextView) view.findViewById(R.id.dianjiO1);
	notice4 = (TextView) view.findViewById(R.id.notice4);
	db = getActivity().openOrCreateDatabase("admonitor.db", Context.MODE_PRIVATE, null);
	Cursor cr;
	cr = db.rawQuery("select * from t_ad", null);
	ws.setText(cr.getCount()+"");
	notice4.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent();
			intent.setClass(getActivity(), LineActivity.class);
			startActivity(intent);
		}
	});
	return view;
}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if(isVisibleToUser){
//			setData();
		}
	}

	public void setData(){
		new Thread(){
			public void run(){
				String url = myApp.url+"Performance";
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
					Toast.makeText(getActivity(), "请求超时，请检查网络", Toast.LENGTH_LONG).show();
				}else {
					json = new JSONObject(res);
					if (json.getString("flag").equals("1")) {
						SharedPreferences sharedPreferences = getActivity().getSharedPreferences("adctim", Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.putString("name", "");
						editor.commit();
						Intent intent = new Intent();
						intent.setClass(getActivity(), LoginActivity.class);
						startActivity(intent);
						ActivityManager.getInstance().exit();
					} else if (json.getString("flag").equals("0")) {
						jsonArray = json.getJSONArray("result");
						if(jsonArray.length()!=0){
							list.clear();
//							for (int i = 0; i < jsonArray.length(); i++) {
//								Performance d = new Performance();
//								d.setP_id(jsonArray.getJSONObject(i).getInt("p_id"));
//								d.setP_ad_num(jsonArray.getJSONObject(i).getString("p_ad_num"));
//								d.setP_today_km(jsonArray.getJSONObject(i).getString("p_today_km"));
//								d.setName(jsonArray.getJSONObject(i).getString("name"));
//								d.setTitle(jsonArray.getJSONObject(i).getString("title"));
//								d.setUrl(jsonArray.getJSONObject(i).getString("url"));
								list.add(jsonArray);
//							}
							Log.i("222222",list.toString());
						}else{
							Toast.makeText(getActivity(), "暂无数据，稍后重试", Toast.LENGTH_LONG).show();
						}
					} else {

					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	};
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) 
{
	super.onActivityResult(requestCode, resultCode, data);
//	if (resultCode == getActivity().RESULT_OK) 
	if (requestCode == 0)
	{
		if(data==null){
			Toast.makeText(getActivity(), "取消", Toast.LENGTH_LONG).show();
		}else{
//		Bundle bundle = data.getExtras();
//		String scanResult = bundle.getString("result");
//		et_mailno_search.setText(scanResult);
//		et_mailno_search.setSelection(et_mailno_search.length());
//		Toast.makeText(getActivity(), scanResult, Toast.LENGTH_LONG).show();
		}
	}
}
}

package com.admonitor.fragment;







import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.admonitor.R;
import com.admonitor.activity.DynamicActivity;
import com.admonitor.activity.LoginActivity;
import com.admonitor.adapter.NewsAdapter;
import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.News;
import com.admonitor.tools.myApp;
import com.listview.pulltorefresh.PullToRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FindFragment extends Fragment{
	private View view;
	private PullToRefreshLayout pullToRefreshLayout;
	private ListView listView;
	private List list = new ArrayList();
	private int i = 1;
	private String id, token;
	public int totalpage;
	public NewsAdapter newsAdapter;
	private JSONObject json;
	private JSONArray jsonArray;
	private String[] imageUrls = {"http://pic113.nipic.com/file/20161020/19302950_183756269001_2.jpg",
			"http://pic114.nipic.com/file/20161110/5369219_194258865000_2.jpg",
			"http://pic110.nipic.com/file/20160918/18703062_162121463473_2.jpg",
			"http://pic113.nipic.com/file/20161031/20614752_152311024000_2.jpg"};
	List<String> imgUrlList;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	view = inflater.inflate(R.layout.fragment_find, null);
	SharedPreferences sharedPreferences = getActivity().getSharedPreferences("adctim", Activity.MODE_PRIVATE);
	id=sharedPreferences.getString("userId","");
	token=sharedPreferences.getString("token","");
	listView = (ListView)view.findViewById(R.id.content_view1);
	pullToRefreshLayout = ((PullToRefreshLayout) view.findViewById(R.id.refresh_view));
	imgUrlList = Arrays.asList(imageUrls);
	pullToRefreshLayout.setOnRefreshListener(new PullToRefreshLayout.OnRefreshListener() {
		@Override
		public void onRefresh(PullToRefreshLayout pullToRefreshLayout) {
			i=1;
			setData();
		}

		@Override
		public void onLoadMore(PullToRefreshLayout pullToRefreshLayout) {

			page();
		}
	});
	listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			News news = (News) list.get((int)id);
			Intent intent = new Intent();
			intent.putExtra("imageUrl",news.getN_url());
			intent.putExtra("title",news.getN_title());
			intent.putExtra("date",news.getN_release_date());
			intent.putExtra("content",news.getN_description());
			intent.setClass(getActivity(),DynamicActivity.class);
			startActivity(intent);
		}
	});

	setData();

	return view;
}
	public void page() {
		if (totalpage == i) {
			pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
			Toast.makeText(getActivity(), "全部加载完成", Toast.LENGTH_SHORT).show();
		} else {
			++i;
			setData();
		}
	}
	public void setData() {
		new Thread() {
			public void run() {
				String url = myApp.url + "news";
				Map map = new HashMap();
				map.put("u_id", id);
				map.put("u_token", token);
				map.put("pagenow", i+"");
				map.put("pagesize","10");
				String res = new HttpRequest().postRequest(url, map);
				Message msg = new Message();
				msg.obj = res;
				if(i==1){
					msg.arg1=1;
				}else{
					msg.arg1=2;
				}
				handler.sendMessage(msg);
			}
		}.start();
	}
	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			String res = (String) msg.obj;
			try {
				if (res == null) {
					pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
					pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
					Toast.makeText(getActivity(), "请求超时，稍后重试", Toast.LENGTH_LONG).show();
				} else {
					json = new JSONObject(res);
					if (json.getString("flag").equals("1")) {
						SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ctim", Activity.MODE_PRIVATE);
						SharedPreferences.Editor editor = sharedPreferences.edit();
						editor.putString("name", "");
						editor.commit();
						Intent intent = new Intent();
						intent.setClass(getActivity(), LoginActivity.class);
						startActivity(intent);
						ActivityManager.getInstance().exit();
					} else {
						totalpage = json.getInt("pagenum");
						jsonArray = json.getJSONArray("result");
						if (msg.arg1 == 1) {
							if(jsonArray.length()!=0){
								list.clear();
								for (int i = 0; i < jsonArray.length(); i++) {
									News n = new News();
									n.setN_id(jsonArray.getJSONObject(i).getInt("n_id"));
									n.setN_title(jsonArray.getJSONObject(i).getString("n_title"));
									n.setN_news_date(jsonArray.getJSONObject(i).getString("n_news_date"));
									n.setN_description(jsonArray.getJSONObject(i).getString("n_description"));
									n.setN_release_date(jsonArray.getJSONObject(i).getString("n_release_date"));
									n.setN_url(jsonArray.getJSONObject(i).getString("n_url"));
									list.add(n);
								}
								newsAdapter=new NewsAdapter(getActivity(),list);
								listView.setAdapter(newsAdapter);
								pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
							}else{
								pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
								Toast.makeText(getActivity(), "暂无数据，稍后重试", Toast.LENGTH_LONG).show();
							}
						}
						if (msg.arg1 == 2) {
							if(jsonArray.length()!=0){
								for (int i = 0; i < jsonArray.length(); i++) {
									News n = new News();
									n.setN_id(jsonArray.getJSONObject(i).getInt("n_id"));
									n.setN_title(jsonArray.getJSONObject(i).getString("n_title"));
									n.setN_news_date(jsonArray.getJSONObject(i).getString("n_news_date"));
									n.setN_description(jsonArray.getJSONObject(i).getString("n_description"));
									n.setN_release_date(jsonArray.getJSONObject(i).getString("n_release_date"));
									n.setN_url(jsonArray.getJSONObject(i).getString("n_url"));
									list.add(n);
								}
								newsAdapter.notifyDataSetChanged();
								pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
							}else{
								pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
								Toast.makeText(getActivity(), "暂无数据，稍后重试", Toast.LENGTH_LONG).show();
							}
						}
					}
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}

		}
	};
}
package com.admonitor.fragment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.admonitor.activity.DataManageActivity;
import com.admonitor.activity.DynamicActivity;
import com.admonitor.activity.FriendlyActivity;
import com.admonitor.activity.InfoAddActivity;
import com.admonitor.activity.LoginActivity;
import com.admonitor.activity.MainActivity;
import com.admonitor.activity.MapActivity;


import com.admonitor.R;
import com.admonitor.activity.ProvinceActivity;
import com.admonitor.adapter.MyViewPagerAdapter;

import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.Dynamic;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.myApp;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.carousel.ad.ADInfo;
import com.carousel.ad.Constant;
import com.carousel.ad.CycleViewPager;
import com.carousel.ad.RollHeaderView;
import com.carousel.ad.ViewFactory;
import com.listview.pulltorefresh.PullToRefreshLayout;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.squareup.okhttp.internal.Platform;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class IndexFragment extends Fragment {
	private View view;
	LinearLayout l1, l2, QRCodeId;
	ImageView shangla;
	private ViewPager pager;
	private PagerAdapter mAdapter;
	float x1 = 0;
	float x2 = 0;
	float y1 = 0;
	float y2 = 0;
//	private BaseApplication base;
	private ArrayList<Fragment> fragments;
	private ArrayList<RadioButton> title = new ArrayList<RadioButton>();
	private String memid, jingdu, weidu;
	private JSONObject json;
	private JSONArray jsonArray, jsonArray1;
	private ImageView smallId1, smallId2, smallId3, smallId4;
	DisplayImageOptions options;
	ImageLoaderConfiguration config;
	ImageLoader imageLoader;
	private String jsonStr;
	int i;
	private EditText searchId;
	private ScrollView sId;
	private ProgressDialog progressDialog=null;
	private List<ImageView> views = new ArrayList<ImageView>();
	private List list = new ArrayList();

	private CycleViewPager cycleViewPager;
	private String[] imageUrls = {"http://pic113.nipic.com/file/20161020/19302950_183756269001_2.jpg",
			"http://pic114.nipic.com/file/20161110/5369219_194258865000_2.jpg",
			"http://pic110.nipic.com/file/20160918/18703062_162121463473_2.jpg",
			"http://pic113.nipic.com/file/20161031/20614752_152311024000_2.jpg"};
    LinearLayout la,maps,add,youqing,jcbg,hlwjc,jihua;
	private ImageView dingwei,xiala;
	List imgUrlList=new ArrayList();
	public static TextView city;
	public LocationClient mLocationClient=null;
	public BDLocationListener myListener = new MyLocationListener();
	private static int REQUEST_THUMBNAIL = 1;// 请求缩略图信号标识
	private static int REQUEST_ORIGINAL = 2;// 请求原图信号标识
	private String sdPath;//SD卡的路径
	private String picPath;//图片存储路径
	private String id,token;
	RollHeaderView rollHeaderView;
	private File sdcardTempFile = new File("/mnt/sdcard/", "tmp_pic_" + SystemClock.currentThreadTimeMillis() + ".jpg");
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		view = inflater.inflate(R.layout.fragment_index, null);
		dingwei = (ImageView) view.findViewById(R.id.dingwei);
		maps = (LinearLayout) view.findViewById(R.id.map);
		add = (LinearLayout) view.findViewById(R.id.add);
		la = (LinearLayout) view.findViewById(R.id.rl_contanier);
		youqing= (LinearLayout) view.findViewById(R.id.youqing);
		jcbg = (LinearLayout) view.findViewById(R.id.jcbg);
		hlwjc = (LinearLayout) view.findViewById(R.id.hlwjc);
		xiala = (ImageView) view.findViewById(R.id.la);
		city = (TextView) view.findViewById(R.id.city);
		jihua = (LinearLayout) view.findViewById(R.id.jihua);
		rollHeaderView = new RollHeaderView(getActivity());
		SharedPreferences sharedPreferences = getActivity().getSharedPreferences("adctim", Activity.MODE_PRIVATE);
		id=sharedPreferences.getString("userId","");
		token=sharedPreferences.getString("token","");
		setData();
//		imgUrlList = Arrays.asList(imageUrls);
		rollHeaderView.setOnHeaderViewClickListener(new RollHeaderView.HeaderViewClickListener() {
			@Override
			public void HeaderViewClick(int position) {
				int p=position+1;
				Dynamic d = (Dynamic) list.get(position);
				Intent intent = new Intent();
				intent.putExtra("imageUrl",d.getUrl());
				intent.putExtra("title",d.getTitle());
				intent.putExtra("date",d.getDate());
				intent.putExtra("content",d.getContent());
                intent.setClass(getActivity(),DynamicActivity.class);
				startActivity(intent);
			}
		});

		jihua.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				MainActivity m = (MainActivity)getActivity();
				m.pager.setCurrentItem(2);
				m.title.get(2).setChecked(true);
			}
		});
		mLocationClient = new LocationClient(getContext());
		//声明LocationClient类
		mLocationClient.registerLocationListener( myListener );
		//注册监听函数
		dingwei.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
					ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,
							Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE,Manifest.permission.ACCESS_FINE_LOCATION}, 1);
				}
				initLocation();
				mLocationClient.start();
			}
		});
		maps.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(getActivity(),MapActivity.class);
				startActivity(intent);
			}
		});
		add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), InfoAddActivity.class);
				startActivity(intent);
			}
		});
		youqing.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), FriendlyActivity.class);
				startActivity(intent);
			}
		});
		hlwjc.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(getActivity(), DataManageActivity.class);
				startActivity(intent);
			}
		});
		xiala.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent();
				intent.setClass(getActivity(),ProvinceActivity.class);
				startActivity(intent);
			}
		});
		sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/AdImage";
		File dir=new File(sdPath);
		dir.mkdir();
		jcbg.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
					           ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
				}
				picPath = sdPath +"/"+ "tmp_pic_" + getDate() + ".jpg";
				Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				Uri uri = Uri.fromFile(new File(picPath));
				//为拍摄的图片指定一个存储的路径
				intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
				startActivityForResult(intent2, REQUEST_ORIGINAL);
			}
		});
		return view;
	}
	public String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		return formatter.format(new Date());
	}
	public void setData(){
		new Thread(){
			public void run(){
				String url = myApp.url+"dynamin";
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
							imgUrlList.clear();
							for (int i = 0; i < jsonArray.length(); i++) {
								imgUrlList.add(jsonArray.getJSONObject(i).getString("url"));
								Dynamic d = new Dynamic();
								d.setId(jsonArray.getJSONObject(i).getInt("id"));
								d.setDate(jsonArray.getJSONObject(i).getString("date"));
								d.setContent(jsonArray.getJSONObject(i).getString("content"));
								d.setName(jsonArray.getJSONObject(i).getString("name"));
								d.setTitle(jsonArray.getJSONObject(i).getString("title"));
								d.setUrl(jsonArray.getJSONObject(i).getString("url"));
								list.add(d);
							}
							rollHeaderView.setImgUrlData(imgUrlList);
							la.addView(rollHeaderView);
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
	/**
	 * 返回应用时回调方法
	 */
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode == getActivity().RESULT_OK) {
			if (requestCode == REQUEST_THUMBNAIL) {//对应第一种方法
				/**
				 * 通过这种方法取出的拍摄会默认压缩，因为如果相机的像素比较高拍摄出来的图会比较高清，
				 * 如果图太大会造成内存溢出（OOM），因此此种方法会默认给图片尽心压缩
				 */
				Bundle bundle = data.getExtras();
				Bitmap bitmap = (Bitmap) bundle.get("data");
				//mImageView.setImageBitmap(bitmap);
			}
			else if(requestCode == REQUEST_ORIGINAL){//对应第二种方法
				/**
				 * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
				 */
				FileInputStream fis = null;
				//try {
					Log.e("sdPath2",picPath);
					//把图片转化为字节流
					//fis = new FileInputStream(picPath);
					//把流转化图片
					//Bitmap bitmap = BitmapFactory.decodeStream(fis);
//					mImageView.setImageBitmap(bitmap);
					Toast.makeText(getActivity(),"照片已经上传",Toast.LENGTH_LONG).show();
				//} catch (FileNotFoundException e) {
					//e.printStackTrace();
				//}finally{
//					try {
//						//fis.close();//关闭流
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
			}
		}
	}



	Handler h = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			String address = (String) msg.obj;
			Toast toast=Toast.makeText(getActivity(),address,Toast.LENGTH_LONG);
			toast.setGravity(Gravity.CENTER,0,0);
			LinearLayout linearLayout = (LinearLayout) toast.getView();
			TextView messageTextView = (TextView) linearLayout.getChildAt(0);
			messageTextView.setTextSize(25);
			toast.show();
			mLocationClient.stop();
		}
	};
	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy);
		//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");
		//可选，默认gcj02，设置返回的定位结果坐标系
		int span=1000;
		option.setScanSpan(0);
		//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);
		//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);
		//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);
		//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);
		//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);
		//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);
		//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
		option.SetIgnoreCacheException(false);
		//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);
		//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}
	class MyLocationListener implements BDLocationListener{


		@Override
		public void onReceiveLocation(BDLocation bdLocation) {
			Message msg=new Message();
			msg.obj= bdLocation.getAddrStr();
			h.sendMessage(msg);
		}

		@Override
		public void onConnectHotSpotMessage(String s, int i) {

		}


	}

}

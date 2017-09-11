package com.admonitor.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.admonitor.R;
import com.admonitor.adapter.ProvinceAdapter;
import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.Ad;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.Provinces;
import com.admonitor.tools.myApp;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.map.MyLocationConfiguration.LocationMode;
import com.carousel.ad.ADInfo;
import com.carousel.ad.CycleViewPager;
import com.carousel.ad.RollHeaderView;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by admin on 2017/5/25.
 */
public class MapActivity extends BaseActivity {
    private LinearLayout back;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap;
    public LocationClient mLocClient=null;
    private LocationMode mCurrentMode;// 定位模式
    BitmapDescriptor mCurrentMarker;// Marker图标
    public BDLocationListener myListener = new MyLocationListenner();
    private String id,token;
    private JSONObject json;
    private JSONArray jsonArray;
    private List list = new ArrayList();
    private List<ImageView> views = new ArrayList<ImageView>();
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private RollHeaderView rollHeaderView;
    private LinearLayout la;
    private List imgUrlList=new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        ActivityManager.getInstance().addActivity(this);
        mMapView = (MapView) findViewById(R.id.bmapView);
        mBaiduMap = mMapView.getMap();
        rollHeaderView = new RollHeaderView(this);
        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
        id=sharedPreferences.getString("userId","");
        token=sharedPreferences.getString("token","");
        mCurrentMode = LocationMode.NORMAL;// 设置定位模式为普通
//        mCurrentMarker = BitmapDescriptorFactory// 构建mark图标
//                .fromResource(R.drawable.dw);
//        //普通地图
        mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
        // 开启定位图层
         mBaiduMap.setMyLocationEnabled(true);
         mBaiduMap.setMyLocationConfigeration(new MyLocationConfiguration(
         mCurrentMode, true, null));
        // 定位初始化
        mLocClient = new LocationClient(this);
        mLocClient.registerLocationListener(myListener);// 注册监听函数：
        LocationClientOption option = new LocationClientOption();
        option.setCoorType("bd09ll");// 返回的定位结果是百度经纬度,默认值gcj02
        option.setScanSpan(0);// 设置发起定位请求的间隔时间为5000ms
        option.setIsNeedAddress(true);// 返回的定位结果包含地址信息
        option.setNeedDeviceDirect(true);// 返回的定位结果包含手机机头的方向
        mLocClient.setLocOption(option);
        mLocClient.start();
        MapStatus.Builder builder = new MapStatus.Builder();
        builder.zoom(18);
        mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setData1();
        mBaiduMap.setOnMapStatusChangeListener(listener1);
    }
    public void setData1(){
        new Thread(){
            public void run(){
                String url = myApp.url+"getSelectAd";
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
                    Toast.makeText(MapActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                }else {
                    json = new JSONObject(res);
                    if (json.getString("flag").equals("1")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", "");
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(MapActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ActivityManager.getInstance().exit();
                    } else if (json.getString("flag").equals("0")) {
                        jsonArray = json.getJSONArray("result");
                        if(jsonArray.length()!=0){
                            list.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Ad ad = new Ad();
                                ad.setA_id(jsonArray.getJSONObject(i).getInt("a_id"));
                                ad.setA_address(jsonArray.getJSONObject(i).getString("a_address"));
                                ad.setA_approval(jsonArray.getJSONObject(i).getString("a_approval"));
                                ad.setA_category(jsonArray.getJSONObject(i).getString("a_category"));
                                ad.setA_city(jsonArray.getJSONObject(i).getString("a_city"));
                                ad.setA_date(jsonArray.getJSONObject(i).getString("a_date"));
                                ad.setA_Illegal(jsonArray.getJSONObject(i).getString("a_Illegal"));
                                ad.setA_image(jsonArray.getJSONObject(i).getString("a_image"));
                                ad.setA_latitude(jsonArray.getJSONObject(i).getString("a_latitude"));
                                ad.setA_law(jsonArray.getJSONObject(i).getString("a_law"));
                                ad.setA_longitude(jsonArray.getJSONObject(i).getString("a_longitude"));
                                ad.setA_moderator(jsonArray.getJSONObject(i).getString("a_moderator"));
                                ad.setA_name(jsonArray.getJSONObject(i).getString("a_name"));
                                ad.setA_remarks(jsonArray.getJSONObject(i).getString("a_remarks"));
                                ad.setA_size(jsonArray.getJSONObject(i).getString("a_size"));
                                ad.setName(jsonArray.getJSONObject(i).getJSONObject("user").getString("u_name"));
                                ad.setPhone(jsonArray.getJSONObject(i).getJSONObject("user").getString("u_phone"));
                                ad.setWork(jsonArray.getJSONObject(i).getJSONObject("user").getJSONObject("department").getString("d_name"));
                                list.add(ad);
                            }
                            for(int i=0;i<list.size();i++) {
                                Ad ad = (Ad) list.get(i);
                                LatLng point = new LatLng(Double.parseDouble(ad.getA_latitude()), Double.parseDouble(ad.getA_longitude()));
                                mCurrentMarker = BitmapDescriptorFactory// 构建mark图标
                                        .fromResource(R.drawable.dw);
                                MarkerOptions ooA = new MarkerOptions().position(point).icon(mCurrentMarker)
                                        .zIndex(10).draggable(true).title(i+"");
                                mBaiduMap.addOverlay(ooA);
                                mBaiduMap.setOnMarkerClickListener(listener);
                            }
                        }else{
                            Toast.makeText(MapActivity.this, "暂无数据，稍后重试", Toast.LENGTH_LONG).show();
                        }
                    } else {

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
    BaiduMap.OnMapStatusChangeListener listener1 = new BaiduMap.OnMapStatusChangeListener() {
        /**
         * 手势操作地图，设置地图状态等操作导致地图状态开始改变。
         * @param status 地图状态改变开始时的地图状态
         */
        public void onMapStatusChangeStart(MapStatus status){
        }
        /**
         * 地图状态变化中
         * @param status 当前地图状态
         */
        public void onMapStatusChange(MapStatus status){
        }
        /**
         * 地图状态改变结束
         * @param status 地图状态改变结束后的地图状态
         */
        public void onMapStatusChangeFinish(MapStatus status){
//            Toast.makeText(MapActivity.this,""+(int)status.zoom,Toast.LENGTH_LONG).show();
        }
    };
    BaiduMap.OnMarkerClickListener listener = new BaiduMap.OnMarkerClickListener() {
        /**
         * 地图 Marker 覆盖物点击事件监听函数
         * @param marker 被点击的 marker
         */
        public boolean onMarkerClick(Marker marker){
            LatLng point2 = marker.getPosition();
            String title = marker.getTitle();
            Ad ad = (Ad) list.get(Integer.parseInt(title));
             Intent intent = new Intent();
            intent.putExtra("name",ad.getA_name());
            intent.putExtra("zhu",ad.getA_moderator());
            intent.putExtra("address",ad.getA_address());
            intent.putExtra("leibie",ad.getA_category());
            intent.putExtra("leixing",ad.getA_type());
            intent.putExtra("size",ad.getA_size());
            intent.putExtra("weifa",ad.getA_Illegal());
            intent.putExtra("falv",ad.getA_law());
            intent.putExtra("date",ad.getA_date());
            intent.putExtra("shenpi",ad.getA_approval());
            intent.putExtra("beizhu",ad.getA_remarks());
            intent.putExtra("user",ad.getName());
            intent.putExtra("work",ad.getWork());
            intent.putExtra("phone",ad.getPhone());
            intent.putExtra("image",ad.getA_image());
            intent.setClass(MapActivity.this,DetailActivity.class);
            startActivity(intent);
//            Button button = new Button(getApplicationContext());
//            button.setBackgroundResource(R.drawable.dw);
//            InfoWindow mInfoWindow = new InfoWindow(view, point2, -47);
//             //显示InfoWindow
//            mBaiduMap.showInfoWindow(mInfoWindow);
            return true;
        }
    };
    void dingwei(){
        new Thread(){
            @Override
            public void run() {
                super.run();
            }
        }.start();
    }
    public class MyLocationListenner implements BDLocationListener {
        @Override
        public void onReceiveLocation(BDLocation location) {
            // map view 销毁后不在处理新接收的位置
            if (location == null || mMapView == null) {
                return;
            }
//            mBaiduMap.clear();
            MyLocationData locData = new MyLocationData.Builder()
                    .accuracy(location.getRadius())
                    // 此处设置开发者获取到的方向信息，顺时针0-360
                    .direction(100).latitude(location.getLatitude())
                    .longitude(location.getLongitude()).build();
            LatLng point=new LatLng(location.getLatitude(),location.getLongitude());
            mBaiduMap.setMyLocationData(locData);
//            MarkerOptions ooA = new MarkerOptions().position(point).icon(mCurrentMarker)
//                    .zIndex(10).draggable(true);
//            mBaiduMap.addOverlay(ooA);
            MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(point);
            mBaiduMap.animateMapStatus(u);
            MapStatus.Builder builder = new MapStatus.Builder();
                builder.target(point).zoom(18);
                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            if (isFirstLoc) {
//                isFirstLoc = false;
//                LatLng ll = new LatLng(location.getLatitude(),
//                        location.getLongitude());
//                MapStatus.Builder builder = new MapStatus.Builder();
//                builder.target(ll).zoom(18.0f);
//                mBaiduMap.animateMapStatus(MapStatusUpdateFactory.newMapStatus(builder.build()));
//            }
        }

        @Override
        public void onConnectHotSpotMessage(String s, int i) {

        }


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
        mLocClient.stop();
    }
    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }
    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
    /**
     * 配置ImageLoder
     */
    private void configImageLoader() {
        // 初始化ImageLoader
        @SuppressWarnings("deprecation")
        DisplayImageOptions options = new DisplayImageOptions.Builder().showStubImage(R.drawable.icon_stub) // 设置图片下载期间显示的图片
                .showImageForEmptyUri(R.drawable.icon_empty) // 设置图片Uri为空或是错误的时候显示的图片
                .showImageOnFail(R.drawable.icon_error) // 设置图片加载或解码过程中发生错误显示的图片
                .cacheInMemory(true) // 设置下载的图片是否缓存在内存中
                .cacheOnDisc(true) // 设置下载的图片是否缓存在SD卡中
                // .displayer(new RoundedBitmapDisplayer(20)) // 设置成圆角图片
                .build(); // 创建配置过得DisplayImageOption对象

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext()).defaultDisplayImageOptions(options)
                .threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .discCacheFileNameGenerator(new Md5FileNameGenerator()).tasksProcessingOrder(QueueProcessingType.LIFO).build();
        ImageLoader.getInstance().init(config);
    }
}

package com.admonitor.activity;

/**
 * Created by admin on 2017/7/27.
 */

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.admonitor.R;
import com.admonitor.adapter.ImageAdapter;
import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.Ad;
import com.admonitor.tools.ImageUtil;
import com.admonitor.tools.NewRequest;
import com.admonitor.tools.myApp;;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;




/**
 * Created by admin on 2017/5/26.
 */
public class SaveInfoActivity extends BaseActivity {
    private LinearLayout back;
    GridView g;
    private EditText falv,address1,address2,name,shenpi,zname,beizhu;
    private List imageList = new ArrayList();
    private List<String> list = new ArrayList<String>();
    private List<String> leixingList = new ArrayList<String>();
    private List<String> sizeList = new ArrayList<String>();
    private List<String> weifaList = new ArrayList<String>();
    private Spinner leibie,leixing,chichun,weifa;
    private ArrayAdapter<String> adapter,adapter1,adapter2,adapter3;
    private ImageAdapter imageAdapter;
    private Button paizhao,xc,save,submit;
    private String sdPath;//SD卡的路径
    private String picPath;//图片存储路径
    private ImageView hao;
    private String id,token;
    private JSONObject json;
    private JSONArray jsonArray;
    private String jing,wei,city,_leibie,_leixing,_chichun,_weifa,_name,_zname,_shenpi,_beizhu,_falv,_date;
    SQLiteDatabase db;
    private Ad ad;
    private int p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_save);
        back = (LinearLayout) findViewById(R.id.back);
        g = (GridView) findViewById(R.id.gridview);
        address1 = (EditText) findViewById(R.id.address1);
        address2 = (EditText) findViewById(R.id.address2);
        leibie = (Spinner)findViewById(R.id.leibie);
        leixing= (Spinner)findViewById(R.id.leixing);
        chichun= (Spinner)findViewById(R.id.chicun);
        weifa = (Spinner) findViewById(R.id.weifa);
        shenpi = (EditText) findViewById(R.id.shenpi);
        zname = (EditText) findViewById(R.id.zname);
        paizhao = (Button) findViewById(R.id.paizhao);
        name = (EditText) findViewById(R.id.name);
        beizhu = (EditText) findViewById(R.id.beizhu);
        submit = (Button) findViewById(R.id.submit);
        xc = (Button) findViewById(R.id.xc);
        falv = (EditText) findViewById(R.id.falv);
        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
        id=sharedPreferences.getString("userId","");
        token=sharedPreferences.getString("token","");
        liebie();
        leixing();
        chichun();
        weifa();
        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);
        adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, leixingList);
        adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, sizeList);
        adapter3 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, weifaList);
        //第三步：为适配器设置下拉列表下拉时的菜单样式。
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        adapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        leibie.setAdapter(adapter);
        leixing.setAdapter(adapter1);
        chichun.setAdapter(adapter2);
        weifa.setAdapter(adapter3);
        leibie.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
//                myTextView.setText("您选择的是："+ adapter.getItem(arg2));
                /* 将mySpinner 显示*/
//                arg0.setVisibility(View.VISIBLE);
                _leibie = arg0.getAdapter().getItem(arg2)+"";
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
//                myTextView.setText("NONE");
//                arg0.setVisibility(View.VISIBLE);
            }
        });
        weifa.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
//                myTextView.setText("您选择的是："+ adapter.getItem(arg2));
                /* 将mySpinner 显示*/
//                arg0.setVisibility(View.VISIBLE);
                _weifa = arg0.getAdapter().getItem(arg2)+"";
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
//                myTextView.setText("NONE");
//                arg0.setVisibility(View.VISIBLE);
            }
        });
        leixing.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
//                myTextView.setText("您选择的是："+ adapter.getItem(arg2));
                /* 将mySpinner 显示*/
//                arg0.setVisibility(View.VISIBLE);
                _leixing = arg0.getAdapter().getItem(arg2)+"";
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
//                myTextView.setText("NONE");
//                arg0.setVisibility(View.VISIBLE);
            }
        });
        chichun.setOnItemSelectedListener(new Spinner.OnItemSelectedListener(){
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                // TODO Auto-generated method stub
                /* 将所选mySpinner 的值带入myTextView 中*/
//                myTextView.setText("您选择的是："+ adapter.getItem(arg2));
                /* 将mySpinner 显示*/
//                arg0.setVisibility(View.VISIBLE);
                _chichun=arg0.getAdapter().getItem(arg2)+"";
            }
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
//                myTextView.setText("NONE");
//                arg0.setVisibility(View.VISIBLE);
            }
        });
        falv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(SaveInfoActivity.this,LawActivity.class);
                startActivityForResult(intent,1);
            }
        });
        sdPath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/AdImage";
        File dir=new File(sdPath);
        dir.mkdir();
        paizhao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageList.size()<4) {
                    if (ContextCompat.checkSelfPermission(SaveInfoActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(SaveInfoActivity.this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                    }
                    picPath = sdPath +"/"+ "tmp_pic_" + getDate() + ".jpg";
                    Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    Uri uri = Uri.fromFile(new File(picPath));
                    //为拍摄的图片指定一个存储的路径
                    intent2.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent2, 0);
                }else{
                    Toast.makeText(SaveInfoActivity.this, "最多传四张", Toast.LENGTH_LONG).show();
                }
            }
        });
        xc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(imageList.size()<4) {
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("image/*");
                    startActivityForResult(intent,2);
                }else{
                    Toast.makeText(SaveInfoActivity.this, "最多传四张", Toast.LENGTH_LONG).show();
                }
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                _name = name.getText().toString();
                _zname = zname.getText().toString();
                _shenpi = shenpi.getText().toString();
                _beizhu = beizhu.getText().toString();
                _falv = falv.getText().toString();
                setData();
            }
        });
        ActivityManager.getInstance().addActivity(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = this.getIntent();
        ad=(Ad)intent.getSerializableExtra("ad");
        p = ad.getA_id();
        name.setText(ad.getA_name());
        leibie.setSelection(adapter.getPosition(ad.getA_category()));
        leixing.setSelection(adapter1.getPosition(ad.getA_type()));
        weifa.setSelection(adapter3.getPosition(ad.getA_Illegal()));
        chichun.setSelection(adapter2.getPosition(ad.getA_size()));
        address1.setText(ad.getA_address());
        falv.setText(ad.getA_law());
        shenpi.setText(ad.getA_approval());
        zname.setText(ad.getA_moderator());
        beizhu.setText(ad.getA_remarks());
        jing=ad.getA_longitude();
        wei=ad.getA_latitude();
        city = ad.getA_city();
        _date=ad.getA_date();
        if(!ad.getA_image().equals("")) {
            imageList.clear();
            String[] str = ad.getA_image().split(",");
            for(int i=0;i<str.length;i++){
                imageList.add(str[i]);
            }
        }
        imageAdapter = new ImageAdapter(this,imageList);
        g.setAdapter(imageAdapter);

    }
    public void liebie(){
        list.add("未知");
        list.add("药品");
        list.add("医疗服务");
        list.add("农药");
        list.add("兽医");
        list.add("食品");
        list.add("酒类");
        list.add("化妆品");
        list.add("房地产");
    }
    public void leixing(){
        leixingList.add("未知");
        leixingList.add("公交站牌");
        leixingList.add("外墙面广告");
        leixingList.add("临时围栏广告");
        leixingList.add("路灯杆广告");
        leixingList.add("灯箱广告");
        leixingList.add("高速公路广告");
        leixingList.add("电子屏广告");
        leixingList.add("其他");
    }
    public void chichun(){
        sizeList.add("未知");
        sizeList.add("小型（1-10平方米）");
        sizeList.add("中型（11-20平方米）");
        sizeList.add("大型（21-50平方米）");
        sizeList.add("超大型（50平方米以上）");
    }
    public void weifa(){
        weifaList.add("未知");
        weifaList.add("不违法");
        weifaList.add("一般违法");
        weifaList.add("严重违法");
    }
    public String getDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHH:mm:ss");
        return formatter.format(new Date());
    }
    public String getDate1() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(new Date());
    }
    public void setData(){
        new Thread(){
            public void run(){
                String url = myApp.url+"getAddAd";
                Map map = new HashMap();
                map.put("u_id",id);
                map.put("u_token",token);
                map.put("a_name",_name);
                map.put("a_moderator",_zname);
                map.put("a_address",address1.getText().toString()+""+address2.getText().toString());
                map.put("a_category",_leibie);
                map.put("a_type",_leixing);
                map.put("a_size",_chichun);
                map.put("a_Illegal",_weifa);
                map.put("a_law",_falv);
                map.put("a_date",_date);
                map.put("a_approval",_shenpi);
                map.put("a_remarks",_beizhu);
                map.put("a_longitude",jing);
                map.put("a_latitude",wei);
                map.put("a_u_id",id);
                map.put("a_city",city);
                String res = new NewRequest().postRequest(url,map,imageList);
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
                    Toast.makeText(SaveInfoActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                }else {
                    json = new JSONObject(res);
                    if (json.getString("flag").equals("1")) {
                        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("name", "");
                        editor.commit();
                        Intent intent = new Intent();
                        intent.setClass(SaveInfoActivity.this, LoginActivity.class);
                        startActivity(intent);
                        ActivityManager.getInstance().exit();
                    } else if (json.getString("flag").equals("0")) {
                        db = openOrCreateDatabase("admonitor.db", Context.MODE_PRIVATE, null);
                        String sql = "delete from t_ad where a_id = "+p;
                        db.execSQL(sql);
                        Toast.makeText(SaveInfoActivity.this, "上传成功", Toast.LENGTH_LONG).show();
                        setResult(RESULT_OK,getIntent());
                        finish();
                    }else{


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
        if (resultCode == RESULT_OK) {
            ContentResolver resolver = getContentResolver();
            Bitmap bm = null;
            if (requestCode == 1) {//对应第一种方法
                /**
                 * 通过这种方法取出的拍摄会默认压缩，因为如果相机的像素比较高拍摄出来的图会比较高清，
                 * 如果图太大会造成内存溢出（OOM），因此此种方法会默认给图片尽心压缩
                 */
                Log.e("1","1");
                Bundle bundle = data.getBundleExtra("qqq");
//                Bitmap bitmap = (Bitmap) bundle.get("data");
                //mImageView.setImageBitmap(bitmap);
                falv.setText(bundle.getString("www"));
            } else if(requestCode == 2){
                imageList.add( ImageUtil.getImageAbsolutePath(SaveInfoActivity.this,data.getData()));
                imageAdapter = new ImageAdapter(this,imageList);
                g.setAdapter(imageAdapter);
                Toast.makeText(this, "照片已经上传", Toast.LENGTH_LONG).show();
                Log.e("sdPath2--------", ImageUtil.getImageAbsolutePath(SaveInfoActivity.this,data.getData())+"");
            }else if (requestCode == 0) {//对应第二种方法
                /**
                 * 这种方法是通过内存卡的路径进行读取图片，所以的到的图片是拍摄的原图
                 */
//                FileInputStream fis = null;
//                try {
//                Bundle bundle = data.getExtras();
//                Bitmap bitmap = (Bitmap) bundle.get("data");
                Log.e("sdPath2", picPath);
                //把图片转化为字节流
//                fis = new FileInputStream(picPath);
                //把流转化图片
//                Bitmap bitmap = BitmapFactory.decodeStream(fis);
//					mImageView.setImageBitmap(bitmap);
//                hao.setImageBitmap(bitmap);

                imageList.add(picPath);
                imageAdapter = new ImageAdapter(this,imageList);
                g.setAdapter(imageAdapter);
                Toast.makeText(this, "照片已经上传", Toast.LENGTH_LONG).show();
//                } catch (FileNotFoundException e) {
//                e.printStackTrace();
//                }finally{
//					try {
//						fis.close();//关闭流
//					} catch (IOException e) {
//						e.printStackTrace();
//					}
//				}
            }
        }
    }
}

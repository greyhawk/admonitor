package com.admonitor.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.admonitor.R;
import com.admonitor.tools.ActivityManager;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.myApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/12/6.
 */
public class UserUpdateActivity extends Activity {
    private LinearLayout back;
    private Button updateuser;
    private EditText old,newpass,newpassag;
    private String _old,_newpass,_newpassag;
    private String id,token,oldpass;
    private JSONObject json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userupdate);
        back = (LinearLayout) findViewById(R.id.back);
        updateuser = (Button) findViewById(R.id.updateuser);
        old = (EditText) findViewById(R.id.old);
        newpass = (EditText) findViewById(R.id.newpass);
        newpassag = (EditText) findViewById(R.id.newpassag);
        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        id = sharedPreferences.getString("userId", "");
        token = sharedPreferences.getString("token", "");
        oldpass = sharedPreferences.getString("pass", "");
        ActivityManager.getInstance().addActivity(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        updateuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                _old = old.getText().toString();
                Log.i("1111111",_old);
                Log.i("2222222",oldpass);
                _newpass = newpass.getText().toString();
                _newpassag = newpassag.getText().toString();
                if(oldpass.equals(_old)){
                    if(_newpass.equals(_newpassag)){
                        if(_old.equals(_newpass)){
                            Toast.makeText(UserUpdateActivity.this, "原始密码和新密码一致", Toast.LENGTH_LONG).show();
                        }else{
                            setData();
                        }
                    }else{
                        Toast.makeText(UserUpdateActivity.this, "新密码不一致", Toast.LENGTH_LONG).show();
                    }
                }else{
                    Toast.makeText(UserUpdateActivity.this, "原始密码不正确", Toast.LENGTH_LONG).show();
                }

            }
        });
    }
    public void setData(){
        new Thread(){
            public void run(){

                String url = myApp.url+"updateUser";
                Map map = new HashMap();
                map.put("u_id",id);
                map.put("u_token",token);
                map.put("u_password",_newpass);
                String res = new HttpRequest().postRequest(url,map);
                Message msg = new Message();
                msg.obj = res;
                handler.sendMessage(msg);
            }
        }.start();
    }
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String res = (String) msg.obj;
            try {
                if(res==null){
                    Toast.makeText(UserUpdateActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                }else {
                    json = new JSONObject(res);
                   if(json.getString("flag").equals("0")) {
                       Toast.makeText(UserUpdateActivity.this, json.getString("msg"), Toast.LENGTH_LONG).show();
                       SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.putString("pass", _newpass);
                       editor.commit();
                       finish();
                   }else if(json.getString("flag").equals("2")){
                       Toast.makeText(UserUpdateActivity.this, json.getString("msg"), Toast.LENGTH_LONG).show();
                   }else{
                       Toast.makeText(UserUpdateActivity.this, json.getString("msg"), Toast.LENGTH_LONG).show();
                       SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                       SharedPreferences.Editor editor = sharedPreferences.edit();
                       editor.putString("name", "");
                       editor.commit();
                       Intent intent = new Intent();
                       intent.setClass(UserUpdateActivity.this,LoginActivity.class);
                       startActivity(intent);
                       ActivityManager.getInstance().exit();
                   }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    };
}

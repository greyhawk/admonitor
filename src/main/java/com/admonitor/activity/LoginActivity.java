package com.admonitor.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.admonitor.R;
import com.admonitor.tools.HttpRequest;
import com.admonitor.tools.myApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2016/12/5.
 */
public class LoginActivity extends BaseActivity {
    private Button loginButton;
    private EditText username,password;
    private String _username,_password;
    private JSONObject json;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        String _name = sharedPreferences.getString("name", "");
        if (!_name.equals("")) {
            Intent intent = new Intent();
            intent.setClass(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            setContentView(R.layout.activity_login);
            loginButton = (Button) findViewById(R.id.login);
            username = (EditText) findViewById(R.id.username);
            password = (EditText) findViewById(R.id.password);
            loginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    _username = username.getText().toString();
                    _password = password.getText().toString();
                    if(_username.equals("")||_password.equals("")){
                        Toast.makeText(LoginActivity.this, "用户名和密码不能为空", Toast.LENGTH_LONG).show();
                    }else{
                        setData();
                    }

                }
            });
        }
//        LoginActivity.this.runOnUiThread(new Runnable(){
//            @Override
//            public void run() {
//
//            }
//        });
    }
    public void setData(){
        new Thread(){
            public void run(){
            String url = myApp.url+"login";
            Map map = new HashMap();
            map.put("u_username",_username);
            map.put("u_password",_password);
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
                    Toast.makeText(LoginActivity.this, "请求超时，请检查网络", Toast.LENGTH_LONG).show();
                }else {
                    json = new JSONObject(res);
                    if (json.getString("flag").equals("1")) {
                        Toast.makeText(LoginActivity.this, json.getString("msg"), Toast.LENGTH_LONG).show();
                    } else if (json.getString("flag").equals("0")) {
                        SharedPreferences mySharedPreferences = getSharedPreferences("adctim", Activity.MODE_PRIVATE);
                        SharedPreferences.Editor editor = mySharedPreferences.edit();
                        editor.putString("name", json.getString("userName"));
                        editor.putString("pass", json.getString("passWord"));
                        editor.putString("token", json.getString("token"));
                        editor.putString("userId", json.getString("id"));
                        editor.putString("cityId", json.getString("city"));
                        editor.commit();
                        Toast.makeText(LoginActivity.this, json.getString("msg"), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {

                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    };
}

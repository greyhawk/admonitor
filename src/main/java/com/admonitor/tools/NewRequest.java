package com.admonitor.tools;

/**
 * Created by admin on 2017/7/12.
 */

import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;




/**
 * Created by admin on 2016/12/15.
 */
public class NewRequest {
    public String res;
    public String postRequest(final String url, final Map<String,String> map,List list){
        OkHttpClient client = new OkHttpClient();
        MultipartBuilder builder = new MultipartBuilder().type(MultipartBuilder.FORM);
        FormEncodingBuilder formBody = new FormEncodingBuilder();
        for (String key : map.keySet()) {
            builder.addFormDataPart(key, map.get(key));
        }
        for (int i = 0; i <list.size();i++) {
            File f=new File((String)list.get(i));
            Log.i("url----------------",(String)list.get(i));
            if (f!=null) {
                builder.addFormDataPart("myfiles",(String)list.get(i),RequestBody.create(MediaType.parse("multipart/form-data"),f));
            }
        }
        RequestBody body = builder.build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response;
        try {
            client.setConnectTimeout(30, TimeUnit.SECONDS);
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                res = response.body().string();
            }else{
                res = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }
}

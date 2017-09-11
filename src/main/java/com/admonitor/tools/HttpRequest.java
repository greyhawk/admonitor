package com.admonitor.tools;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by admin on 2016/12/15.
 */
public class HttpRequest {
    public String res;
    public String postRequest(final String url, final Map<String,String> map){
            OkHttpClient client = new OkHttpClient();
                FormEncodingBuilder formBody = new FormEncodingBuilder();
                for (String key : map.keySet()) {
                    formBody.add(key, map.get(key));
                }
                RequestBody body = formBody.build();
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

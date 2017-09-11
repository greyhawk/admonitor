package com.admonitor.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;

import com.admonitor.R;
import com.admonitor.tools.ActivityManager;

/**
 * Created by admin on 2017/5/31.
 */
public class FriendlyActivity extends BaseActivity {
    private LinearLayout back,jcbg,add,jcbg1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friendly);
        jcbg = (LinearLayout) findViewById(R.id.jcbg);
        add  = (LinearLayout) findViewById(R.id.add);
        jcbg1 = (LinearLayout) findViewById(R.id.jcbg1);
        ActivityManager.getInstance().addActivity(this);
        back = (LinearLayout) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        jcbg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.saic.gov.cn/");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.hebgs.gov.cn/yw/yp/s-index.asp");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
        jcbg1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.hebfda.gov.cn/CL0001/index.html");
                intent.setData(content_url);
                startActivity(intent);
            }
        });
    }
}

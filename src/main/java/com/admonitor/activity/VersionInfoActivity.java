package com.admonitor.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.admonitor.R;
import com.admonitor.tools.ActivityManager;


/**
 * Created by admin on 2016/12/6.
 */
public class VersionInfoActivity extends Activity{
    private LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_versioninfo);
        back = (LinearLayout) findViewById(R.id.back);
        ActivityManager.getInstance().addActivity(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}

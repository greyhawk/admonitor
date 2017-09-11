package com.admonitor.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admonitor.R;
import com.carousel.ad.RollHeaderView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/7/26.
 */
public class DetailActivity extends BaseActivity {
    private RollHeaderView rollHeaderView;
    private LinearLayout la;
    private List imgUrlList=new ArrayList();
    private TextView name,zhu,address,leibie,leixing,size,weifa,falv,date,shenpi,beizhu,user,work,call;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_de);
        name = (TextView) findViewById(R.id.name);
        name.setText(getIntent().getStringExtra("name"));
        zhu = (TextView) findViewById(R.id.zhu);
        zhu.setText(getIntent().getStringExtra("zhu"));
        address = (TextView) findViewById(R.id.address);
        address.setText(getIntent().getStringExtra("address"));
        leibie = (TextView) findViewById(R.id.leibie);
        leibie.setText(getIntent().getStringExtra("leibie"));
        leixing = (TextView) findViewById(R.id.leixing);
        leixing.setText(getIntent().getStringExtra("leixing"));
        size = (TextView) findViewById(R.id.size);
        size.setText(getIntent().getStringExtra("size"));
        weifa = (TextView) findViewById(R.id.weifa);
        weifa.setText(getIntent().getStringExtra("weifa"));
        falv = (TextView) findViewById(R.id.falv);
        falv.setText(getIntent().getStringExtra("falv"));
        date = (TextView) findViewById(R.id.date);
        date.setText(getIntent().getStringExtra("date"));
        shenpi = (TextView) findViewById(R.id.shenpi);
        shenpi.setText(getIntent().getStringExtra("shenpi"));
        beizhu = (TextView) findViewById(R.id.beizhu);
        beizhu.setText(getIntent().getStringExtra("beizhu"));
        user = (TextView) findViewById(R.id.user);
        user.setText(getIntent().getStringExtra("user"));
        work = (TextView) findViewById(R.id.work);
        work.setText(getIntent().getStringExtra("work"));
        call = (TextView) findViewById(R.id.call);
        call.setText(getIntent().getStringExtra("phone"));
        rollHeaderView = new RollHeaderView(this);
        String image = getIntent().getStringExtra("image");
        imgUrlList.clear();
        la = (LinearLayout) findViewById(R.id.rl_contanier);
        String[] src  = image.split(",");
        for(int i=0;i<src.length;i++){
            imgUrlList.add(src[i]);
        }
        rollHeaderView.setOnHeaderViewClickListener(new RollHeaderView.HeaderViewClickListener() {
            @Override
            public void HeaderViewClick(int position) {

            }
        });
        rollHeaderView.setImgUrlData(imgUrlList);
        la.addView(rollHeaderView);

    }
}

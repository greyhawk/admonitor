package com.admonitor.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.admonitor.R;
import com.admonitor.tools.ActivityManager;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;



/**
 * Created by admin on 2016/12/6.
 */
public class DynamicActivity extends Activity {
    private LinearLayout back;
    private ImageView set,imageView;
    private TextView jgtitle,jgdate,jgcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamic);
        back = (LinearLayout) findViewById(R.id.back);
        set = (ImageView) findViewById(R.id.set);
        imageView = (ImageView) findViewById(R.id.pic);
        jgtitle = (TextView) findViewById(R.id.jgtitle);
        jgdate = (TextView) findViewById(R.id.jgdate);
        jgcontent = (TextView) findViewById(R.id.jgcontent);
        ActivityManager.getInstance().addActivity(this);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
//                intent.setClass(DynamicActivity.this,SetActivity.class);
                startActivity(intent);
            }
        });
        String imageUrl = getIntent().getStringExtra("imageUrl");
        String title = getIntent().getStringExtra("title");
        String date = getIntent().getStringExtra("date");
        String content = getIntent().getStringExtra("content");
        CharSequence _content = Html.fromHtml(content);
        jgtitle.setText(title);
        jgdate.setText(date);
        jgcontent.setText(_content);
        configImageLoader();
        ImageLoader.getInstance().displayImage(imageUrl, imageView);
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

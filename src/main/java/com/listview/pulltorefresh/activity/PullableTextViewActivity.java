package com.listview.pulltorefresh.activity;





import android.app.Activity;
import android.os.Bundle;

import com.listview.pulltorefresh.MyListener;
import com.listview.pulltorefresh.PullToRefreshLayout;
import com.admonitor.R;

public class PullableTextViewActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_textview);
		((PullToRefreshLayout) findViewById(R.id.refresh_view))
				.setOnRefreshListener(new MyListener());
	}
}

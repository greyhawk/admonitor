package com.listview.pulltorefresh.pullableview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.support.v7.widget.AppCompatImageView;


public class PullableImageView extends AppCompatImageView implements Pullable
{

	public PullableImageView(Context context)
	{
		super(context);
	}

	public PullableImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public PullableImageView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean canPullDown()
	{
		return true;
	}

	@Override
	public boolean canPullUp()
	{
		return true;
	}

}

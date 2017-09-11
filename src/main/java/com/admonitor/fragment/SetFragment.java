package com.admonitor.fragment;
import android.app.Activity;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.admonitor.R;
import com.admonitor.activity.LoginActivity;
import com.admonitor.activity.UserUpdateActivity;
import com.admonitor.activity.VersionInfoActivity;
import com.admonitor.tools.ActivityManager;

public class SetFragment extends Fragment{
	private View view;
	private RelativeLayout RL,version,loginout;
@Override
public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	view = inflater.inflate(R.layout.fragment_set, null);
	RL = (RelativeLayout) view.findViewById(R.id.userupdate);
	loginout = (RelativeLayout) view.findViewById(R.id.loginout);
	version = (RelativeLayout) view.findViewById(R.id.version);
	RL.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent();
			intent.setClass(getActivity(),UserUpdateActivity.class);
			startActivity(intent);
		}
	});
	version.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			Intent intent = new Intent();
			intent.setClass(getActivity(),VersionInfoActivity.class);
			startActivity(intent);
		}
	});
	loginout.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			new AlertDialog.Builder(getActivity()).setTitle("确认退出吗？")
					.setIcon(android.R.drawable.ic_dialog_info)
					.setPositiveButton("确定", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 点击“确认”后的操作
							SharedPreferences sharedPreferences = getActivity().getSharedPreferences("adctim", Activity.MODE_PRIVATE);
							SharedPreferences.Editor editor = sharedPreferences.edit();
							editor.putString("name", "");
							editor.commit();
							Intent intent = new Intent();
							intent.setClass(getActivity(),LoginActivity.class);
							startActivity(intent);
							ActivityManager.getInstance().exit();
						}
					})
					.setNegativeButton("返回", new DialogInterface.OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							// 点击“返回”后的操作,这里不设置没有任何操作
						}
					}).show();
		}
	});
	return view;
}

}
package com.wuxianedu.wechat.fragment;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.activity.CriOfFriendsActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class FindFragment  extends BaseFragment implements OnClickListener{
	private View view; //发现界面
	
	@Override
	public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_find, container,false);
		return view;
	}
	
	@Override
	public void builderView(View rootView) {
		view.findViewById(R.id.friends_id).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) { //跳转到朋友圈
		startActivity(new Intent(getActivity(),CriOfFriendsActivity.class));
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			hideJianPan();
		}
	}
}

package com.wuxianedu.wechat.fragment;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.activity.SettingActivity;
import com.wuxianedu.wechat.bean.UserInforOfUserLoginAndUserReg;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.FileLocalCache;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MeFragment  extends BaseFragment implements OnClickListener{
	private View view;  	//我的界面

	@Override
	public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_me, container,false); 
		return view;
	}

	@Override
	public void builderView(View rootView) {
		//获取用户信息
		UserInforOfUserLoginAndUserReg userInfor = (UserInforOfUserLoginAndUserReg) 
				FileLocalCache.getSerializableData(getActivity(),Constant.USER_INFOR);	
		setHeadPath(userInfor.getHead());
		setName(userInfor.getUserName());
		setWeiXinId(userInfor.getUserPhoneNum()+"");
		//设置按钮，设置监听
		TextView setting= (TextView) view.findViewById(R.id.setting_id);
		setting.setOnClickListener(this);				
	}
	
	private void setWeiXinId(String weiXinId){
		TextView weixinId = (TextView) view.findViewById(R.id.weixinId_id);
		weixinId.append(weiXinId);	
	}
	
	private void setName(String name){
		TextView nameTv = (TextView) view.findViewById(R.id.name_id);
		nameTv.setText(name);
	}
	/**
	 * 设置圆形头像
	 * @param headPath（头像地址）
	 */
	private void setHeadPath(String headPath){	
		final RoundImageView headImg = (RoundImageView) view.findViewById(R.id.head_id);	 
		SetImage.SetHeadImage(headImg, headPath);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.setting_id: //按下设置按钮
				pressSettingBut();
				break;
			//TODO 相册，收藏，钱包等按钮的点击事件未写方法 	
		}
	}	
	
	private void pressSettingBut(){
		startActivity(new Intent(getActivity(),SettingActivity.class));
	}
	
}

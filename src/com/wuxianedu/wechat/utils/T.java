package com.wuxianedu.wechat.utils;

import android.content.Context;
import android.widget.Toast;

public class T {

	/**
	 * Toast工具方法
	 */
	public   static void showMessage(Context context,Object msg){
		
		Toast.makeText(context,msg.toString(),Toast.LENGTH_LONG).show();
	}
}

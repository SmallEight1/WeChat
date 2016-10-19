package com.wuxianedu.wechat.utils;

import android.util.Log;

public class L {
	
	//是否为debug状态，非debug状态不打印日志
	public static final boolean IS_DEBUG=true; 
	
	//标签
	public static final String TAG="--main--";
	
	public static void i(Object obj){
		if(IS_DEBUG){
			Log.i(TAG,obj.toString());
		}
	}
	public static void w(Object obj){
		if(IS_DEBUG){
			Log.w(TAG,obj.toString());
		}
	}
	public static void e(Object obj){
		if(IS_DEBUG){
			Log.e(TAG,obj.toString());
		}
	}
}

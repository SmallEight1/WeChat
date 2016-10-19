package com.wuxianedu.wechat.utils;

import android.content.Context;
import android.content.Intent;

//跳转活动使用
public class CoreUtil {

	public static void go(Context context, Class<?> class1){
		context.startActivity(new Intent(context,class1));
	}
}

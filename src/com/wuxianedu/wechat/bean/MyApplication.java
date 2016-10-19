/**
 * @Project Name:WeChat
 * @File Name:MyApplication.java
 * @Package Name:com.wuxianedu.wechat
 * @Date:2016年8月24日下午3:41:44
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
*/

package com.wuxianedu.wechat.bean;

import org.xutils.x;

import android.app.Application;

/**
 * 应用程序类  在应用资源中被应用    
 */
public class MyApplication extends Application{

	@Override
	public void onCreate() {
		super.onCreate();
		x.Ext.init(this);   //初始化xutils
		x.Ext.setDebug(true); // 是否输出debug日志
	}
	
}


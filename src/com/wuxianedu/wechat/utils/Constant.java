

package com.wuxianedu.wechat.utils;

public interface Constant {
	//序列化 登录用户信息文件名
	String USER_INFOR = "UserInfor";
	
	//传递图片地址集合的  key
	String IMAGE_LIST = "imageList";
	String POSITION = "POSITION";
	//传递详细信息对象的key
	String FRIEDDATA = "frieddata";
	//传递对象到群聊	
	String GROUPCHAT = "groupchat";
	//传递对象到聊天
	String CHAT_LIST ="chat_list";
	String CHAT_ID ="chat_id";
	String BOOLEAN = "boolean";
	//传递对象到图片浏览
	String IMAGEBROWSE = "imagebrowse";
	//传递对象到微信界面
	String WEIXIN = "weixin";
	//打开照相机的请求码
	int CAMERA  = 100;
	//打开相册的请求码
	int  POHTO = 101;	
	//跳转到聊天界面的请求码
	int WEIXIN_REQUEST = 102;
}


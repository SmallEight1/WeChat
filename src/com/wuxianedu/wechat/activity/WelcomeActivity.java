package com.wuxianedu.wechat.activity;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.CoreUtil;
import com.wuxianedu.wechat.utils.FileLocalCache;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class WelcomeActivity extends Activity {

	@SuppressLint("HandlerLeak")
	private  Handler handler = new Handler(){
		@Override
		public void handleMessage(android.os.Message msg) {
			//登录信息判断  如果已经登录    跳转 主界面    未登录  跳转 登录
			Object object = FileLocalCache.getSerializableData(WelcomeActivity.this, Constant.USER_INFOR);
			if(object != null){
				//跳转到主界面
				CoreUtil.go(WelcomeActivity.this, MainActivity.class);
			}else {
				//跳转到登陆界面
				CoreUtil.go(WelcomeActivity.this, LoginActivity.class);
			}
			//结束本次活动
			finish();
		};
	};
	
	/**
	 * 主线程中
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		new Thread(){
			@Override
			public void run() {
				//消息传递，延迟1秒()
				handler.sendEmptyMessageDelayed(0,1000);
				super.run();
			}
		}.start();
	}
}

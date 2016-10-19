package com.wuxianedu.wechat.activity;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.asnycTask.LoginTask;
import com.wuxianedu.wechat.bean.JsUserLogin;
import com.wuxianedu.wechat.bean.UserInforOfUserLoginAndUserReg;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.FileLocalCache;
import com.wuxianedu.wechat.utils.TextUtil;
import com.wuxianedu.wechat.utils.Validator;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

/**
 * 登陆界面活动
 * @author Administrator
 *
 */
public class LoginActivity extends BaseActivity implements OnClickListener {	
	private EditText phoneEd,passWordEd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//得到       （登陆，注册，有问题）   三个View的对象，并设置监听 
		findViewById(R.id.client_id).setOnClickListener(this);
		findViewById(R.id.problem_id).setOnClickListener(this);
		findViewById(R.id.res_id).setOnClickListener(this);
		phoneEd = (EditText) findViewById(R.id.phone_id);
		passWordEd = (EditText) findViewById(R.id.password_id);
	}

	@Override
	public int getContentView() {	
		return R.layout.activity_login;
	}
	
	/**
	 * 监听事件：登陆，帮助，注册功能
	 */
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.client_id:   //登陆按钮
				Login();
				break;
			case R.id.problem_id: //帮助按钮
				help();
				break;
			case R.id.res_id:   //注册按钮
				jumpActivity(RegActivity.class);
				break;
		}	
	}
	/**
	 * 实现登陆        1，获得用户输入 的   手机号码  和密码     2，进行逻辑判断       3，开启异步任务（解析登陆对象，跳转界面）   
	 */
	private  void Login(){	
		String phoneStr = phoneEd.getText().toString();
		String passWordStr = passWordEd.getText().toString();		
		if (TextUtil.isEmpty(phoneStr)) {
			showMessage("手机号码不能为空");
		}
		if(TextUtil.isEmpty(passWordStr)){
			showMessage("密码不能为空");
			return;
		}	
		if(phoneStr.length() < 11 || !Validator.isMobile(phoneStr) ){
			showMessage("请输入正确的手机号码");
			return;
		}
		if(passWordStr.length() < 6 || !Validator.isPassword(passWordStr)){
			showMessage("请输入正确的密码");
			return;
		}
		//启动 异步任务
		new LoginTask(this){
			public void setView(JsUserLogin result) {				
				//从UserLogin对象中得到   用户信息userInfor
				UserInforOfUserLoginAndUserReg userInfor = result.getUserInfor();
				//序列化对象（将其存入sd卡中）     第二个参数  是自定义文件名
				FileLocalCache.setSerializableData(LoginActivity.this, userInfor,Constant.USER_INFOR);
				//登录成功跳转
				jumpActivity(MainActivity.class);
				//结束当前活动
				finish();
			};
		}.execute();
	}
	
	/**
	 * 实现帮助
	 */
	private void help(){
		//得到uri对象
		Uri uri = Uri.parse("http://weixin.qq.com");
		Intent intent = new Intent();
		intent.setData(uri);
		//设置动作
		intent.setAction(Intent.ACTION_VIEW);
		startActivity(intent);
	}
}

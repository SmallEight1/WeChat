package com.wuxianedu.wechat.activity;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.asnycTask.PassCodeTask;
import com.wuxianedu.wechat.asnycTask.RegTask;
import com.wuxianedu.wechat.bean.JsPassCode;
import com.wuxianedu.wechat.bean.JsUserReg;
import com.wuxianedu.wechat.bean.UserInforOfUserLoginAndUserReg;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.FileLocalCache;
import com.wuxianedu.wechat.utils.TextUtil;
import com.wuxianedu.wechat.utils.Validator;
import android.app.Notification;
import android.app.NotificationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
/**
 * 注册界面
 * @author Administrator
 *
 */
public class RegActivity extends BaseActivity  implements OnClickListener{
	//电话号码，密码，验证码的 EditText对象
	private EditText phoneEd,passWordEd,verifyEd;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置标题名
		setTitleName("注册");
		initialize();		
	}

	@Override
	public int getContentView() {
		return R.layout.activity_reg;
	}

	private void initialize(){
		phoneEd = (EditText) findViewById(R.id.phone_id);
		passWordEd = (EditText) findViewById(R.id.password_id);
		verifyEd = (EditText) findViewById(R.id.vef_id);
		findViewById(R.id.reg_id).setOnClickListener(this);
		findViewById(R.id.vef_bt_id).setOnClickListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.reg_id:   //点击注册按钮
				Register();
				break;
			case R.id.vef_bt_id:   //点击注册按钮
				showMessage("发送验证码");
				sendPassWord();
				break;	
		}
	}
	
	/**
	 * 发送验证码
	 */
	private void sendPassWord(){	
		new PassCodeTask(this){
			public void setView(JsPassCode result) {
				Long passCodeStr = result.getPassCode();
				//发送通知
				NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
				Notification.Builder builder = new Notification.Builder(RegActivity.this);
				builder.setTicker("通知");
				builder.setContentTitle("验证码");
				builder.setContentText(passCodeStr.toString());
				builder.setWhen(System.currentTimeMillis()); //发送时间
				builder.setDefaults(Notification.DEFAULT_ALL);
				builder.setSmallIcon(R.drawable.ic_launcher); //设置图标
				Notification notification =builder.build();
				manager.notify(1,notification);
				verifyEd.setText(passCodeStr.toString());
			};
		}.execute();
	}
	
	/**
	 * 实现注册 1，得到输入信息    2，判断输入信息的基本逻辑   3，启动异步任务
	 */
	
	private  void Register(){
		String phoneStr = phoneEd.getText().toString();
		String passWordStr = passWordEd.getText().toString();
		
		//封装的工具类
		if (TextUtil.isEmpty(phoneStr)) {
			//BaseAcitivity中的方法
			showMessage("手机号码不能为空");
		}
		if(TextUtil.isEmpty(passWordStr)){
			showMessage("密码不能为空");
			return;
		}
		if(phoneStr.length() < 11 || !Validator.isMobile(phoneStr)){
			showMessage("请输入正确的手机号码");
			return;
		}
		if(passWordStr.length() < 6 || !Validator.isPassword(passWordStr)){
			showMessage("请输入正确的密码");
			return;
		}
		//启动 异步任务
		new RegTask(this){
			public void setView(JsUserReg result) {
				//用户信息
				UserInforOfUserLoginAndUserReg userInfor = result.getUserInfor();
				/**
				 * TODO 序列化对象并存入本地， （会覆盖登陆时  的用户信息）==应该使用数据库存储
				 */
				FileLocalCache.setSerializableData(RegActivity.this, userInfor,Constant.USER_INFOR);
				//登录成功跳转到主界面
				jumpActivity(MainActivity.class);
				//关闭当前活动
				finishAll();	
			};
		}.execute();
	}
			
}

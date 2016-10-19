package com.wuxianedu.wechat.asnycTask;


import org.json.JSONException;
import org.json.JSONObject;
import com.wuxianedu.wechat.activity.LoginActivity;
import com.wuxianedu.wechat.bean.JsUserLogin;
import com.wuxianedu.wechat.bean.UserInforOfUserLoginAndUserReg;
import com.wuxianedu.wechat.utils.AssetsUtil;
import android.os.AsyncTask;

public abstract class LoginTask extends AsyncTask<String,Void,JsUserLogin>{
	private LoginActivity loginActivity;
	
	public LoginTask(LoginActivity loginActivity){
		this.loginActivity = loginActivity;
	}
	
	@Override
	protected void onPreExecute() {
		//显示滚动条对话框
		loginActivity.showProgressDialog();
	}
	
	@Override
	protected JsUserLogin doInBackground(String... params) {
		//将json文件 转为字符串（工具类）
		String jsonStr = AssetsUtil.getAssets(loginActivity, "userLogin.json");	
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}		
		return getUserLogin(jsonStr);
	}

	@Override
	protected void onPostExecute(JsUserLogin result) {
		loginActivity.closeProgressDialog();
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			loginActivity.showMessage("错误："+result.getMessage());
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsUserLogin result);
	
	/**
	 * 解析json文件
	 */
	private JsUserLogin getUserLogin(String jsonStr){
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			/**
			 * 三个属性 status（int）， message(String)，userInfor(UserInfor)
			 */
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			JSONObject jsonObject2 = jsonObject.getJSONObject("userInfor");
			/**
			 * 四个属性 userId，userName，head，userPhoneNum
			 */
			String userId = jsonObject2.getString("userId");
			String userName = jsonObject2.getString("userName");
			String head = jsonObject2.getString("head");
			long userPhoneNum = jsonObject2.getLong("userPhoneNum");
			//设置用户信息
			UserInforOfUserLoginAndUserReg userInfor = new UserInforOfUserLoginAndUserReg();
			userInfor.setUserId(userId);
			userInfor.setUserName(userName);
			userInfor.setUserPhoneNum(userPhoneNum);
			userInfor.setHead(head);
			//设置用户登陆时信息（外围信息）
			JsUserLogin userLogin = new JsUserLogin();
			userLogin.setStatus(status);
			userLogin.setMessage(message);
			userLogin.setUserInfor(userInfor);
			
			//返回对象
			return userLogin;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;		
	}
}

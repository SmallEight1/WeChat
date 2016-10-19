package com.wuxianedu.wechat.asnycTask;

import org.json.JSONException;
import org.json.JSONObject;
import com.wuxianedu.wechat.activity.RegActivity;
import com.wuxianedu.wechat.bean.JsUserReg;
import com.wuxianedu.wechat.bean.UserInforOfUserLoginAndUserReg;
import com.wuxianedu.wechat.utils.AssetsUtil;

import android.os.AsyncTask;

public abstract class RegTask extends AsyncTask<String,Void,JsUserReg> {
	private RegActivity regActivity;
	
	public RegTask(RegActivity regActivity) {
		this.regActivity = regActivity;
	}
	
	@Override
	protected void onPreExecute() {
		//开始加载之前 显示加载对话框
		regActivity.showProgressDialog();
	}
	
	@Override
	protected JsUserReg doInBackground(String... params) {
		String jsonStr = AssetsUtil.getAssets(regActivity, "userReg.json");		
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}			
		//解析json  返回UserReg对象
		return getUserReg(jsonStr);
	}
	
	@Override
	protected void onPostExecute(JsUserReg result) {
		regActivity.closeProgressDialog();
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			regActivity.showMessage("错误："+result.getMessage());
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsUserReg result);
	
	/**
	 * 解析json文件
	 */
	private JsUserReg getUserReg(String jsonStr){
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			//三个参数 status，message，userInfor
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			JSONObject jsonObject2 = jsonObject.getJSONObject("userInfor");			
			//四个参数 userId，userName，head，userPhoneNum
			String userId = jsonObject2.getString("userId");
			String userName = jsonObject2.getString("userName");
			String head = jsonObject2.getString("head");
			long userPhoneNum = jsonObject2.getLong("userPhoneNum");		
			//设置userInfor对象信息
			UserInforOfUserLoginAndUserReg userInfor = new UserInforOfUserLoginAndUserReg();
			userInfor.setUserId(userId);
			userInfor.setUserName(userName);
			userInfor.setUserPhoneNum(userPhoneNum);
			userInfor.setHead(head);
			//设置userReg对象信息
			JsUserReg userReg = new JsUserReg();
			userReg.setStatus(status);
			userReg.setMessage(message);
			userReg.setUserInfor(userInfor);
			//返回userReg对象
			return userReg;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
}

package com.wuxianedu.wechat.asnycTask;

import org.json.JSONException;
import org.json.JSONObject;
import com.wuxianedu.wechat.activity.RegActivity;
import com.wuxianedu.wechat.bean.JsPassCode;
import com.wuxianedu.wechat.utils.AssetsUtil;
import android.os.AsyncTask;

public abstract class PassCodeTask extends AsyncTask<Void,Void,JsPassCode>{
	private RegActivity regActivity;
	
	public PassCodeTask(RegActivity regActivity){
		this.regActivity = regActivity;
	}
	
	@Override
	protected void onPreExecute() {
		//开始加载之前 显示加载对话框
		regActivity.showProgressDialog();
	}
	
	@Override
	protected JsPassCode doInBackground(Void... params) {
		String jsonStr = AssetsUtil.getAssets(regActivity,"passCode.json");	
		//添加睡眠时间，模拟网络请求
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}				
		return getPassCode(jsonStr);
	}
	
	@Override
	protected void onPostExecute(JsPassCode result) {
		regActivity.closeProgressDialog();
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			regActivity.showMessage("错误："+result.getMessage());
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsPassCode result);
	
	private JsPassCode getPassCode(String jsonStr){
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			long passCode = jsonObject.getLong("passCode");
			JsPassCode js_PassCode = new JsPassCode();
			js_PassCode.setStatus(status);
			js_PassCode.setMessage(message);
			js_PassCode.setPassCode(passCode);
			return js_PassCode;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}

package com.wuxianedu.wechat.asnycTask;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.wuxianedu.wechat.activity.SubscribDetailsActivity;
import com.wuxianedu.wechat.bean.JsSubscribDetails;
import com.wuxianedu.wechat.bean.SubSribDetai;
import com.wuxianedu.wechat.utils.AssetsUtil;

import android.os.AsyncTask;

public abstract class SubscribDetailsTask extends AsyncTask<Void,Void,JsSubscribDetails>{
	private SubscribDetailsActivity subscribDetailsActivity;
	
	public SubscribDetailsTask(SubscribDetailsActivity subscribDetailsActivity) {
		this.subscribDetailsActivity = subscribDetailsActivity;
	}
	
	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		subscribDetailsActivity.showProgressDialog();
	}
	
	@Override
	protected JsSubscribDetails doInBackground(Void... params) {
		String jsonStr = AssetsUtil.getAssets(subscribDetailsActivity,
				"subscribeDetails.json");
		try {
			Thread.sleep(1000L);
			//获取json字符串		 
		} catch (InterruptedException e) {			
			e.printStackTrace();
		}
		//解析json 返回	
		return getSubscribDetails(jsonStr);
	}
	
	@Override
	protected void onPostExecute(JsSubscribDetails result) {
		subscribDetailsActivity.closeProgressDialog();
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			subscribDetailsActivity.showMessage("错误："+result.getMessage());
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsSubscribDetails result);
	/**
	 * 解析json文件
	 */
	private JsSubscribDetails getSubscribDetails(String jsonStr){
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);	
			//三个参数
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			
			JSONArray jsonArray = jsonObject.getJSONArray("subscribeDetails");	
			List<SubSribDetai>  listsubSribDetails = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {
				SubSribDetai subscribDetails = new SubSribDetai();
				//五个参数
				String title = jsonArray.getJSONObject(i).getString("title");	
				String image = jsonArray.getJSONObject(i).getString("image");
				String content = jsonArray.getJSONObject(i).getString("content");		
				String time = jsonArray.getJSONObject(i).getString("time");		
				subscribDetails.setTitle(title);
				subscribDetails.setImage(image);
				subscribDetails.setContent(content);
				subscribDetails.setTime(time);		
				listsubSribDetails.add(subscribDetails);
			}
				
			JsSubscribDetails subscribDetail = new JsSubscribDetails();
			subscribDetail.setStatus(status);
			subscribDetail.setMessage(message);
			subscribDetail.setListsubSribDetails(listsubSribDetails);
			return subscribDetail;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;		
	}
}

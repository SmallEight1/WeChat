package com.wuxianedu.wechat.asnycTask;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.wuxianedu.wechat.activity.CriOfFriendsActivity;
import com.wuxianedu.wechat.bean.JsUserFriendsCircle;
import com.wuxianedu.wechat.bean.Friends;
import com.wuxianedu.wechat.utils.AssetsUtil;
import android.os.AsyncTask;

public abstract  class UserFriendsCircleTack extends AsyncTask<Void,Void,JsUserFriendsCircle>{
	private CriOfFriendsActivity criOfFriendsActivity;
	
	/**
	 * 异步解析数据,在解析完之后执行一个抽象方法，该方法由调用者实现 
	 * @param baseActivity
	 */
	public UserFriendsCircleTack(CriOfFriendsActivity baseActivity){
		this.criOfFriendsActivity = baseActivity;
	}
	
	@Override
	protected void onPreExecute() {	
		super.onPreExecute();
		criOfFriendsActivity.showProgressDialog();
	}
	
	@Override
	protected JsUserFriendsCircle doInBackground(Void... params) {
		String jsonStr = AssetsUtil.getAssets(criOfFriendsActivity,"userFriendsCircle.json");
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		return getUserFriendsCircle(jsonStr);
	}

	@Override
	protected void onPostExecute(JsUserFriendsCircle result) {
		criOfFriendsActivity.closeProgressDialog();
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			criOfFriendsActivity.showMessage("错误："+result.getMessage());
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsUserFriendsCircle result);
	
	/**
	 * 解析json文件
	 */
	private JsUserFriendsCircle getUserFriendsCircle(String jsonStr){
		try {
			/**
			 * 得到对象（四个属性） status（int）  		message（String）
			 * 			  backGround（String）          listFriends(List<Friends>)
			 */
			JSONObject jsonObject = new JSONObject(jsonStr);
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			String backGround = jsonObject.getString("backGround");	
			JSONArray jsonArray = jsonObject.getJSONArray("friends");	
			List<Friends>  listFriends = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {		
				Friends friends = new Friends();
				/**
				 * 得到对象（五个属性） userName（String）   head（String）
				 * 			    content(String)   time(String)  listImages(List<String>)
				 */
				String userName = jsonArray.getJSONObject(i).getString("userName");
				String head = jsonArray.getJSONObject(i).getString("head");
				String content = jsonArray.getJSONObject(i).getString("content");
				String time = jsonArray.getJSONObject(i).getString("time");
				List<String> listImages = new ArrayList<>();
				JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("images");
				//图片数组  接收
				for (int j = 0; j < jsonArray1.length(); j++) {
					listImages.add(jsonArray1.getString(j));
				}
				//设置 friends对象的属性
				friends.setUserName(userName);
				friends.setHead(head);
				friends.setContent(content);
				friends.setTime(time);
				friends.setListImages(listImages);
				//将friends对象添加到listFriends集合中
				listFriends.add(friends);
			}			
			//设置userFriendsCircle对象的属性
			JsUserFriendsCircle userFriendsCircle = new JsUserFriendsCircle();
			userFriendsCircle.setStatus(status);
			userFriendsCircle.setMessage(message);
			userFriendsCircle.setBackGround(backGround);
			userFriendsCircle.setListFriends(listFriends);
		//返回解析对象
			return userFriendsCircle;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
}

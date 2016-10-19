package com.wuxianedu.wechat.asnycTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.wuxianedu.wechat.activity.NewsFriendActivity;
import com.wuxianedu.wechat.bean.ContactsOfAddFriends;
import com.wuxianedu.wechat.bean.JsaddFriends;
import com.wuxianedu.wechat.utils.AssetsUtil;
import com.wuxianedu.wechat.utils.PingYinUtil;
import com.wuxianedu.wechat.utils.TextUtil;

import android.os.AsyncTask;

public abstract class AddFriendsTask extends AsyncTask<Void,Void,JsaddFriends>{
	private NewsFriendActivity newsFriendActivity;
	
	public AddFriendsTask(NewsFriendActivity newsFriendActivity){
		this.newsFriendActivity = newsFriendActivity;
	}
	
	@Override
	protected void onPreExecute() {
		newsFriendActivity.showProgressDialog(); 			//显示加载对话框，模拟网络请求
		super.onPreExecute();
	}
	
	@Override
	protected JsaddFriends doInBackground(Void... params) {
		//获取json字符串
		String jsonStr = AssetsUtil.getAssets(newsFriendActivity,"addFriends.json");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return getWechat(jsonStr);
	}
	
	@Override
	protected void onPostExecute(JsaddFriends result) {
		newsFriendActivity.closeProgressDialog();
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			newsFriendActivity.showMessage("错误："+result.getMessage());
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsaddFriends result);
	
	/**
	 * 解析json文件
	 */
	private JsaddFriends getWechat(String jsonStr){
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			JSONArray jsonArray = jsonObject.getJSONArray("contacts");	
			List<ContactsOfAddFriends>  listContacts = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {
				ContactsOfAddFriends contacts = new ContactsOfAddFriends();
				String name = jsonArray.getJSONObject(i).getString("name");
				String allpingyin = PingYinUtil.getPingYin(name);
				//得到拼音的首字母,大写
				String firstName = allpingyin.substring(0,1).toUpperCase(Locale.getDefault());
				//判断  名字   是否为英文  如果是 则设置 首字母为#  如果不是 则设置首字母
				if (!TextUtil.isEnglish(allpingyin)) {
					contacts.setNameFirst("#"); //如果不是拼音,直接设置#
				}else{
					contacts.setNameFirst(firstName); //设置名字拼音首字母
				}
				String area = jsonArray.getJSONObject(i).getString("area");
				String head = jsonArray.getJSONObject(i).getString("head");
				String autograph = jsonArray.getJSONObject(i).getString("autograph");
				String lastStr = jsonArray.getJSONObject(i).getString("lastStr");
				String lastTime = jsonArray.getJSONObject(i).getString("lastTime");		
				long weCode = jsonArray.getJSONObject(i).getLong("weCode");
				int newsNum = jsonArray.getJSONObject(i).getInt("newsNum");
				boolean isAdd = jsonArray.getJSONObject(i).getBoolean("isAdd");			
				List<String> listImages = new ArrayList<>();
				JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("images");
				for (int j = 0; j < jsonArray1.length(); j++) {
					listImages.add(jsonArray1.getString(j));
				}				
				contacts.setName(name);
				contacts.setArea(area);
				contacts.setHead(head);
				contacts.setAutograph(autograph);
				contacts.setLastStr(lastStr);
				contacts.setLastTime(lastTime);
				contacts.setWeCode(weCode);
				contacts.setNewsNum(newsNum);
				contacts.setAdd(isAdd);
				contacts.setListImages(listImages);
				contacts.setFristNameFirst(PingYinUtil.converterToFirstSpell(name));
				listContacts.add(contacts);
			}				
			JsaddFriends addFriends = new JsaddFriends();
			addFriends.setStatus(status);
			addFriends.setMessage(message);
			addFriends.setListContacts(listContacts);
			return addFriends;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
}

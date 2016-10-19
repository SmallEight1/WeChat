package com.wuxianedu.wechat.asnycTask;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.wuxianedu.wechat.bean.ContactsOfWeChat;
import com.wuxianedu.wechat.bean.JsWechat;
import com.wuxianedu.wechat.fragment.WeiXinFragment;
import com.wuxianedu.wechat.utils.AssetsUtil;
import android.os.AsyncTask;


public abstract class WechatTask extends AsyncTask<Void,Void,JsWechat> {
	private WeiXinFragment weiXinFragment;
	
	public WechatTask(WeiXinFragment weiXinFragment){
		this.weiXinFragment = weiXinFragment;
	}
	
	@Override
	protected void onPreExecute() {	
		super.onPreExecute();
		weiXinFragment.showProgressDialog();
	}
	
	@Override
	protected JsWechat doInBackground(Void...pragms) {		
		try {			
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//获取json字符串
		String jsonStr = AssetsUtil.getAssets(weiXinFragment.getActivity(),"wechat.json");
		//解析json  返回	
		return getWechat(jsonStr);
	}
	
	@Override
	protected void onPostExecute(JsWechat result) {
		weiXinFragment.closeProgressDialog();
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			weiXinFragment.showMessage("错误："+result.getMessage());
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsWechat result);
	/**
	 * 解析json文件
	 */
	private JsWechat getWechat(String jsonStr){
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);	
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			JSONArray jsonArray = jsonObject.getJSONArray("contacts");	
			List<ContactsOfWeChat>  listContacts = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {
				ContactsOfWeChat contacts = new ContactsOfWeChat();
				String name = jsonArray.getJSONObject(i).getString("name");
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
				listContacts.add(contacts);
			}
				
			JsWechat wechat = new JsWechat();
			wechat.setStatus(status);
			wechat.setMessage(message);
			wechat.setListContacts(listContacts);
			return wechat;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
}

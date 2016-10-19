package com.wuxianedu.wechat.asnycTask;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wuxianedu.wechat.activity.GounpChatActivity;
import com.wuxianedu.wechat.bean.ContactsOfContact;
import com.wuxianedu.wechat.bean.JsContact;
import com.wuxianedu.wechat.fragment.ContactsFragment;
import com.wuxianedu.wechat.utils.AssetsUtil;
import com.wuxianedu.wechat.utils.PingYinUtil;
import com.wuxianedu.wechat.utils.TextUtil;

import android.os.AsyncTask;

public abstract class ContactTask extends AsyncTask<Void, Void, JsContact>{
	private ContactsFragment contactsFragment;
	private GounpChatActivity gounpChatActivity;	
	private List<ContactsOfContact> listContacts;   //数据源list集合 
	
	//构造器重载，在通讯录界面解析数据，在群聊界面解析数据
	public ContactTask(ContactsFragment contactsFragment) {
		this.contactsFragment = contactsFragment;
	}
	
	public  ContactTask(GounpChatActivity gounpChatActivity) {
		this.gounpChatActivity = gounpChatActivity;
	}
	
	@Override
	protected void onPreExecute() {	
		super.onPreExecute();
		if (contactsFragment !=null) {
			contactsFragment.showProgressDialog();
		}
		if (gounpChatActivity !=null) {
			gounpChatActivity.showProgressDialog();
		}		
	}
	
	@Override
	protected JsContact doInBackground(Void... params) {
		try {
			Thread.sleep(1000L);	
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		String jsonString = null;
		if (contactsFragment !=null) {
			jsonString = AssetsUtil.getAssets(contactsFragment.getActivity(),"contacts.json");
		}
		if (gounpChatActivity !=null) {
			jsonString = AssetsUtil.getAssets(gounpChatActivity,"contacts.json");
		}						
		return getContact(jsonString);
	}

	@Override
	protected void onPostExecute(JsContact result) {
		if (contactsFragment != null) {
			contactsFragment.closeProgressDialog();
		}
		if (gounpChatActivity != null) {
			gounpChatActivity.closeProgressDialog();
		}	
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			if (contactsFragment != null) {
				contactsFragment.showMessage("错误："+result.getMessage());
			}
			if (gounpChatActivity != null) {
				gounpChatActivity.closeProgressDialog();
			}
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsContact result);
	
	/**
	 * parse:解析数据
	 */
	private JsContact getContact(String string){
		try {
			JSONObject jsonObject = new JSONObject(string);
			//三个参数  status message list
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			listContacts = new ArrayList<ContactsOfContact>();
			JSONArray jsonArray = jsonObject.getJSONArray("contacts");
			for (int i = 0; i < jsonArray.length(); i++) {
				ContactsOfContact contacts = new ContactsOfContact();
				JSONObject jsonObject2= jsonArray.getJSONObject(i);
				String name = setPrams(jsonObject2,contacts,jsonArray,i);
				//将 name 转换为 拼音（英文字母）              如果不是中文（特殊字符）则不转换
				String allpingyin = PingYinUtil.getPingYin(name);
				//得到拼音的首字母,大写
				String firstName = allpingyin.substring(0,1).toUpperCase(Locale.getDefault());
				//判断  名字   是否为英文  如果是 则设置 首字母为#  如果不是 则设置首字母
				if (!TextUtil.isEnglish(allpingyin)) {
					contacts.setNameFirst("#"); //如果不是拼音,直接设置#
				}else{
					contacts.setNameFirst(firstName); //设置名字拼音首字母
				}
				listContacts.add(contacts);
				/**
				 *  采用Comparator接口对list集合排序
				 */
				Collections.sort(listContacts,new Comparator<ContactsOfContact>() {
					@Override
					public int compare(ContactsOfContact lhs, ContactsOfContact rhs) {
						String beforName = lhs.getNameFirst();
						String afterName = rhs.getNameFirst();
						return beforName.compareTo(afterName);
					}
				});		
			}
			
			JsContact contact = new JsContact();
			contact.setStatus(status);
			contact.setMessage(message);
			contact.setListContacts(listContacts);
			return contact;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}	
	/**
	 * 设置contacts对象中的属性值
	 * @param jsonObject2        数组中的对象
	 * @param contacts		
	 * @param jsonArray	
	 * @param i		 	
	 * @return name  		contacts中的姓名
	 */
	private String setPrams(JSONObject jsonObject2,ContactsOfContact contacts,
							JSONArray jsonArray,int i){
		//十个参数
		try{
			String name = jsonObject2.getString("name");
			String head = jsonObject2.getString("head");
			String area = jsonObject2.getString("area");
			String autograph = jsonObject2.getString("autograph");
			String lastStr = jsonObject2.getString("lastStr");
			String lastTime = jsonObject2.getString("lastTime");
			long  weCode = jsonObject2.getLong("weCode");
			int  newsNum = jsonObject2.getInt("newsNum");
			boolean isAdd = jsonObject2.getBoolean("isAdd");
			List<String> listImages = new ArrayList<>();
			JSONArray jsonArray1 = jsonArray.getJSONObject(i).getJSONArray("images");
			for (int j = 0; j < jsonArray1.length(); j++) {
				listImages.add(jsonArray1.getString(j));
			}
			contacts.setName(name);
			contacts.setHead(head); 
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
			return name;
		}catch(Exception e){
			e.printStackTrace();
		}	
		return null;
	}
}

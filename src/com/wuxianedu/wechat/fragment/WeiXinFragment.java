package com.wuxianedu.wechat.fragment;


import java.util.List;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.activity.ChatActivity;
import com.wuxianedu.wechat.activity.SubscribeActivity;
import com.wuxianedu.wechat.adapter.WeixinListViewApater;
import com.wuxianedu.wechat.asnycTask.WechatTask;
import com.wuxianedu.wechat.bean.ContactsOfWeChat;
import com.wuxianedu.wechat.bean.JsWechat;
import com.wuxianedu.wechat.datebase.WechatDB;
import com.wuxianedu.wechat.utils.Constant;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class WeiXinFragment  extends BaseFragment {
	private View view;
	private boolean isFirst = true; //第一次加载
	private WeixinListViewApater apater;
	private ListView listView;
	private WechatDB wechatDB;
	private List<ContactsOfWeChat> listContacts;
	
	@Override
	public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_weixin, container,false);
		return view;
	}

	@Override
	public void builderView(View rootView) {
		listView = (ListView) view.findViewById(R.id.lv_id);
		wechatDB = WechatDB.getInstance(getActivity());
		if (wechatDB.loadContactsOfWeChat().size()>0) {//如果数据库内有值
			listContacts = wechatDB.loadContactsOfWeChat();
			apater = new WeixinListViewApater(getActivity(), listContacts);
			listView.setAdapter(apater);
			listView.setOnItemClickListener(new ListonTtemClick());
		}else{
			//加载数据,异步操作
			new WechatTask(this){
				@Override
				public void setView(JsWechat result) {
					//我的界面  listView设置适配器
					listContacts = result.getListContacts();
					for(ContactsOfWeChat contactsOfWeChat:listContacts){
						wechatDB.savaContactsOfWeChat(contactsOfWeChat);
					}			
					apater = new WeixinListViewApater(getActivity(), listContacts);
					listView.setAdapter(apater);
					listView.setOnItemClickListener(new ListonTtemClick());
				};
			}.execute();	
		}
	
	}
	
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		super.setUserVisibleHint(isVisibleToUser);	
		if (isVisibleToUser && isFirst) {
			//new WechatTask().execute();  TODO 执行此处操作，程序变得异常卡顿，?	 
			isFirst = false;
		}else if (isVisibleToUser) {
			hideJianPan();
		}
	}

	class ListonTtemClick implements OnItemClickListener{

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			if (position == 0) {  //如果是点击订阅号的话
				//当点击带有  红色小球的 子条目时  让NewNum为零，并刷新
				listContacts.get(position).setNewsNum(0);
				startActivity(new Intent(getActivity(),SubscribeActivity.class));
			}else{
				Intent intent = new Intent(getActivity(),ChatActivity.class);
				intent.putExtra(Constant.CHAT_LIST, listContacts.get(position));
				intent.putExtra(Constant.CHAT_ID, position+1);
				startActivityForResult(intent, Constant.WEIXIN_REQUEST);
				if (listContacts.get(position).getNewsNum()!=0) {//如果被点击的子条目对象消息数不为零
					ContentValues values = new ContentValues();
					values.put("newsNum", 0);
					wechatDB.updateContactsOfWeChat(values, 
							"_id = ?", new String[]{String.valueOf(position+1)});
				}		
			}
		}		
	}	
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		listContacts = wechatDB.loadContactsOfWeChat();
		apater.setList(listContacts);
	}
}

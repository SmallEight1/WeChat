package com.wuxianedu.wechat.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.adapter.NewsFriendsAdapter;
import com.wuxianedu.wechat.asnycTask.AddFriendsTask;
import com.wuxianedu.wechat.bean.ContactsOfAddFriends;
import com.wuxianedu.wechat.bean.JsaddFriends;
import com.wuxianedu.wechat.utils.T;
import com.wuxianedu.wechat.utils.TextUtil;
import com.wuxianedu.wechat.widget.SearchEditText;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class NewsFriendActivity extends BaseActivity implements OnClickListener{
	private TextView newFriedsTv,photoTv,qqTv;
	private View view;
	private SearchEditText searchEditText;
	private List<ContactsOfAddFriends> list = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		setTitleName("新的朋友");
		initialize();
	}

	@Override
	public int getContentView() {	
		return R.layout.activity_news_friend;
	}

	@SuppressLint("InflateParams")
	private void initialize(){
		view = LayoutInflater.from(this).inflate(R.layout.item_head_newfriends, null);
		newFriedsTv = (TextView)findViewById(R.id.news_friends);
		photoTv = (TextView)view.findViewById(R.id.new_phone);
		qqTv = (TextView)view.findViewById(R.id.new_qq);
		searchEditText = (SearchEditText)view.findViewById(R.id.con_sea_img_id);
		setView();
	}
	
	private void setView(){
		newFriedsTv.setOnClickListener(this);	
		photoTv.setOnClickListener(this);
		qqTv.setOnClickListener(this);
		 //异步操作，解析文件	
		new AddFriendsTask(this){
			public void setView(JsaddFriends result) {
				ListView listView = (ListView)findViewById(R.id.newsFriends_listView);
				list = result.getListContacts();
				NewsFriendsAdapter apater = new NewsFriendsAdapter(NewsFriendActivity.this,
						list);
				listView.setAdapter(apater);
				searchEditText.addTextChangedListener(new SearchTextWatch(apater));
				//设置头视图
				listView.addHeaderView(view, null, false);
			};
		}.execute();
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.news_friends:
			T.showMessage(getApplicationContext(), "添加朋友");
			break;
		case R.id.new_phone:
			T.showMessage(getApplicationContext(), "添加手机联系人");
			break;
		case R.id.new_qq:
			T.showMessage(getApplicationContext(), "添加QQ好友");
			break;
		}
	}	
	
	class SearchTextWatch implements TextWatcher{
		private List<ContactsOfAddFriends> subList = new ArrayList<>(); //以搜索内容开头集合
		private List<ContactsOfAddFriends> subSonList = new ArrayList<>(); //包含搜索内容集合
		private NewsFriendsAdapter adapter;
		
		public SearchTextWatch(NewsFriendsAdapter adapter) {
			this.adapter = adapter;
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {	
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {		
		}

		@Override
		public void afterTextChanged(Editable s) {
			String input = s.toString(); //获取用户输入的内容
			subList.clear();  //清空集合
			subSonList.clear();
			if (TextUtil.isEmpty(input)) { //输入框为空
				adapter.setList(list); 	
				return;
			}
			boolean isChinese = TextUtil.isChinese(input); //判断是否为中文
			for (int i = 0; i < list.size(); i++) {
				ContactsOfAddFriends contacts = list.get(i);
				if (isChinese) {  //是中文
					String name = contacts.getName();
					if (name.startsWith(input)) {
						subList.add(contacts);
					}else if(name.contains(input)){
						subSonList.add(contacts);
					}
				}else{  //是英文
					input = input.toUpperCase(Locale.getDefault());
					if (contacts.getNameFirst().equals(input)) {
						subList.add(contacts);
					}else if (contacts.getFristNameFirst().contains(input)) {
						subSonList.add(contacts);
					}
				}
			}
			subList.addAll(subSonList);  //把包含的添加到以输入内容开头的结合中
			adapter.setList(subList);
		}	
	}
}

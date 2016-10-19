package com.wuxianedu.wechat.fragment;

/**
 * 联系人界面
 */
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.activity.FriedDetailsActivity;
import com.wuxianedu.wechat.activity.GounpChatActivity;
import com.wuxianedu.wechat.activity.NewsFriendActivity;
import com.wuxianedu.wechat.adapter.ContactAdapter;
import com.wuxianedu.wechat.adapter.ContactAdapter.ViewHodler;
import com.wuxianedu.wechat.asnycTask.ContactTask;
import com.wuxianedu.wechat.bean.ContactsOfContact;
import com.wuxianedu.wechat.bean.JsContact;
import com.wuxianedu.wechat.listener.ContactListener;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.TextUtil;
import com.wuxianedu.wechat.widget.SearchEditText;
import com.wuxianedu.wechat.widget.SideBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class ContactsFragment  extends BaseFragment {
	private List<ContactsOfContact> listContacts;   //数据源list集合  
	private List<ContactsOfContact> uplist;		
	private ListView listView;				//联系人的listView
	private boolean isFirst = true; 		//第一次加载
    private  View headView; 				//头视图
	private View view; 						//加载好的视图
	private JsContact js_Contact;			//数据源对象
	private SearchEditText searchEditText;  //自定义输入框
	private SideBar sideBar;				//自定义View  检索
	
	@Override
	public View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_contacts, container,false);
		return view;
	}

	@Override
	public void builderView(View rootView) {
		//得到listView对象，添加头视图
		listView = (ListView) view.findViewById(R.id.contact_lv);
		headView = LayoutInflater.from(getActivity()).
				inflate(R.layout.item_head_conthead,listView,false);		
		listView.addHeaderView(headView,null,false);
		listView.setHeaderDividersEnabled(false);
		//得到sideBar对象，该对象必须绑定 textView（该textView用于显示提示信息） 和  listView
		sideBar = (SideBar) view.findViewById(R.id.sideBar_id);
		TextView textView = (TextView) view.findViewById(R.id.char_id);
		sideBar.setTextView(textView);
		sideBar.setListView(listView);
		//得到searchEditText对象
		searchEditText = (SearchEditText) headView.findViewById(R.id.con_sea_img_id);	
	}
	/**
	 * 解决(viewPager)页面预加载问题（用于解析数据操作，节约流量）：1，该方法在onCreateView（）方法之前执行具有较高的优先级。
	 * 				 2，该方法在界面可见时执行，Fragment的其他方法会有预加载（即页面两边的其他方法执行到onStop）
	 * 				 3，由于它是先于onCreateView()执行的，当其中进行view.findviewByid 操作时，要考虑view对象是否存在
	 * 				 4，由于这个是viewPager的默认选中对象旁边的对象，预加载时会执行onCreateView()，不会执行setUserVisibleHint（）；
	 */
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {

		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser && isFirst) {
			new ContactTask(this){
				@Override
				public void setView(JsContact result) {
					inti(result);
				};
			}.execute();
			isFirst = false;
		}
	}
	
	private void inti(JsContact result){
		js_Contact = result;
		//new 适配器   第二个参数是解析到 的Contact对象
		listContacts = result.getListContacts();
		//得到适配器对象
		ContactAdapter adapter = new ContactAdapter(getActivity(),listContacts);
		//设置适配器
		listView.setAdapter(adapter);
		uplist = listContacts;
		adapter.setOnItemListener(new ContactListener() {
			
			@Override
			public void onItemClick(ViewHodler viewHolder, int position) {
				Intent intent = new  Intent(getActivity(),FriedDetailsActivity.class);
				intent.putExtra(Constant.FRIEDDATA, uplist.get(position));
				startActivity(intent);
			}
		});
		//设置监听
		searchEditText.addTextChangedListener(new SearchTextWatch(adapter));
		LinearLayout newsFriend =(LinearLayout) view.findViewById(R.id.newsFriend_ly_id);
		LinearLayout groudChat =(LinearLayout) view.findViewById(R.id.groudChat_ly_id);
		LinearLayout official =(LinearLayout) view.findViewById(R.id.official_ly_id);
		newsFriend.setOnClickListener(new LinearLayoutOnClickListener());
		groudChat.setOnClickListener(new LinearLayoutOnClickListener());
		official.setOnClickListener(new LinearLayoutOnClickListener());
	}
		
	class LinearLayoutOnClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
				case R.id.newsFriend_ly_id://新的朋友
					startActivity(new Intent(getActivity(),NewsFriendActivity.class));
					break;
				case R.id.groudChat_ly_id://群聊
					Intent intent = new Intent(getActivity(),GounpChatActivity.class);
					intent.putExtra(Constant.GROUPCHAT, js_Contact);
					startActivity(intent);
					break;
				case R.id.official_ly_id://公众号	
					break;
			}				
		}	
	}
	/**
	 * @ClassName: SearchTextWatch 文本改变
	 */
	class SearchTextWatch implements TextWatcher{
		private List<ContactsOfContact> subList = new ArrayList<ContactsOfContact>(); //以搜索内容开头集合
		private List<ContactsOfContact> subSonList = new ArrayList<ContactsOfContact>(); //包含搜索内容集合
		private ContactAdapter adapter;  //传递ListView的Adapter,用于改变视图
		private View headBody;  //要隐藏的视图，即头视图对象
		//构造器
		public SearchTextWatch(ContactAdapter adapter) {
			this.adapter = adapter;
			//得到头视图对象
			headBody = headView.findViewById(R.id.ly_head_id); 
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}
		
		@Override
		public void afterTextChanged(Editable s) {
			String input = s.toString(); 	//获取用户输入的内容
			subList.clear();  				//清空集合
			subSonList.clear();
			if (TextUtil.isEmpty(input)) { //输入框为空
				headBody.setVisibility(View.VISIBLE);
				sideBar.setVisibility(View.VISIBLE);
				adapter.setList(listContacts); 
				uplist = listContacts;
				return;
			}	
			sideBar.setVisibility(View.GONE);
			headBody.setVisibility(View.GONE); //不为空，隐藏头视图
			boolean isChinese = TextUtil.isChinese(input); //判断是输入框内容否为中文
			for (int i = 0; i < listContacts.size(); i++) {
				ContactsOfContact contacts = listContacts.get(i);				
				if (isChinese) {  //是中文
					String name = contacts.getName();
					if (name.startsWith(input)) { 	//输入内容是名字的开头
						subList.add(contacts);
					}else if(name.contains(input)){	//名字中包含输入的内容
						subSonList.add(contacts);
					}
				}else{  //是英文
					input = input.toUpperCase(Locale.getDefault());   //转换成大写
					if (contacts.getNameFirst().equals(input)) {
						subList.add(contacts);
					}else if (contacts.getFristNameFirst().contains(input)) {
						subSonList.add(contacts);
					}
				}
			}
			subList.addAll(subSonList);  //把包含的添加到以输入内容开头的结合中
			adapter.setList(subList);
			uplist = subList;
		}
	}
}

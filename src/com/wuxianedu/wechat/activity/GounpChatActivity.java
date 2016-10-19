package com.wuxianedu.wechat.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.adapter.GroupChatAdapter;
import com.wuxianedu.wechat.adapter.GroupChatHlvAdapter;
import com.wuxianedu.wechat.asnycTask.ContactTask;
import com.wuxianedu.wechat.bean.ContactsOfContact;
import com.wuxianedu.wechat.bean.JsContact;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.DensityUtil;
import com.wuxianedu.wechat.utils.TextUtil;
import com.wuxianedu.wechat.widget.ClearEditText;
import com.wuxianedu.wechat.widget.HorizontalListView;
import com.wuxianedu.wechat.widget.SideBar;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class GounpChatActivity extends BaseActivity {
	private ListView listView;  //listView
	private HorizontalListView hListView;  //水平listView
	private TextView rightTv;  //右边的TextView
	private TextView charTv;  //
	private List<ContactsOfContact> list;  //ListView的数据源
	private CheckBox checkBox;  //是否添加到群聊
	private SideBar sideBar;
	private List<ContactsOfContact> groupList = new ArrayList<ContactsOfContact>(); //水平ListView的数据源
	private GroupChatHlvAdapter hAdapter; //水平lIstView的Adapter
	private GroupChatAdapter adapter;
	private ClearEditText clearEditText;
	private float ALPHAFIRST = 0.5F; //点击一个时图片的透明度
	private float ALPHATWO = 1.0F; //默认的图片的透明度
	private boolean falg = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleName("群聊");
		listView = (ListView)findViewById(R.id.gounpChat_listView);
		//获取ListView的数据源
		Intent intent = getIntent();
		//得到Contact对象
		JsContact Contact  = new JsContact();
		Contact = (JsContact) intent.getSerializableExtra(Constant.GROUPCHAT);  //通讯录界面传过来的值
		if (Contact == null) {//如果没有从通讯录界面跳转过来，则自己解析数据
			new ContactTask(this) {
				@Override
				public void setView(JsContact result) {
					list =result.getListContacts();
					initialize();
				}
			}.execute();
		}else{
			list =Contact.getListContacts();
			initialize();
		}
	}

	@Override
	public int getContentView() {
		return R.layout.activity_gounp_chat;
	}
	
	private void initialize(){
		rightTv = (TextView) findViewById(R.id.queding);
		hListView = (HorizontalListView) findViewById(R.id.group_chat_hlv);
		listView = (ListView) findViewById(R.id.gounpChat_listView);
		clearEditText =(ClearEditText) findViewById(R.id.group_chat_search);
		sideBar = (SideBar) findViewById(R.id.sideBar_id);
		charTv = (TextView) findViewById(R.id.char_id);
		setView();
	}
	
	
	private void setView(){
		//得到sideBar
		sideBar.setTextView(charTv);
		sideBar.setListView(listView);
		if (list != null) { //不为空是直接设置
			adapter = new GroupChatAdapter(this, list);
			listView.setAdapter(adapter);
			View view  = LayoutInflater.from(this).inflate(R.layout.item_head_gounp_chat,listView,false);
			listView.addHeaderView(view, null, false);
			listView.setHeaderDividersEnabled(false);
		}else { //为空时要解析数据
			
		}
		//搜索设置监听器
		clearEditText.addTextChangedListener(new SearchTextWatch(adapter));
		hAdapter = new GroupChatHlvAdapter(this,groupList);
		hListView.setAdapter(hAdapter);
		setListener();
	}
	
	/**
	 * 设置监听事件
	 */
	private void setListener(){
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				//获取CheckBox的对象
				checkBox = (CheckBox) view.findViewById(R.id.ck_id);
				//接收CheckBox的选中状态
				boolean isChose = !checkBox.isChecked();
				checkBox.setChecked(isChose);
				//TODO 获取对象
				ContactsOfContact contact = list.get(position-1);
				if (isChose) {  //选中
					addContact(contact);
				}else if(groupList.contains(contact)){
					removeContatc(contact);
				}
			}
		});
		
		hListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				ContactsOfContact contact = groupList.get(position);
				removeContatc(contact);
				//一定要刷新视图,否则你就联动不起来
				adapter.setList(list);
				
			}
		});
	}
	
	/**
	 * 把对象添加到水平ListView数据源中
	 * @param contact
	 */
	private void addContact(ContactsOfContact contact){
		contact.setAddGroup(true);  //设置对象选中状态
		groupList.add(contact); 
		hAdapter.setList(groupList);
		changWidth();
		hListView.setSelection(groupList.size() -1); //设置水平ListView的选中项
		if (groupList.size() == 0) {//设置右边TextView的文本
			rightTv.setText("确定");
		}else {
			rightTv.setText("确定"+"("+groupList.size()+")");
		}
	}
	
	/**
	 * //把contact对象从水平ListView数据源中删除
	 * @param contact
	 */
	private void removeContatc(ContactsOfContact contact){
		contact.setAddGroup(false);  //设置对象选中状态
		groupList.remove(contact); 
		hAdapter.setList(groupList); //刷新视图
		changWidth();
		hListView.setSelection(groupList.size() -1);//设置水平ListView的选中项
		contact.setAlpha(ALPHATWO);  //把图片的透明度设置为1.0
		if (groupList.size() == 0) { //设置右边TextView的文本
			rightTv.setText("确定");
		}else {
			rightTv.setText("确定"+"("+groupList.size()+")");
		}
	}
	
	/**
	 * 改变水平listView的显示宽度
	 */
	private void changWidth(){
		ImageView searchIrcon = (ImageView) findViewById(R.id.group_chat_search_bar);		
		int size = groupList.size(); //获取水平ListView数据源的大小
		if (size == 0) { //数据源没有数据的时候
			searchIrcon.setVisibility(View.VISIBLE);
			return;
		}
		searchIrcon.setVisibility(View.GONE);
		//拿相对应的布局LayoutParams
		RelativeLayout.LayoutParams params = (LayoutParams) hListView.getLayoutParams();
		/**
		 * 要大一点点，不然会挤压边框 TODO 原因？  模拟器上运行良好
		 */
		int newWidth = size*DensityUtil.dp2px(this,53);  //所有数据源的图片的宽度
		//要小一点点，不然会消掉后面的close键，DensityUtil.getDisplay(this)[0]得到的是宽度（屏幕）
		int maxWidth = DensityUtil.getDisplay(this)[0]-DensityUtil.dp2px(this,100);
		if (newWidth > maxWidth) { //数据源中所有图片的宽度大于最大宽度时
			params.width = maxWidth;
		}else {  //不大于最大宽度的时候
			params.width = newWidth;
		}
		hListView.setLayoutParams(params);	
	}	
	
	/**
	 * @ClassName: SearchTextWatch 
	 */
	class SearchTextWatch implements TextWatcher{
		private List<ContactsOfContact> subList = new ArrayList<ContactsOfContact>(); //以搜索内容开头集合
		private List<ContactsOfContact> subSonList = new ArrayList<ContactsOfContact>(); //包含搜索内容集合
		GroupChatAdapter adapter;  //传递ListView的Adapter,用于改变视图
		View headBody;  //要隐藏的视图
		
		public SearchTextWatch(GroupChatAdapter adapter) {
			this.adapter = adapter;
			headBody = findViewById(R.id.item_head_gounp); 
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
				headBody.setVisibility(View.VISIBLE);
				sideBar.setVisibility(View.VISIBLE);
				adapter.setList(list); 	
				return;
			}else{
				falg = true;
			}
			
			headBody.setVisibility(View.GONE); //隐藏头视图
			sideBar.setVisibility(View.GONE);
			boolean isChinese = TextUtil.isChinese(input); //判断是否为中文
			for (int i = 0; i < list.size(); i++) {
				ContactsOfContact contacts = list.get(i);
				
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_DEL && falg ==false ){  //判断点击是删除键 
			if (groupList.size() == 0) { //水平ListView没有数据
				return super.onKeyDown(keyCode, event);
			}else {
				if (groupList.get(groupList.size()-1).getAlpha() == ALPHATWO ){  //透明度为1.0f
					groupList.get(groupList.size()-1).setAlpha(ALPHAFIRST);
				}else { //为0.5的时候
					removeContatc(groupList.get(groupList.size()-1));
				}
			}
			
			hAdapter.setList(groupList);
			adapter.setList(list);
		}else{
			falg =false;
		}
		return super.onKeyDown(keyCode, event);
	}
}

package com.wuxianedu.wechat.activity;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.adapter.ChatAdapter;
import com.wuxianedu.wechat.adapter.ChatAdapter.ViewHolder;
import com.wuxianedu.wechat.bean.ChatMsg;
import com.wuxianedu.wechat.bean.ContactsOfWeChat;
import com.wuxianedu.wechat.datebase.WechatDB;
import com.wuxianedu.wechat.listener.ChatListener;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.ExpressionUtil;
import com.wuxianedu.wechat.utils.FileUtil;
import com.wuxianedu.wechat.utils.ImageUtil;
import com.wuxianedu.wechat.utils.SetTime;
import com.wuxianedu.wechat.utils.TextUtil;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLayoutChangeListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ChatActivity extends BaseActivity  implements OnClickListener,TextWatcher,
				OnItemClickListener,OnLayoutChangeListener{	
	//数据对象
	private ContactsOfWeChat weChat; 
	//发送按钮,加号按钮,表情按钮，声音按钮，键盘按钮，右边键盘按钮
	private TextView sendTv, jiahao,voice,biaoqing, key,talk,key_right,talk_hit;
	private EditText sendEd;			 //输入编辑框
	private View toolView,voice_bg;     //工具栏的视图
	private ChatAdapter adapter;       //发送消息适配器
	private ListView msglv;           //发送消息的listview
	private ImageView photo,camera,leftImage,voice_mic,file,location;  //照片 和 相机
	private List<ChatMsg> chatList = new ArrayList<>();      //初始化消息集合（为空）
	private List<Map<String, Object>> biaoqList; //表情集合
	private String imgName;				//图片路径
	private GridView biaoqGv;    		//表情的GridView视图
	private InputMethodManager imm ;  	//键盘管理器
	private MediaPlayer player; 		//播放器
	private View rootView;
	private String voicePath;  			//语音的路径
	private MediaRecorder recoder; 		//录音
	private AnimationDrawable aDrawable;
	private boolean isSend = false; 	//是否发送录音
	private long startRecoderTime; 		//开始录音的时间
	private WechatDB wechatDB;
	private int t_id;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setMenu();   	//设置标题栏属性
		initView();     //初始化视图对象
		initData();  	//初始化数据对象
		//设置左边图标监听
		leftImage.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				imm.hideSoftInputFromWindow(sendEd.getWindowToken(),0);
				finish();
			}
		});
	}
		
	/**
	 * 重写父类抽象方法，设置视图
	 */
	@Override
	public int getContentView() {	
		return R.layout.activity_chat;
	}
	/**
	 * 此方法用于设置标题栏属性
	 */
	private void setMenu(){
		Intent intent = getIntent();
		weChat =(ContactsOfWeChat) intent.getSerializableExtra(Constant.CHAT_LIST);
		t_id = intent.getIntExtra(Constant.CHAT_ID, 1);
		setTitleName(weChat.getName());
		showRightImage(R.drawable.icon_chat_user, new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				showMessage("聊天信息");
			}
		});
	}
	
	/**
	 * 初始化视图对象
	 */
	private void initView(){
		imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);	
		biaoqList = ExpressionUtil.buildExpressionsList(this);
		leftImage = (ImageView) findViewById(R.id.back_id);
		jiahao= (TextView) findViewById(R.id.jiahao_id);
		voice= (TextView) findViewById(R.id.voice_id);
	    biaoqing= (TextView) findViewById(R.id.biaoqing_id);
	    msglv = (ListView) findViewById(R.id.chat_lv_id);
		sendEd = (EditText) findViewById(R.id.send_et_id);
		sendTv = (TextView) findViewById(R.id.send_tv_id);
		key = (TextView) findViewById(R.id.key_id);
		talk = (TextView) findViewById(R.id.talk_id);
		key_right = (TextView) findViewById(R.id.key_right_id);
		photo =(ImageView) findViewById(R.id.photo_id);
		camera = (ImageView) findViewById(R.id.camera_id);
		toolView = findViewById(R.id.tool_id);
		biaoqGv = (GridView) findViewById(R.id.biaoq_gv_id);
		voice_bg = findViewById(R.id.voice_bg);
		voice_mic =(ImageView) findViewById(R.id.chat_voice_mic);
		aDrawable = (AnimationDrawable) voice_mic.getDrawable();
		rootView = findViewById(R.id.root_id);
		talk_hit = (TextView) findViewById(R.id.chat_voice_hint);
		file = (ImageView) findViewById(R.id.send_file_id);
		location = (ImageView) findViewById(R.id.location_id);
		//设置控件的监听
		setViewLiener();

		setGridView();
	}
	
	/**
	 * 初始化数据对象
	 */
	private void initData(){
		wechatDB = WechatDB.getInstance(this);
		//初始化第一个数据
		if(wechatDB.loadChatMsg(String.valueOf(t_id)).size()== 0){		
			ChatMsg chatMsg = new ChatMsg();
			chatMsg.setContent(weChat.getLastStr());
			chatMsg.setType(ChatMsg.TYPE_RECEIVED);
			chatMsg.setTypeContent(ChatMsg.TYPE_CONTENT_TEXT);
			chatMsg.setHead(weChat.getHead());
			chatMsg.setTime(weChat.getLastTime());	
			chatMsg.setContactsOfWeChat_id(t_id);
			//添加第一个对象进集合
			chatList.add(chatMsg);	
			//TODO  修改数据库
			updataDB(chatMsg);		
		}
		chatList = wechatDB.loadChatMsg(String.valueOf(t_id));	
		player = new MediaPlayer();
		adapter = new ChatAdapter(this,chatList,player);
		adapter.setOnItemListener(new MyChatListener());
		//设置适配器
		msglv.setAdapter(adapter);
		msglv.setSelection(chatList.size()-1);
		//设置输入框的焦点为false(初始化)
		sendEd.setFocusable(false);
		sendEd.setFocusableInTouchMode(false);
		sendEd.clearFocus();
	}
	
	/**
	 * 设置控件的监听
	 */
	
	private void setViewLiener(){
		//设置加号监听器
		jiahao.setOnClickListener(this);
		//设置声音监听器
		voice.setOnClickListener(this);
		//设置键盘监听器
		key.setOnClickListener(this);
		key_right.setOnClickListener(this);
		//设置表情监听器
		biaoqing.setOnClickListener(this);
		//发送按钮设置监听
		sendTv.setOnClickListener(this);
		//设置输入框的监听
		sendEd.addTextChangedListener(this);
		sendEd.setOnClickListener(this);
		//设置图片点击监听
		photo.setOnClickListener(this);
		//设置拍照点击监听
		camera.setOnClickListener(this);
		//设置点击文件监听
		file.setOnClickListener(this);
		//设置点击位置监听
		location.setOnClickListener(this);
		//消息lsitview的监听
		//msglv.setOnItemClickListener(this);
		//表情的点击事件
		biaoqGv.setOnItemClickListener(this);
		msglv.setOnTouchListener(new LisTouch());
		rootView.addOnLayoutChangeListener(this);
		talk.setOnTouchListener(new VoiceTouch());
		
	}
	
	
	/**
	 * 所有点击事件的监听
	 */
	@Override
	public void onClick(View v) {	
		switch (v.getId()) {
			case R.id.send_et_id:  //点击发送按钮
				pressSendBut();
				break;
			case R.id.jiahao_id:    //点击加号
				pressJiaHao();
				break;
			case R.id.voice_id:  //点击声音
				pressVoiceBut();
				break;
			case R.id.send_tv_id:  //点击发送按钮
				sendMsg();
				break;
			case R.id.key_id: //点击键盘按钮
				pressJianPanBut();	
				break;
			case R.id.key_right_id:  //点击右边键盘
				pressRightJianPanBut();
				break;		
			case R.id.photo_id: //点击图片 按钮
				pressPhotoBut();	
				break;
			case R.id.camera_id: //点击拍照按钮
				pressCameraBut();
				break;		
			case R.id.biaoqing_id:  //点击表情
				pressBiaoQing();
				break;
			case R.id.send_file_id: //点击文件
				pressFile();	
				break;	
			case R.id.location_id: //点击位置
				showMessage("点击位置");
				jumpActivity(MapLocationActivity.class);	
				break;
		}
		/**
		 * 这种连续的if  必须保证后面的 if中的内容{}  不会修改前面if的判断()
		 */
		if (!key_right.isShown()) { //如果右侧键盘不显示（输入文字状态），当输入框活获得焦点时   显示键盘
			//获得焦点
			if (sendEd.hasFocus()) { 
				//显示键盘，隐藏工具栏，隐藏表情GV
				imm.showSoftInput(sendEd, InputMethodManager.RESULT_SHOWN);  
				toolView.setVisibility(View.GONE);
				biaoqGv.setVisibility(View.GONE);
				if (!TextUtil.isEmpty(sendEd.getText().toString())) {
					sendTv.setVisibility(View.VISIBLE);
					jiahao.setVisibility(View.GONE);
				}else {
					sendTv.setVisibility(View.GONE);
					jiahao.setVisibility(View.VISIBLE);
				}
			}else{
				//隐藏键盘,
				imm.hideSoftInputFromWindow(sendEd.getWindowToken(),0);
			}
		} else{					//如果右侧键盘显示（输入图片状态），当输入框获得焦点时	TODO 此段代码经过深思熟虑，别改				
			if (sendEd.hasFocus()){
				//显示表情工具栏
				biaoqGv.setVisibility(View.VISIBLE); 
				toolView.setVisibility(View.GONE);
				imm.hideSoftInputFromWindow(sendEd.getWindowToken(),0);
			}else{	
				//TODO  右侧键盘显示，输入框失去焦点,此处if嵌套可以被优化
			}
		}
	
		if (voice.isShown()) {   //声音显示       提示信息隐藏，左侧键盘隐藏，按下说话隐藏，发送显示
			voice_bg.setVisibility(View.GONE);
			key.setVisibility(View.GONE);
			talk.setVisibility(View.GONE);
			sendEd.setVisibility(View.VISIBLE);
		}	
	}
	
	private void pressSendBut(){
		sendEd.setFocusable(true);
		sendEd.setFocusableInTouchMode(true);
		sendEd.requestFocus();
		biaoqing.setVisibility(View.VISIBLE);
		key_right.setVisibility(View.GONE);
	}
	
	private void pressJiaHao(){
		if (!toolView.isShown()) { 		//工具栏不显示  ，设置显示，失去焦点
			sendEd.setFocusable(false);
			sendEd.setFocusableInTouchMode(false);
			sendEd.clearFocus();
			toolView.setVisibility(View.VISIBLE);
		}else{ 						//工具栏显示时 ，设置不显示 得到焦点
			sendEd.setFocusable(true);
			sendEd.setFocusableInTouchMode(true);
			sendEd.requestFocus();
			toolView.setVisibility(View.GONE);
		}
		//显示表情，隐藏键盘右侧
		voice.setVisibility(View.VISIBLE);
		biaoqGv.setVisibility(View.GONE);
		biaoqing.setVisibility(View.VISIBLE);
		key_right.setVisibility(View.GONE);
	} 
	
	private void pressVoiceBut(){
		//显示 左侧键盘视图     显示按下条视图 ，显示加号视图
		key.setVisibility(View.VISIBLE);
		talk.setVisibility(View.VISIBLE);
		jiahao.setVisibility(View.VISIBLE);
		//隐藏     声音视图   表情视图   输入框视图   工具栏视图   表情GV视图   发送按钮视图
		voice.setVisibility(View.GONE);
		biaoqing.setVisibility(View.GONE);
		sendEd.setVisibility(View.GONE);
		toolView.setVisibility(View.GONE);
		biaoqGv.setVisibility(View.GONE);
		sendTv.setVisibility(View.GONE);
	}
	
	private void pressJianPanBut(){
		//输入框获得焦点,  显示声音视图， 显示表情视图
		sendEd.setVisibility(View.VISIBLE);	 //必须加上这一句不然拿不到焦点 TODO
		sendEd.setFocusable(true);
		sendEd.setFocusableInTouchMode(true);
		sendEd.requestFocus();
		voice.setVisibility(View.VISIBLE);
		biaoqing.setVisibility(View.VISIBLE);
		//隐藏键盘视图， 隐藏按下说话视图   隐藏右边键盘
		key_right.setVisibility(View.GONE);
		//根据文件有无属性，设置是否显示发送
		if (!TextUtil.isEmpty(sendEd.getText().toString())) {
			sendTv.setVisibility(View.VISIBLE);
			jiahao.setVisibility(View.GONE);
		}else {
			sendTv.setVisibility(View.GONE);
			jiahao.setVisibility(View.VISIBLE);
		}
	}
	
	/**
	 * 发送消息:将输入框中的东西写入集合   刷新ListView 清空输入框
	 */
	private void sendMsg(){
		String content = sendEd.getText().toString();
		//如果输入框内的内容不为空
		if(!TextUtil.isEmpty(content)){
			ChatMsg chatMsg = new ChatMsg();
			chatMsg.setContent(content);
			chatMsg.setType(ChatMsg.TYPE_SENT);
			chatMsg.setTypeContent(ChatMsg.TYPE_CONTENT_TEXT);
			chatMsg.setTime(SetTime.getNowTime());
			chatMsg.setContactsOfWeChat_id(t_id);
			chatList.add(chatMsg);	
			//TODO  修改数据库
			updataDB(chatMsg);
			adapter.setList(chatList);
			msglv.setSelection(chatList.size()-1);
			//设置为空
			sendEd.setText("");
		}
	}
	
	private void pressPhotoBut(){
		Intent intent;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4以上
			intent = new Intent(Intent.ACTION_PICK,Media.EXTERNAL_CONTENT_URI);
		}else{
			intent = new Intent(Intent.ACTION_GET_CONTENT,null); //4.4以下及4.4
		}
		intent.setDataAndType(Media.EXTERNAL_CONTENT_URI,"image/*");
		startActivityForResult(intent, Constant.POHTO );
	}
	
	private void pressRightJianPanBut(){
		sendEd.setFocusable(true);
		sendEd.setFocusableInTouchMode(true);
		sendEd.requestFocus();
		biaoqing.setVisibility(View.VISIBLE);
		key_right.setVisibility(View.GONE);
	}
	
	private void pressCameraBut(){
		Intent photoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);//打开相机
		String photoPath = FileUtil.getSDCardPath()+"MyChat/photp/";
		FileUtil.checkDir(photoPath);
		imgName = photoPath + "image" + System.currentTimeMillis() + ".jpg";
		//这个对象是存在的
		Uri uri = Uri.fromFile(new File(imgName));
		//将路径设置给相机
		photoIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
		startActivityForResult(photoIntent, Constant.CAMERA);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == Constant.POHTO && resultCode == Activity.RESULT_OK) {
			Uri uri = data.getData();
			resultPhoto(uri);
		}else if(requestCode == Constant.CAMERA && resultCode == Activity.RESULT_OK) {
			resultCaMera();
		}
	}
	
	/**
	 * 从图库返回
	 * @param uri
	 */
	private void resultPhoto(Uri uri){		
		//此处有时候需要读写权限
		String path = ImageUtil.getRealFilePath(this, uri);
		ChatMsg chatMsg = new ChatMsg();
		chatMsg.setContent(path);
		chatMsg.setType(ChatMsg.TYPE_SENT);
		chatMsg.setTypeContent(ChatMsg.TYPE_CONTENT_IMG);
		chatMsg.setTime(SetTime.getNowTime());
		chatMsg.setContactsOfWeChat_id(t_id);
		//TODO  修改数据库
		updataDB(chatMsg);
		chatList.add(chatMsg);
		adapter.setList(chatList);				//刷新适配器
		msglv.setSelection(chatList.size()); 		//设置选中位置	
	}
	
	/**
	 * 从拍照处返回
	 */
	private void resultCaMera(){
		//此处有时候需要读写权限
		ChatMsg chatMsg = new ChatMsg();
		chatMsg.setContent(imgName);
		chatMsg.setType(ChatMsg.TYPE_SENT);
		chatMsg.setTypeContent(ChatMsg.TYPE_CONTENT_IMG);	
		chatMsg.setTime(SetTime.getNowTime());
		chatMsg.setContactsOfWeChat_id(t_id);
		chatList.add(chatMsg);
		//TODO  修改数据库
		updataDB(chatMsg);
		adapter.setList(chatList);
		msglv.setSelection(chatList.size()); 		//设置选中位置	
		addImage(imgName);
	}
	
   /**
    * 保存照片到本地
    */	
   private void addImage(String path){
        ContentResolver contentResolver = getContentResolver();
        ContentValues contentValues = new ContentValues();
        contentValues.put("_data",path);
        contentValues.put("mime_type","image/jpg");
        Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        contentResolver.insert(uri,contentValues);
    }
   
	private void pressBiaoQing(){
		sendEd.setFocusable(true);
		sendEd.setFocusableInTouchMode(true);
		sendEd.requestFocus();
		key_right.setVisibility(View.VISIBLE);
		biaoqGv.setVisibility(View.VISIBLE);
	}

	private void pressFile(){
		Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
		intent2.setType("*/*");
		intent2.addCategory(Intent.CATEGORY_OPENABLE);
		startActivityForResult(intent2,200);
	}
	
	
	
	@Override
	public void beforeTextChanged(CharSequence s, int start, int count, int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}
	
	@Override
	public void afterTextChanged(Editable s) {
		String content = s.toString();
		if(TextUtil.isEmpty(content)){	
			//当文本内容改变时，并不为空时，显示加号  隐藏按钮
			jiahao.setVisibility(View.VISIBLE);
			sendTv.setVisibility(View.GONE);
		}else{
			//当文本内容改变时，并为空时，显示按钮  隐藏加号
			jiahao.setVisibility(View.GONE);
			sendTv.setVisibility(View.VISIBLE);
		}
	
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch (parent.getId()) {
		case R.id.biaoq_gv_id:
			//拿到对用表情集合
			Map<String, Object> map = biaoqList.get(position);
			//获取字符串形式的表情ID
			String formatStr = (String) map.get("drawableId");
			//转换成方括号形式表情
			formatStr = ExpressionUtil.drawableIdToFaceName.get(formatStr);
			//获取光标位置
			int index = sendEd.getSelectionStart();
			//获取输入中的文本内容
			String string = sendEd.getText().toString();
			StringBuffer sBuffer = new StringBuffer(string);
			//insert能在字符串的任意位置添加，而append只能在末尾.
			string = sBuffer.insert(index,formatStr).toString();
			SpannableString  ss = ExpressionUtil.decorateFaceInStr(ChatActivity.this,string);
			//设置拼接后的文本
			sendEd.setText(ss);
			//设置光标位置
			sendEd.setSelection(index+formatStr.length());
			break;
		}
	}
	/**
	 * 设置表情GridView的Adapter
	 */
	private void setGridView(){
		//设置gridview
		List<Map<String, Object>>listBiaoQ = new ArrayList<>();
		String [] string = {"drawableRId"};
		int[] ints ={R.id.biaoq_img_id};
		//得到表情的list集合
		listBiaoQ = ExpressionUtil.buildExpressionsList(this);
		SimpleAdapter simpleAdapter = new SimpleAdapter(this, listBiaoQ, R.layout.item_gridview_biaoq, string, ints);
		biaoqGv.setAdapter(simpleAdapter);
	}
	
	class MyChatListener implements ChatListener{
		private	 List<String> uriList= new ArrayList<String>();
		private  List<ChatMsg> imgList = new ArrayList<ChatMsg>();
		
		@Override
		public void onItemClick(ViewHolder viewHolder, int position) {		
			 uriList.clear();
			 imgList.clear();
			 for (int i = 0; i < chatList.size(); i++) {
					if(chatList.get(i).getTypeContent() == ChatMsg.TYPE_CONTENT_IMG){
						imgList.add(chatList.get(i));
						uriList.add(chatList.get(i).getContent());
					}
			}
			 Intent intent = new Intent(ChatActivity.this,ImageBrowseActivity.class);			
			 int index =  imgList.indexOf(chatList.get(position));
			 intent.putExtra(Constant.POSITION, index);
			 intent.putExtra(Constant.IMAGE_LIST, (Serializable)uriList);
			 intent.putExtra(Constant.BOOLEAN, true);
			 intent.putExtra(Constant.IMAGEBROWSE, true);
			 //跳转页面之前，关闭键盘
			 imm.hideSoftInputFromWindow(sendEd.getWindowToken(),0);
			 startActivity(intent); 		 
		}	
	}
	
	/**
	 * lisView的点击事件：关闭键盘，下布局
	 * @author Administrator
	 *
	 */
	@SuppressLint("ClickableViewAccessibility")
	class LisTouch implements OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_DOWN) {
				sendEd.setFocusable(false);
				sendEd.setFocusableInTouchMode(false);
				sendEd.clearFocus();
				toolView.setVisibility(View.GONE);
				biaoqGv.setVisibility(View.GONE);
				imm.hideSoftInputFromWindow(sendEd.getWindowToken(),0);
				if (key_right.isShown()) {
					key_right.setVisibility(View.GONE);
					biaoqing.setVisibility(View.VISIBLE);
				} 
			}
			return false;
		}
		
	}
	
	class VoiceTouch implements OnTouchListener{

		@SuppressLint("ClickableViewAccessibility")
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:  //摁下的时候,开始录音
				talk.setText("松开发送");
				voice_bg.setVisibility(View.VISIBLE);
				aDrawable.start();
				startRecoder();   //开始录音
				isSend = true;
				break;
			case MotionEvent.ACTION_MOVE:  //移动的时候,改变录音提示,是否发送录音				
				if (event.getY() < 0 ) {
					talk.setText("取消发送");
					talk_hit.setText("松开手指,取消发送");
					isSend = false;
				}else {
					talk_hit.setText("向上滑动,取消发送");
					isSend = true;
				}
				break;
			case MotionEvent.ACTION_UP:  //抬起的时候,发送录音
				talk.setText("按下说话");
				voice_bg.setVisibility(View.GONE);
				aDrawable.stop();
				stopRecoder();   	//结束录音
				if (isSend) {
					sendVoice();	//发送录音
				}
				break;
			}
			return false;
		}
	}
	
	/**
	 * 发送声音的方法
	 */
	private void sendVoice(){
		ChatMsg chatMsg = new ChatMsg();
		chatMsg.setTypeContent(ChatMsg.TYPE_CONTENT_VOICE); //设置消息类型
		chatMsg.setType(ChatMsg.TYPE_SENT); 		//设置来源
		chatMsg.setContent(voicePath);				//设置路径
		chatMsg.setTime(SetTime.getNowTime());
		chatMsg.setContactsOfWeChat_id(t_id);
		chatList.add(chatMsg);
		//TODO 操作数据库
		updataDB(chatMsg);
		adapter.setList(chatList);				
		msglv.setSelection(chatList.size()); 		//设置选中位置
	}
	
	/**
	 * 开始录音
	 */	
	@SuppressWarnings("deprecation")
	private void startRecoder(){
		if (recoder != null) {
			recoder.reset(); 
		}else {
			recoder = new MediaRecorder();
		}
		//必须要开权限
		recoder.setAudioSource(MediaRecorder.AudioSource.MIC);//设置录音源为麦克风
		recoder.setOutputFormat(MediaRecorder.OutputFormat.RAW_AMR);//设置输出格式
		recoder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);//设置解码格式
		
		String string = FileUtil.getSDCardPath()+"weChat/voice/"; //创建录音存放的目录
		FileUtil.checkDir(string);  //检查目录是否存在,不存在则创建
		voicePath = string + "voice" + System.currentTimeMillis() + ".amr";
		recoder.setOutputFile(voicePath);
		try {
			recoder.prepare(); //准备
		} catch (IllegalStateException e) {
			e.printStackTrace();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		startRecoderTime = System.currentTimeMillis(); //记录开始录音的时间
		recoder.start(); //开始录音
	}
	
	/**
	 * stopRecoder:结束录音
	 * @date: 2016年9月5日 下午2:55:19 
	 * 
	 */
	private void stopRecoder(){
		recoder.stop();  //结束
		if (System.currentTimeMillis() - startRecoderTime < 1000) {  //录音的时间比1秒还少,就不发送
			isSend = false;
			File file = new File(voicePath);  //得到音频所在文件
			if (file.exists()) {  //检查文件是否存在
				file.delete();  //存在则删除
			}
			Toast.makeText(this, "录音时间过短,请重录.....", Toast.LENGTH_SHORT).show();
		}
		if (recoder != null) {
			recoder.release();  //释放资源
			recoder = null;  //把引用置为空
			System.gc();  //通知垃圾回收机制回收
		}
	}
	
	@Override
	public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight,
			int oldBottom) {
		//判断是不是刚进来的时候
		if (bottom != 0 && oldBottom != 0 && oldBottom - bottom > 0){
			adapter.notifyDataSetChanged();				//刷新适配器
			msglv.setSelection(chatList.size()); 		//设置选中位置
		}
	}
	
	@Override
	protected void onDestroy() {		
		if (player != null) {
			player = null;
			System.gc();
		}	
		imm.hideSoftInputFromWindow(sendEd.getWindowToken(),0);
		chatList = null;
		super.onDestroy();
	}
	
	/**
	 * 修改微信界面联系人表
	 */
	private void updataDB(ChatMsg chatMsg){
		wechatDB.savaChatMsg(chatMsg);
		ContentValues contentValues = new ContentValues();
		int type  = chatMsg.getType();
		int typeContent = chatMsg.getTypeContent();
		String content = chatMsg.getContent();
		String time = chatMsg.getTime();
		if (type == ChatMsg.TYPE_RECEIVED ) {//如果是接受的那条消息，则不修改
			return;
		}
		if (type == ChatMsg.TYPE_SENT) {//如果是发送消息
			contentValues.put("lastTime", time);
			if (typeContent == ChatMsg.TYPE_CONTENT_TEXT) {
				contentValues.put("lastStr", content);
				
			}else if (typeContent == ChatMsg.TYPE_CONTENT_VOICE) {
				contentValues.put("lastStr", "语言");
				
			}else if (typeContent == ChatMsg.TYPE_CONTENT_IMG) {
				contentValues.put("lastStr","图片");
				
			}
			
		}		
		wechatDB.updateContactsOfWeChat(contentValues, 
				"_id = ?", new String[]{String.valueOf(t_id)});
	}
}

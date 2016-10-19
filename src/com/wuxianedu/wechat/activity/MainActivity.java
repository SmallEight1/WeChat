package com.wuxianedu.wechat.activity;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.fragment.ContactsFragment;
import com.wuxianedu.wechat.fragment.FindFragment;
import com.wuxianedu.wechat.fragment.MeFragment;
import com.wuxianedu.wechat.fragment.WeiXinFragment;
import android.annotation.SuppressLint;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends BaseActivity implements OnCheckedChangeListener
												,OnClickListener,OnPageChangeListener{
	private Fragment []fragments = new Fragment[4];	
	private RadioButton weixinBt,contactisBt,findBt,meBt;
	private RadioButton[] radioButtons= new RadioButton[]{weixinBt,contactisBt,findBt,meBt};
	private int[] IdsOfButton = new int[]{R.id.bt1_id,R.id.bt2_id,R.id.bt3_id,R.id.bt4_id};
	private ViewPager viewPager;
	private PopupWindow popupWindow;
	private String[] titles = {"微信","通讯录","发现","我的"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//（==默认显示==）隐藏返回键（左），初始化标题和图片，初始第一个碎片，初始化视图对象
		hideLeftImage();
		setTitleAndImage(0);   
		createFragment(0);	    // 创建第一个对象	
		initialize();					//初始化 控件
	}
	
	@Override
	public int getContentView() {
		return R.layout.activity_main;
	}
	
	/**
	 * 设置标题文本和 图标
	 */
	private void setTitleAndImage(int index){
		setTitleName(titles[index]);
		switch (index) {
			case 0://微信页面（设置右边图标和它的监听）
				showRightImage(R.drawable.icon_add,new OnClickListener() {
					@Override
					public void onClick(View v) {						
						popuwindow(v);
					}
				});
				break;
			case 1://通讯录页面（设置右边图标和它的监听）
				showRightImage(R.drawable.icon_menu_addfriend,new OnClickListener() {		
					@Override
					public void onClick(View v) {
						showMessage("点击添加朋友");
					}
				});
				break;
			default://其他页面 隐藏 右边图标
				hideRightImage();
				break;
		}
	}
	
	/**
	 * 初始化数据，并设置监听，
	 */
	private void initialize(){
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroud_id);
		for (int i = 0; i < IdsOfButton.length; i++) {
			radioButtons[i] = (RadioButton) findViewById(IdsOfButton[i]);
		}
		viewPager = (ViewPager) findViewById(R.id.vp_id);	
		radioGroup.setOnCheckedChangeListener(this );
		setView();
	}
	
	private void setView(){
		FragmentManager fragmentManager = getSupportFragmentManager();
		viewPager.setAdapter(new MyAdapter(fragmentManager));
		//为 onPageSelected 设置选中状态监听器
		viewPager.addOnPageChangeListener(this);
	}
	
	/**
	 * 实例 fragment对象  如果为空创建   不为空 直接使用，index为选中哪一个对象，在监听事件中给出(check)
	 */
	private Fragment createFragment(int index){
		//判断 点击位置的 fragment是否创建
		Fragment fragment = fragments[index];
		if(fragment == null){
			switch (index) {
				case 0:      //初始化微信界面对象
					fragments[index] = new WeiXinFragment();		
					break;
				case 1:		//初始化联系人界面对象
					fragments[index] = new ContactsFragment();
					break;
				case 2:		//初始化发现界面对象
					fragments[index] =  new FindFragment();	
					break;
				case 3:		//初始化我的界面对象
					fragments[index] = new MeFragment();				
					break;
			}
		}
		return fragments[index];
	}

	/**
	 * 创建一个类  继承碎片的设配器(pager)
	 */
	class MyAdapter extends FragmentPagerAdapter{

		public MyAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			return createFragment(arg0);
		}

		@Override
		public int getCount() {
			return fragments.length;
		}
	}
	//viewPager的监听事件
	@Override
	public void onPageScrollStateChanged(int arg0) {	
	}
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}
	@Override
	public void onPageSelected(int arg0) {
		radioButtons[arg0].setChecked(true);
	}	
	
	/**
	 *  监听事件  底部单选按钮组的（选中事件）
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for(int index = 0;index<4;index ++){
			if (checkedId == IdsOfButton[index]) {		
				createFragment(index);
				//同步（当按钮切换时  同时切换viewPager对象的选中状态）         第二个参数：是否有滚动效果
				viewPager.setCurrentItem(index,false);
				setTitleAndImage(index);
			}
		}
	}
	
	/**
	 * （父窗口）的构建
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void popuwindow(View v){
		View view = getLayoutInflater().inflate(R.layout.item_popuwindows, null);	
		 popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT, 
				ViewGroup.LayoutParams.WRAP_CONTENT,true);
		//解决 点击外部 不可关闭 bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//依赖于某空间下方显示
		popupWindow .showAsDropDown(v);		
		TextView chat = (TextView) view.findViewById(R.id.chat_id);
		TextView addfriend = (TextView) view.findViewById(R.id.addfriend_id);
		TextView sao = (TextView) view.findViewById(R.id.sao_id);
		TextView abv = (TextView) view.findViewById(R.id.abv_id);
		chat.setOnClickListener(this);
		addfriend.setOnClickListener(this);
		sao.setOnClickListener(this);
		abv.setOnClickListener(this);
	}
	/**
	 * popuwindow中控件的点击事件,监听
	 * @param v
	 */
	@Override
	public void onClick(View v) {
		popupWindow.dismiss();
		switch (v.getId()) {
			case R.id.chat_id:
				jumpActivity(GounpChatActivity.class);
				break;
			case R.id.addfriend_id:
				jumpActivity(NewsFriendActivity.class);
				break;
			case R.id.sao_id:
				showMessage("扫一扫");
				break;
			case R.id.abv_id:
				showMessage("微信支付");
				break;
		}	
	}
}

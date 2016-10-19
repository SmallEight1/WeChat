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
	private String[] titles = {"΢��","ͨѶ¼","����","�ҵ�"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//��==Ĭ����ʾ==�����ط��ؼ����󣩣���ʼ�������ͼƬ����ʼ��һ����Ƭ����ʼ����ͼ����
		hideLeftImage();
		setTitleAndImage(0);   
		createFragment(0);	    // ������һ������	
		initialize();					//��ʼ�� �ؼ�
	}
	
	@Override
	public int getContentView() {
		return R.layout.activity_main;
	}
	
	/**
	 * ���ñ����ı��� ͼ��
	 */
	private void setTitleAndImage(int index){
		setTitleName(titles[index]);
		switch (index) {
			case 0://΢��ҳ�棨�����ұ�ͼ������ļ�����
				showRightImage(R.drawable.icon_add,new OnClickListener() {
					@Override
					public void onClick(View v) {						
						popuwindow(v);
					}
				});
				break;
			case 1://ͨѶ¼ҳ�棨�����ұ�ͼ������ļ�����
				showRightImage(R.drawable.icon_menu_addfriend,new OnClickListener() {		
					@Override
					public void onClick(View v) {
						showMessage("����������");
					}
				});
				break;
			default://����ҳ�� ���� �ұ�ͼ��
				hideRightImage();
				break;
		}
	}
	
	/**
	 * ��ʼ�����ݣ������ü�����
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
		//Ϊ onPageSelected ����ѡ��״̬������
		viewPager.addOnPageChangeListener(this);
	}
	
	/**
	 * ʵ�� fragment����  ���Ϊ�մ���   ��Ϊ�� ֱ��ʹ�ã�indexΪѡ����һ�������ڼ����¼��и���(check)
	 */
	private Fragment createFragment(int index){
		//�ж� ���λ�õ� fragment�Ƿ񴴽�
		Fragment fragment = fragments[index];
		if(fragment == null){
			switch (index) {
				case 0:      //��ʼ��΢�Ž������
					fragments[index] = new WeiXinFragment();		
					break;
				case 1:		//��ʼ����ϵ�˽������
					fragments[index] = new ContactsFragment();
					break;
				case 2:		//��ʼ�����ֽ������
					fragments[index] =  new FindFragment();	
					break;
				case 3:		//��ʼ���ҵĽ������
					fragments[index] = new MeFragment();				
					break;
			}
		}
		return fragments[index];
	}

	/**
	 * ����һ����  �̳���Ƭ��������(pager)
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
	//viewPager�ļ����¼�
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
	 *  �����¼�  �ײ���ѡ��ť��ģ�ѡ���¼���
	 */
	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		for(int index = 0;index<4;index ++){
			if (checkedId == IdsOfButton[index]) {		
				createFragment(index);
				//ͬ��������ť�л�ʱ  ͬʱ�л�viewPager�����ѡ��״̬��         �ڶ����������Ƿ��й���Ч��
				viewPager.setCurrentItem(index,false);
				setTitleAndImage(index);
			}
		}
	}
	
	/**
	 * �������ڣ��Ĺ���
	 */
	@SuppressLint("InflateParams")
	@SuppressWarnings("deprecation")
	private void popuwindow(View v){
		View view = getLayoutInflater().inflate(R.layout.item_popuwindows, null);	
		 popupWindow = new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT, 
				ViewGroup.LayoutParams.WRAP_CONTENT,true);
		//��� ����ⲿ ���ɹر� bug
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		//������ĳ�ռ��·���ʾ
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
	 * popuwindow�пؼ��ĵ���¼�,����
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
				showMessage("ɨһɨ");
				break;
			case R.id.abv_id:
				showMessage("΢��֧��");
				break;
		}	
	}
}

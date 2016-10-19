package com.wuxianedu.wechat.activity;

import java.util.ArrayList;
import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.ImageUtil;
import com.wuxianedu.wechat.utils.SetImage;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageBrowseActivity extends BaseActivity {
	private List<View> listView;
	private List<String> list;
	private boolean isFromChat;
	private ViewPager viewPager;
	private TextView textView;
	
	@Override
	public int getContentView() {
		return R.layout.activity_image;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleName("图片浏览");	
		initialize();		
	}
	
	private void initialize(){
		viewPager = (ViewPager) findViewById(R.id.pg_id);
		textView = (TextView) findViewById(R.id.tv_id);		
		listView = new ArrayList<View>();
		ininitializeList();	
	}
	
	@SuppressLint("InflateParams")
	private void ininitializeList(){
		Bundle bundle = new Bundle();
		bundle= getIntent().getExtras();
		list = bundle.getStringArrayList(Constant.IMAGE_LIST);
		int position  = bundle.getInt(Constant.POSITION);
		//构建 ViewPager  要显示的视图
		for (int i = 0; i < list.size(); i++) {
			View view = getLayoutInflater().inflate(R.layout.item_viewpage_image, null);
			listView.add(view);
		}			
		if (getIntent().getBooleanExtra(Constant.IMAGEBROWSE, false)) {
			isFromChat = true;
		}	
		setView(position);
	}
	
	private void setView(int position){			
		viewPager.setAdapter(new MyPagerAdapter());
		//设置默认选中位置---
		viewPager.setCurrentItem(position);	
		textView.setText((position+1)+"/"+list.size());
		//加载第一次选中位置图片
		loadImage(position);
		//每次选中时 再加载
		viewPager.addOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				textView.setText((arg0+1)+"/"+list.size());			
				loadImage(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
	}
	
	/**
	 * 根据位置加载图片
	 */
	private void  loadImage(int position){
		//选中时  加载图片
		View view  = listView.get(position);
		//查找imageVeiw
		ImageView imageView = (ImageView) view.findViewById(R.id.im_id);	
		if (isFromChat) {  //从聊天来
			imageView.setImageBitmap(ImageUtil.getSmallBitmap(list.get(position), 400, 800));
		}else {
			SetImage.SetHeadImage(imageView, list.get(position));
		}
	}

	class MyPagerAdapter extends PagerAdapter{	

		@Override
		public int getCount() {
			return listView.size();
		}	
		
		/**
		 * TODO 简单理解  viewpager现在显示的视图 是否是要显示的视图 ?
		 */		
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}
		
		/**
		 * 移除不显的视图
		 */	
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			//从 ViewPager容器中移除 根据 position  移除对应位置的  view对象
			container.removeView(listView.get(position));
		}
		
		/**
		 * 添加要显示的视图  类似 adapter里 getview方法
		 */	
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			//根据 position 添加视图
			View view = listView.get(position);
			container.addView(view);					
			loadImage(position);			
			return view;
		}	
	}
}

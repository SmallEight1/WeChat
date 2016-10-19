package com.wuxianedu.wechat.utils;

import org.xutils.x;
import org.xutils.common.Callback.CommonCallback;
import org.xutils.image.ImageOptions;
import com.wuxianedu.wechat.R;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class SetImage {
	
	/**
	 * 设置头像
	 * @param imageView  圆形图片的视图控件对象
	 * @param url		网络地址
	 */
	public static void  SetHeadImage(final ImageView imageView,String url) {
		imageView.setImageResource(R.drawable.head_nor);
		x.image().loadDrawable(url, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				imageView.setImageDrawable(arg0);
			}
			
			@Override
			public void onFinished() {			
			}
			
			@Override
			public void onError(Throwable arg0, boolean arg1) {				
			}
			
			@Override
			public void onCancelled(CancelledException arg0) {
				imageView.setImageResource(R.drawable.head_nor);
			}
		});
	}
	
	/**
	 * 设置普通图片
	 * @param imageView 矩形图片的视图控件对象
	 * @param url       网络地址
	 */
	public static void  SetRecImage(final ImageView imageView,String url) {
		imageView.setImageResource(R.drawable.image_nor);
		x.image().loadDrawable(url, ImageOptions.DEFAULT, new CommonCallback<Drawable>() {
			
			@Override
			public void onSuccess(Drawable arg0) {
				imageView.setImageDrawable(arg0);
			}
			
			@Override
			public void onFinished() {			
			}
			
			@Override
			public void onError(Throwable arg0, boolean arg1) {				
			}
			
			@Override
			public void onCancelled(CancelledException arg0) {
				imageView.setImageResource(R.drawable.image_nor);
			}
		});
	}
}

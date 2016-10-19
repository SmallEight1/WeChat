package com.wuxianedu.wechat.widget;


import com.wuxianedu.wechat.utils.DensityUtil;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * @ClassName:SearchEditText 
 * @Function: 自动清除输入框 
 * @Date:     2016年7月9日 下午7:16:45 
 * @author   yifeng.Zhang
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class SearchEditText extends EditText{

	private Context context;
	private Drawable draw,drawleft;
	
	public SearchEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		// 左上右下  
		drawleft = getCompoundDrawables()[0];
		draw = getCompoundDrawables()[2];
		int leftsize = DensityUtil.dp2px(context,20);
		int right = DensityUtil.dp2px(context,16);

//		int widtd = (int) (size*(96.0/84.0));
		// 距离左边的距离   距离上边的距离   宽 高
		drawleft.setBounds(0,0,leftsize, leftsize);
		draw.setBounds(0,0,right,right);
		init();
	}


	private void init(){
		addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			}
			@Override
			public void afterTextChanged(Editable s) {
				setDrawable();
			}
		});
		setDrawable();
	}
	
	private void setDrawable(){
		if(length() < 1)    //如果没有输入字符时,只显示左边图片
			setCompoundDrawables(drawleft, null, null, null);
		else
			setCompoundDrawables(drawleft, null, draw, null);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//MotionEvent对象方式处理，比如开始触控时会触发ACTION_DOWN而移动操作时为ACTION_MOVE最终放开手指时触发ACTION_UP事件。
		//当然还有用户无规则的操作可能触发ACTION_CANCEL这个动作。
		if(draw != null && event.getAction() == MotionEvent.ACTION_UP){
			Rect rect = new Rect();
			//getLocalVisibleRect的作用是获取视图本身可见的坐标区域，坐标以自己的左上角为原点（0，0）
			//getGlobalVisibleRect方法的作用是获取视图在屏幕坐标中的可视区域
			getGlobalVisibleRect(rect);		//获取视图在屏幕中的可视区域
			
			rect.left = rect.right-100;		//矩形大小
			int x = (int) event.getRawX(); //getRawX()就是说,获取的是相对于屏幕左上角的x坐标，而getX()是获取相对控件左上角的x坐标。
			int y = (int) event.getRawY(); //
			
			if(rect.contains(x, y)){  //用来决定点(x,y)是否在此矩形框内
				setText("");
			}
		}
		
		return super.onTouchEvent(event);
	}
	
	@Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}


package com.wuxianedu.wechat.widget;

import com.wuxianedu.wechat.utils.DensityUtil;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

public class LoginEditText extends EditText {

	private Drawable drawableRight;
	
	public LoginEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		drawableRight = getCompoundDrawables()[2];
		int viewHeghit = DensityUtil.dp2px(context,16);
		int viewWidth = viewHeghit;
		drawableRight.setBounds(0,0,viewWidth,viewHeghit);
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
		if (length()<1) {
			setCompoundDrawables(null, null, null, null);
		}else{
			setCompoundDrawables(null, null, drawableRight, null);
		}		
	}
	
	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (drawableRight != null && event.getAction() == MotionEvent.ACTION_UP) {
			Rect rect = new Rect();
			getGlobalVisibleRect(rect);	
			rect.left = rect.right-100;		
			int x = (int) event.getRawX(); 
			int y = (int) event.getRawY(); 
			if(rect.contains(x, y)){  
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

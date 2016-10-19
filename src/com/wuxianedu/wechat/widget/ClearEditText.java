package com.wuxianedu.wechat.widget;


import com.wuxianedu.wechat.utils.DensityUtil;
import com.wuxianedu.wechat.utils.L;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.EditText;

/**
 * 带删除图标的EditText
 * @author Administrator
 *
 */
public class ClearEditText extends EditText{

	private Drawable rightDeleteIcon;
	public ClearEditText(Context paramContext, AttributeSet paramAttributeSet) {
		super(paramContext, paramAttributeSet);
		rightDeleteIcon = getCompoundDrawables()[2];
		int size = DensityUtil.dp2px(getContext(),18);
		//设置drawable的 图片的大小  
		rightDeleteIcon.setBounds(0, 0,size,size);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent event) {
		if ((rightDeleteIcon != null) && (event.getAction() == MotionEvent.ACTION_DOWN)) {
			float f1 = event.getX();
			float f2 = getWidth() - 80;
			L.e("f1="+f1+",f2="+f2);
			if (f1 > f2)
				setText(null);
				clearFocus();
		}
		return super.dispatchTouchEvent(event);
	}

	@Override
	public boolean onPreDraw() {
		int width = DensityUtil.dp2px(getContext(),100);
		if (TextUtils.isEmpty(getText()) || getWidth() < width) {
			setCompoundDrawables(null, null, null, null);
		} else {
			setCompoundDrawables(null, null, rightDeleteIcon, null);
		}
		return true;
	}
	
}

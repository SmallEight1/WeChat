package com.wuxianedu.wechat.widget;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.utils.DensityUtil;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HeaderViewListAdapter;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;

/**
 * 英文字母A-Z过滤控件
 * @author Administrator
 *
 */
public class SideBar extends View {
	private char[] l;
	private SectionIndexer sectionIndexter = null; //Adapter要实现的接口，用于选中是哪一个字母
	private ListView listView;
	private TextView mDialogText;
	//字母所占高度
	private int m_nItemHeight;
	
	
	//只在代码中使用时调用这一个构造函数
	public SideBar(Context context) {
		super(context);
		init(context);
	}

	//AttributeSet 是接收xml中定义的属性信息，这不一定是自定义布局，不是自定义布局也有该属性，要不xml中定义的属性信息就无法接收了。
	public SideBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	//在Xml文件中制定主题时,调用这个构造函数,主要是第三个参数管理管理主题
	public SideBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}
	
	private void init(Context context) {
		l = new char[] { '↑','#', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
			'W', 'X', 'Y', 'Z' };
	}

	
	/**
	 * 当视图大小改变的时候 触发
	 */
	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		//改进为  获取SideBar视图本身的高度 进行计算 设置字母所占高度
			m_nItemHeight = (h-DensityUtil.dp2px(getContext(),12))/l.length;

	}
	
	/**
	 * 获得 和索引列表匹配的 listview。
	 * @param list0
	 */
	public void setListView(ListView listView) {
		this.listView = listView;
	}

	/**
	 * 获得 和索引列表匹配的 TextView（提示字母）
	 * @param mDialogText
	 */
	public void setTextView(TextView mDialogText) {
		this.mDialogText = mDialogText;
	}

	/**
	 * 触屏事件
	 */
	public boolean onTouchEvent(MotionEvent event) {
		super.onTouchEvent(event);
		int i = (int) event.getY();
		
		//根据触摸位置  计算出所触摸的字母
		int idx = i / m_nItemHeight;  //取余算出是第几个字母
		if (idx >= l.length) {   //大于数组的长度时，选择最后一个
			idx = l.length - 1;
		} else if (idx < 0) {  //不足一个是选中数组第一个
			idx = 0;
		}
		//如果是触摸到 且滑动
		if (event.getAction() == MotionEvent.ACTION_DOWN
				|| event.getAction() == MotionEvent.ACTION_MOVE) {
			//显示提示文字
			mDialogText.setVisibility(View.VISIBLE);
			mDialogText.setText("" + l[idx]);
			
			if (sectionIndexter == null) {
				//获得 listview的adapter  带头视图的adapter
				HeaderViewListAdapter ha = (HeaderViewListAdapter) listView.getAdapter();
				// 转为索引器  
				sectionIndexter = (SectionIndexer) ha.getWrappedAdapter();
			}
			//  根据选中的字母获取在ListView中的位置 
			//  使用索引器获取当前字符对应的位置
			int position = sectionIndexter.getPositionForSection(l[idx]);
			if (idx > 1) {   //表示有选中的位置
//				if(position != -1){
					//listView有头视图，所以要position+1 position是Adapter中getPositionForSection返回的i
					listView.setSelection(position+1);  
//				}
			}else{  //不大于1时 idx=0 直接选中头视图
				listView.setSelection(idx);
			}
		
		} else {
			mDialogText.setVisibility(View.GONE);
		}
		return true;
	}

	/**
	 * 绘制A-Z字母
	 */
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		Paint paint = new Paint();
		paint.setColor(getResources().getColor(R.color.text_gray));
		paint.setTextSize(DensityUtil.sp2px(getContext(),13));
		Typeface font = Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL);
		paint.setTypeface(font);
		paint.setFlags(Paint.ANTI_ALIAS_FLAG);
		paint.setTextAlign(Paint.Align.CENTER);
		float widthCenter = getMeasuredWidth()/2;
		for (int i = 0; i < l.length; i++) {
			canvas.drawText(String.valueOf(l[i]), widthCenter, m_nItemHeight + (i * m_nItemHeight), paint);
		}
	}
	
}

package com.wuxianedu.wechat.activity;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.FileLocalCache;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;

/**
 * 设置   设置界面
 * @author Administrator
 *
 */
public class SettingActivity extends BaseActivity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		//设置标题名，隐藏右边图标
		setTitleName("设置");
		hideRightImage();
		//监听(退出登陆按钮)
		findViewById(R.id.exit_id).setOnClickListener(this);;
	}

	@Override
	public int getContentView() {	
		return R.layout.activity_setting;
	}
	/**
	 * 点击事件监听器
	 */
	@Override
	public void onClick(View v) {
		//构建对话框
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		String[]items = {"退出登录","退出程序"};
		setMessage(items,builder);
		//设置对话框外部点击可关闭,但点击外部空白不可关闭
		builder.setCancelable(true);
		//显示对话框
		Dialog dialog =builder.show();
		dialog.setCanceledOnTouchOutside(false);
	}
	
	private void setMessage(String[] items,AlertDialog.Builder builder){
		//设置对话框内容
		builder.setItems(items, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				switch (which) {
					case 0:	  					//退出登陆	
						//清除序列化对象
						FileLocalCache.delSerializableData(SettingActivity.this, 
								Constant.USER_INFOR);
						jumpActivity(LoginActivity.class);
						finishAll();
						break;
					case 1:  					//退出程序					
						exit();	
						break;
				}
			}
		});
	}
}

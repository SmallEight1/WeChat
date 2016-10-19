package com.wuxianedu.wechat.activity;

import java.util.ArrayList;
import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.utils.L;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * （抽象）Activity，所有活动的父类，继承AppCompatActivity
 */
public abstract class BaseActivity extends AppCompatActivity{
	//描述（全局变量）：标题的文字 ； 标题左右两边的图片； 滚动框（模拟网络请求）；活动集合（管理所有子类活动）
	private TextView title;
	private ImageView leftImage,rightImage;
	private ProgressDialog progressDialog;
	public  static List<Activity> list = new ArrayList<>();
	private long exitTime = 0;	
	private Toast mToast = null;  
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	L.e(list.size());    
    	if (keyCode == KeyEvent.KEYCODE_BACK) {
    		if (list.size()<2) { 
    			PressTwoexit();
    			return false;
    		}else{
    			 return super.onKeyDown(keyCode, event);
    		}
    	}    
        return false;    
    }
    
    /**
     * 连按两次退出，时间间隔为两秒
     */
    public void PressTwoexit() {				
        if ((System.currentTimeMillis() - exitTime) > 2000) {
        	showMessage("再按一次退出程序");
            exitTime = System.currentTimeMillis();
        } else {
            finishAll();
        }  	
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//将此活动添加到活动集合
		list.add(this);
		//作用：绑定视图 ----  描述：getContentView()为抽象方法  必须被子类活动重写
		setContentView(getContentView());	
		//初始化数据
		info();
		//描述：左边图片的点击事件 ,被子类重写(关闭当前活动)
		leftImage.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	/**
	 * 初始化数据
	 */
	private void info(){
		//作用：获得对象        描述：标题内的
		title = (TextView) findViewById(R.id.title_id);
		leftImage = (ImageView) findViewById(R.id.back_id);
		rightImage = (ImageView) findViewById(R.id.right_id);
	}
	/**
	 * 获取子类要绑定的视图
	 */
	public abstract int getContentView();
	
	/**
	 * 设置标题，（重写：参数可以是String 也可以是 R.id)
	 */
	protected void setTitleName(String str){
		title.setText(str);
	}
	protected void setTitleName(int resId){
		title.setText(resId);
	}
	/**
	 * 隐藏返回键，隐藏右边图标
	 */
	protected void hideLeftImage(){
		leftImage.setVisibility(View.GONE);
	}
	protected void hideRightImage(){
		rightImage.setVisibility(View.GONE);
	}
	
	/**
	 * 设置右侧图片和 监听器（在子类中重写）---->默认为不显示
	 */
	protected void showRightImage(int resId,OnClickListener onClickListener){
		rightImage.setVisibility(View.VISIBLE);
		rightImage.setImageResource(resId);
		rightImage.setOnClickListener(onClickListener);
	}
	

	/**
	 * Toast工具方法 可防止点击出现重复  TODO 该方法并不是完整的
	 */
	public void showMessage(Object msg){
		if (mToast == null) {  
            mToast = Toast.makeText(this,msg.toString(),Toast.LENGTH_SHORT); 
        } else {  
            mToast.setText(msg.toString());  
            mToast.setDuration(Toast.LENGTH_SHORT);  
        }   
        mToast.show(); 	
	}
	
	/**
	 * 跳转Activity
	 */
	public void jumpActivity(Class<?> cls){
		startActivity(new Intent(this,cls));
	}
	
	/**
	 * addActivity（）添加活动入集合，finishAll()结束所有活动，exit()，结束所有活动并杀死进程
	 * @param activity
	 */
	public  void addActivity(Activity activity){
		list.add(activity);
	}
	
	/**
	 * 清空活动结合，结束所有活动
	 */
	public  void finishAll(){
		for(Activity activity:list){
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
		list.clear();
	}
	
	/**
	 *清空活动结合，结束所有活动，并杀死进程 
	 */
	
	public  void exit(){
		for(Activity activity:list){
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
		list.clear();
		android.os.Process.killProcess(android.os.Process.myPid());
	}
	
	public void removeAcivity(Activity activity){
		list.remove(activity);
	}
	
	/**
	 * showProgressDialog()显示加载对话框，closeProgressDialog()关闭加载对话框
	 */
	public void showProgressDialog(){
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(this);
			progressDialog.setMessage("加载中，请稍后");
			progressDialog.setCancelable(false);	
		}
		if (progressDialog.isShowing()) {
			return;
		}	
		progressDialog.show();
	}	
	public void  closeProgressDialog(){
		if (progressDialog != null) {
			progressDialog.dismiss();
		}
	}
	
	
	public  void hideJianPan(View view){
		if (view !=null) {
			InputMethodManager imm =(InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(view.getWindowToken(),0);
		}		
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		removeAcivity(this);
	}
}

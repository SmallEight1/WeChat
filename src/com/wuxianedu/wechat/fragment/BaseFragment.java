package com.wuxianedu.wechat.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

public abstract class BaseFragment extends Fragment {
	
	/**
	 * 1，rootView对象在 Fragment对象创建（new ）时声明，在视图创建(onCreateView)时引用,（是全局变量）依赖于Fragment对象
	 * 
	 * 2， Fragment对象在（new）声明后，会一直存在，知道他依赖的活动销毁才销毁。里面的所有操作也会一起保存起来，比如（监听）
	 */
    private View rootView;
    private ProgressDialog progressDialog;
    /**
	 *  （优化） 判断是否存在对象，不存在就存在。
     */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView==null){	
        	//此方法绑定碎片视图
            rootView = getRootView(inflater,container,savedInstanceState);
           //此方法绑定所有视图控件
            builderView(rootView);
        }else{
            ViewGroup p = (ViewGroup) rootView.getParent();
            if (p != null) {
           /* 	removeAllViewsInLayout()和removeAllViews()都有移除子view的功能,
				但是removeAllViewsInLayout() 需要先测量当前的布局, 一旦调用该方法,
				只能移除已经自身布局中已计算好的所包含的子view. 相比而言, removeAllViews() 
				也调用了removeAllViewsInLayout(), 但是后面还调用了requestLayout(),
				这个方法是当View的布局发生改变会调用它来更新当前视图, 移除子View会更加彻底.
				所以除非必要, 还是推荐使用removeAllViews()这个方法.*/
                p.removeAllViewsInLayout();
            }
        }
        return rootView;
    }
	/**
	 * 抽象方法：在父类中已经被调用，但是这种调用没有方法体（即没有具体的实现），该方法在子类中重写，给出方法实现
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 * @return
	 */
    public abstract View getRootView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);
    /**
     * 抽象方法：通过该碎片视图，得到相对应的内部视图
     * @param rootView
     */
    public abstract void builderView(View rootView);

	public void showProgressDialog(){
		if (progressDialog == null) {
			progressDialog = new ProgressDialog(getContext());
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
	
	public void showMessage(Object msg){
		Toast.makeText(getContext(),msg.toString(),Toast.LENGTH_LONG).show();
	}

	protected void hideJianPan(){
		if (rootView !=null) {
			InputMethodManager imm =(InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(rootView.getWindowToken(),0);
		}		
	}
}

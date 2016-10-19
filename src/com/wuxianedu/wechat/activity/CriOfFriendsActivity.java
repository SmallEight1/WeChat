package com.wuxianedu.wechat.activity;

import java.util.ArrayList;
import java.util.List;

import org.xutils.x;
import org.xutils.image.ImageOptions;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.adapter.FriendsCriAdapter;
import com.wuxianedu.wechat.adapter.FriendsCriAdapter.ViewHolder;
import com.wuxianedu.wechat.asnycTask.UserFriendsCircleTack;
import com.wuxianedu.wechat.bean.Friends;
import com.wuxianedu.wechat.bean.JsUserFriendsCircle;
import com.wuxianedu.wechat.bean.UserInforOfUserLoginAndUserReg;
import com.wuxianedu.wechat.listener.FriendsCrilListener;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.FileLocalCache;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * 朋友圈活动，继承BaseActivity
 * @author Administrator
 *
 */
public class CriOfFriendsActivity extends BaseActivity {
	private ListView listView;
	private List<Friends> listFriends;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//设置标题文字
		setTitleName("朋友圈");
		//设置右边View 的图片资源 和监听事件
		showRightImage(R.drawable.icon_talk,new OnClickListener() {
			@Override
			public void onClick(View v) {
			showMessage("点击");
			}
		});
		initialize();
	}
	
	@Override
	public int getContentView() {
		return R.layout.activity_cri_of_friends;
	}
	
	/**
	 * 初始化布局信息
	 */
	private void initialize(){	
		
		new UserFriendsCircleTack(this){
			
			public void setView(JsUserFriendsCircle result) {
				listView = (ListView) findViewById(R.id.news_ls_id);	
				listFriends = result.getListFriends();
				FriendsCriAdapter adapter = new FriendsCriAdapter(CriOfFriendsActivity.this,
						listFriends);	
				listView.setAdapter(adapter);
				adapter.setOnItemListener(new MyFriedsCriListener());
				setHeadLayout(listView,result);
			};
		}.execute();
		
	}
	
	/**
	 * 设置头部布局
	 */	
	private void setHeadLayout(ListView listView,JsUserFriendsCircle result){
		//得到朋友圈lisView头部视图的对象   和他的子控件的对象
		View headview =getLayoutInflater().inflate(R.layout.item_head_friofcriback,listView,false);
		ImageView backImag = (ImageView) headview.findViewById(R.id.backImag_id);
		ImageView head = (ImageView) headview.findViewById(R.id.head_id);
		TextView userName = (TextView) headview.findViewById(R.id.userName_id);

		//获取用户信息（序列化对象的读出，从sd卡中）
		UserInforOfUserLoginAndUserReg userInfor = (UserInforOfUserLoginAndUserReg) FileLocalCache.getSerializableData
				(CriOfFriendsActivity.this,Constant.USER_INFOR);

		ImageOptions.Builder builder = new ImageOptions.Builder();
		builder.setCircular(true);		
		/**
		 * 此方法加载网络图片  视图对象必须为ImageView ，不能为RoundImageView,不然会出错（类型转换）
		 * TODO 该方法的好处 为什么不能使用 x.image() commbrak的那个方法
		 */
		//圆形网络图片的加载    第一个参数要设置的视图对象          第二个参数网络地址（字符串）       第三个参数   设置
		ImageOptions imageOptions = builder.build();			
		x.image().bind(head,userInfor.getHead(),imageOptions);	
		//普通的网络图片的加载  第一个参数要设置的视图对象          第二个参数网络地址（字符串）
		x.image().bind(backImag,result.getBackGround());	
		
		userName.setText(userInfor.getUserName());
		//添加listView的头部视图          第一参数：头部视图对象             第三个参数：是否可被点击
		listView.addHeaderView(headview, null, false);
		//listView.addFooterView(v);     添加尾部视图
		//不显示头部视图的分隔线
		listView.setHeaderDividersEnabled(false);
	}
	
	/**
	 * 实现监听
	 * @author Administrator
	 *
	 */
	class MyFriedsCriListener  implements FriendsCrilListener{

		@Override
		public void onItemClick(ViewHolder viewHolder, int position) {
			//意图：图片活动
			Intent intent = new Intent(CriOfFriendsActivity.this,ImageBrowseActivity.class);
			Bundle bundle = new Bundle();
			//传递图片集合     类型为 AarrayLiset
			bundle.putStringArrayList(Constant.IMAGE_LIST, 
					(ArrayList<String>) listFriends.get(position).getListImages());		
			bundle.putInt(Constant.POSITION, viewHolder.j);
			intent.putExtras(bundle);
			startActivity(intent);
		}	
	}
}


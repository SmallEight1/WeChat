package com.wuxianedu.wechat.adapter;

import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.Friends;
import com.wuxianedu.wechat.listener.FriendsCrilListener;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.utils.SetTime;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * TODO 朋友圈的适配器
 * @author Administrator
 */
public class FriendsCriAdapter extends BaseAdapter{
	private Context context;
	private List<Friends> listFriends;
	private FriendsCrilListener friendsCrilListener;
	
	public FriendsCriAdapter(Context context,List<Friends> listFriends) {
		this.context = context;
		this.listFriends = listFriends;
	}
	@Override
	public int getCount() {
		return listFriends.size();
	}

	@Override
	public Friends getItem(int position) {
		return listFriends.get(position);
	}

	@Override
	public long getItemId(int position) {	
		return position;
	}

	public void setOnItemListener(FriendsCrilListener friendsCrilListener){
		this.friendsCrilListener = friendsCrilListener;
	}
	/**
	 * 返回view的值
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView  = LayoutInflater.from(context).
					inflate(R.layout.item_listview_friendsofcricle, parent,false);
			convertView.setTag(new ViewHolder(convertView));
		}	
		Initialized((ViewHolder) convertView.getTag(), position);		
		return convertView;
	}
	
	private void Initialized(ViewHolder viewHodler,int position){
		viewHodler.setPostion(position);
		Friends friends = getItem(position);
		//设置用户名，内容
		viewHodler.userName.setText(friends.getUserName());
		viewHodler.content.setText(friends.getContent());		
		//得到时间字符串,设置时间
		String timeStr = friends.getTime();
		viewHodler.time.setText(SetTime.getData(timeStr));	
		SetImage.SetHeadImage(viewHodler.head, friends.getHead());
		//设置动态图片
		for(int i = 0;i<9;i++){	
			//判断图片数组的长度与i的大小，把小于i的部分显示，大于i的部分隐藏
			if (i<friends.getListImages().size()) {
				viewHodler.imageViews[i].setVisibility(View.VISIBLE);						
				SetImage.SetRecImage(viewHodler.imageViews[i], friends.getListImages().get(i));
			}else {
				viewHodler.imageViews[i].setVisibility(View.GONE);	
			}
		}	
	
		
		
	}
	
	public class ViewHolder implements OnClickListener{
		TextView userName,time,content;
		ImageView imageViews[];
		RoundImageView head;
		private int position;
		public int j;
		private int[] intR ={R.id.img1,R.id.img2,R.id.img3,R.id.img4,R.id.img5,
				R.id.img6,R.id.img7,R.id.img8,R.id.img9};
		
		public ViewHolder(View convertView) {
			userName= (TextView) convertView.findViewById(R.id.userName_id);
			time= (TextView) convertView.findViewById(R.id.time_id);
			content= (TextView) convertView.findViewById(R.id.content_id);
			head= (RoundImageView) convertView.findViewById(R.id.head_id);
			imageViews = new ImageView[9];			
			for(int i =0 ;i<imageViews.length;i++){	
				imageViews[i]= (ImageView) convertView.findViewById(intR[i]);				
			}	
			for(int i =0 ;i<imageViews.length;i++){			
				imageViews[i].setOnClickListener(this);		
			}
		}
		
		private void setPostion(int position){
			this.position = position;
		}

		@Override
		public void onClick(View v) {	
			for(int i = 0;i < intR.length;i++){
				if(v.getId() == intR[i] ){
					j = i;
					friendsCrilListener.onItemClick(this, position);	
				}						
			}				
		}
		
	}	
}

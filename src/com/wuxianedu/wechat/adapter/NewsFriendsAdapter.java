package com.wuxianedu.wechat.adapter;

import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.ContactsOfAddFriends;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class NewsFriendsAdapter extends BaseAdapter {
	private Context context;
	private List<ContactsOfAddFriends> listNewsFriends;
	
	public NewsFriendsAdapter(Context context,List<ContactsOfAddFriends> listNewsFriends) {
		this.context = context;
		this.listNewsFriends = listNewsFriends;
	}
	@Override
	public int getCount() {		
		return listNewsFriends.size();
	}

	public void setList(List<ContactsOfAddFriends> listNewsFriends){
		this.listNewsFriends = listNewsFriends;
		notifyDataSetChanged();
	}
	
	@Override
	public ContactsOfAddFriends getItem(int position) {
		return listNewsFriends.get(position);
	}

	@Override
	public long getItemId(int position) {	
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item_listview_newfriends, parent,false);	
			convertView.setTag(new ViewHolder(convertView));
		}
		Initialized((ViewHolder)convertView.getTag(),position);
		return convertView;
	}
	
	@SuppressWarnings("deprecation")
	private void Initialized(ViewHolder viewHolder,int position){
		ContactsOfAddFriends contacts = getItem(position);
		//设置姓名
		viewHolder.name.setText(contacts.getName());
		//导入圆角图片 头像		
		SetImage.SetHeadImage(viewHolder.head, contacts.getHead());	
		//设置是是否已经接受
		if (contacts.isAdd()) {	
			viewHolder.isAdd.setText("已接受");
			viewHolder.isAdd.setBackground(context.getResources().getDrawable(R.drawable.accept_shap_ck));
			viewHolder.isAdd.setClickable(false);
		}else if(!contacts.isAdd()){	
			viewHolder.isAdd.setText("接受");
			viewHolder.isAdd.setBackground(context.getResources().getDrawable(R.drawable.accept_shap_nor));
			viewHolder.isAdd.setClickable(true);
		}
		//监听事件的复用
		viewHolder.isAdd.setOnClickListener(viewHolder.onClickListener);
		viewHolder.isAdd.setTag(position);
	}
	
	class ViewHolder{
		TextView name;
		RoundImageView head;
		Button isAdd;
		OnClickListener onClickListener;
		
		public ViewHolder(View convertView) {
			name =(TextView) convertView.findViewById(R.id.name_id);
			head = (RoundImageView) convertView.findViewById(R.id.phto_id);
			isAdd = (Button) convertView.findViewById(R.id.isAdd_id);	
			//设置监听器  复用
			onClickListener = new OnClickListener() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {					
					int index = (int) v.getTag();
					isAdd.setText("已接受");
					isAdd.setClickable(false);
					isAdd.setBackground(context.getResources().getDrawable(R.drawable.accept_shap_ck));
					listNewsFriends.get(index).setAdd(true);					
				}
			};		
		}
	}
}


package com.wuxianedu.wechat.adapter;

import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.ContactsOfContact;
import com.wuxianedu.wechat.utils.SetImage;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * @ClassName:GroupChatHlvAdapter 
 */
public class GroupChatHlvAdapter extends BaseAdapter{
	private Context context;
	private List<ContactsOfContact> groupList;
	
	public GroupChatHlvAdapter(Context context,List<ContactsOfContact> groupList) {
		this.context = context;
		this.groupList = groupList;
	}
	
	public void setList(List<ContactsOfContact> groupList) {
		this.groupList = groupList;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {
		return groupList.size();
	}

	@Override
	public ContactsOfContact getItem(int position) {
		return groupList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@SuppressLint("InflateParams")
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			/*
			 * 得到视图convertView的方法   
			 * TODO 这里不可以使用  convertView = LayoutInflater.from(context).inflate
			 *	(R.layout.item_h_listview_group_chat, parent,false);  
			 *	两者的区别？
			 */
			convertView = LayoutInflater.from(context).inflate
					(R.layout.item_h_listview_group_chat,null);
			convertView.setTag(new ViewHolder(convertView));
		}
		Initialized((ViewHolder)convertView.getTag(),position);	
		return convertView;
	}

	private void Initialized(ViewHolder viewHolder,int position){
		//设置透明度 
		viewHolder.imageView.setAlpha(getItem(position).getAlpha());
		SetImage.SetHeadImage(viewHolder.imageView,getItem(position).getHead());
	}
	
	class ViewHolder{
		ImageView imageView;
		
		public ViewHolder(View convertView) {
			imageView = (ImageView) convertView.findViewById(R.id.group_chat_hlv_image);
		}
	} 
			
}




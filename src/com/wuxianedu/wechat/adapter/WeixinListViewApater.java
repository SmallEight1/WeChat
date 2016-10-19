package com.wuxianedu.wechat.adapter;

import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.ContactsOfWeChat;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.utils.SetTime;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class WeixinListViewApater extends BaseAdapter{
	private Context context = null;
	private List<ContactsOfWeChat> list = null;

	public WeixinListViewApater(Context context, List<ContactsOfWeChat> list) {
		this.context = context;
		this.list = list;
	}
	@Override
	public int getCount() {		
		return list.size();
	}
	
	@Override
	public ContactsOfWeChat getItem(int position) {		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	public void setList(List<ContactsOfWeChat> list) {
		this.list = list;
		notifyDataSetChanged();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item_listview_weixin, parent,false);		
			convertView.setTag(new ViewHolder(convertView));
		}
		Initialized((ViewHolder)convertView.getTag(),position);	
		return convertView;
	}
	
	private void Initialized(ViewHolder viewHolder,int position){
		ContactsOfWeChat contacts = getItem(position);
		viewHolder.name.setText(contacts.getName());
		viewHolder.messaged.setText(contacts.getLastStr());
		String timeStr = contacts.getLastTime();
		viewHolder.lastTime.setText(SetTime.getData(timeStr));
		if (position == 0) { //第一个item		
			viewHolder.head.setImageResource(R.drawable.icon_public);
		}else{
			SetImage.SetHeadImage(viewHolder.head, contacts.getHead());		
		}
		if (contacts.getNewsNum()!=0) { //如果对象的 消息数 不等于零
			viewHolder.messageNum.setVisibility(View.VISIBLE);
			viewHolder.messageNum.setText(contacts.getNewsNum()+"");
		}else {
			viewHolder.messageNum.setVisibility(View.GONE);
		}
	}
	
	class ViewHolder{
		TextView name,messaged,lastTime,messageNum;
		ImageView head;
		
		public ViewHolder(View convertView) {
			name =(TextView) convertView.findViewById(R.id.name_id);
			messaged =(TextView) convertView.findViewById(R.id.message_id);
			lastTime =(TextView) convertView.findViewById(R.id.time_id);
			head = (RoundImageView) convertView.findViewById(R.id.phto_id);
			messageNum = (TextView) convertView.findViewById(R.id.messageNum_id);
		}
	}
	
}

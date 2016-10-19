package com.wuxianedu.wechat.adapter;

import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.Subscribes;
import com.wuxianedu.wechat.utils.SetTime;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 订阅号
 * @author Administrator
 *
 */
public class SubscribeAdapter extends BaseAdapter{
	private Context context;
	private List<Subscribes> listSubscribes;
	
	public SubscribeAdapter(Context context, List<Subscribes> listSubscribes) {
		this.context = context;
		this.listSubscribes = listSubscribes;
	}
	@Override
	public int getCount() {
		return listSubscribes.size();
	}

	@Override
	public Subscribes getItem(int position) {	
		return listSubscribes.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).
					inflate(R.layout.item_listview_subsrcribe, parent,false);		
			convertView.setTag(new ViewHolder(convertView));
		} 
		Initialized((ViewHolder)convertView.getTag(),position);
		
		return convertView;	
	}
	
	private void Initialized(ViewHolder viewholder,int position){
		Subscribes subscribes = getItem(position);	
		//设置 信息 谁知消息
		viewholder.name.setText(subscribes.getName());
		viewholder.messaged.setText(subscribes.getLastStr());
		//对时间进行操作
		String timeStr = subscribes.getLastTime();
		viewholder.lastTime.setText(SetTime.getData(timeStr));
		//设置头像 =圆形
		viewholder.head.setImageResource(R.drawable.icon_public);		
		//设置圆形图片上的文本
		if (subscribes.getNewsNum()!=0) {
			viewholder.messageNum.setVisibility(View.VISIBLE);
			viewholder.messageNum.setText(subscribes.getNewsNum()+"");
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

package com.wuxianedu.wechat.adapter;


import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.SubSribDetai;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.utils.SetTime;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
/**
 * 订阅号详情
 * @author Administrator
 *
 */
public class SubscribDetailsAdapter extends BaseAdapter {
	private Context context;
	private List<SubSribDetai> listsubSribDetails;
	
	public SubscribDetailsAdapter(Context context,List<SubSribDetai> listsubSribDetails) {
		this.context = context;
		this.listsubSribDetails = listsubSribDetails;
	}
	
	@Override
	public int getCount() {	
		return listsubSribDetails.size();
	}

	@Override
	public SubSribDetai getItem(int position) {		
		return listsubSribDetails.get(position);
	}

	@Override
	public long getItemId(int position) {	
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item_listview_subscrib_details ,parent,false);	
			convertView.setTag(new Viewholder(convertView));
		}
		Initialized((Viewholder)convertView.getTag(),position);
		return convertView;
	}

	private void Initialized(Viewholder viewholder,int position){
		//得到对象
		SubSribDetai subSribDetail = getItem(position);
		//设置标题
		viewholder.title.setText(subSribDetail.getTitle());
		//设置内容
		viewholder.content.setText(subSribDetail.getContent());
		//设置时间（头部）
		//得到表示时间的字符串
		String  timeStr = subSribDetail.getTime();
		viewholder.time.setText(SetTime.getTime(timeStr));
		//设置日期 
		viewholder.data.setText(SetTime.getDate(timeStr));	
		//设置图片
		//设置初始图片 解决复用问题
		SetImage.SetHeadImage(viewholder.image, subSribDetail.getImage());
	}
	
	class Viewholder{
		TextView title,content,time,data;
		ImageView image;
		
		public Viewholder(View convertView) {
			title = (TextView) convertView.findViewById(R.id.name_id);
			time = (TextView) convertView.findViewById(R.id.time);
			content = (TextView) convertView.findViewById(R.id.content_id);
			image = (ImageView) convertView.findViewById(R.id.photo_id);
			data = (TextView) convertView.findViewById(R.id.data_id);
		}
	}
}

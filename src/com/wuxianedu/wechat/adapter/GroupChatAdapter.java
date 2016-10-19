
package com.wuxianedu.wechat.adapter;

import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.ContactsOfContact;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.SectionIndexer;
import android.widget.TextView;
/**
 * @ClassName:GroupChatAdapter 
 */
public class GroupChatAdapter extends BaseAdapter implements SectionIndexer {
	private Context context;
	private List<ContactsOfContact> list;
	
	public GroupChatAdapter(Context context,List<ContactsOfContact> list) {
		this.context = context;
		this.list = list;
	}
	

	public void setList(List<ContactsOfContact> list) {
		this.list = list;
		notifyDataSetChanged();
	}	
	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null ) {
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item_listview_gounp_chat, parent,false);
			convertView.setTag(new ViewHolder(convertView));
		}
		Initialized((ViewHolder) convertView.getTag(), position);		
		return convertView;
	}

	private void Initialized(ViewHolder viewHodler,int position){
		ContactsOfContact contact = list.get(position);	
		String string = contact.getNameFirst();
		if (position != 0) {   //下表不为0		
			String befor = list.get(position-1).getNameFirst();  //获取前一个对象的名字首字母
			if (string.equals(befor)) {  //比较相同时
				viewHodler.lable.setVisibility(View.GONE);
			}else{  //不同时
				viewHodler.lable.setVisibility(View.VISIBLE);
				viewHodler.lable.setText(string);
			}
		}else{  //为0
			viewHodler.lable.setVisibility(View.VISIBLE);
			viewHodler.lable.setText(string);
		}		
		viewHodler.name.setText(contact.getName());
		SetImage.SetHeadImage(viewHodler.head, contact.getHead());		
		//要根据JavaBen中的boolean来设置,否则会复用前面的视图  ---- list的维护在activity中
		viewHodler.add.setChecked(contact.isAddGroup());
	}
	
	class ViewHolder{
		TextView lable,name;
		RoundImageView head;
		CheckBox add;
		
		public ViewHolder(View convertView) {
			lable = (TextView) convertView.findViewById(R.id.contact_lable);
			name = (TextView) convertView.findViewById(R.id.contact_content);
			head = (RoundImageView) convertView.findViewById(R.id.contact_head);
			add = (CheckBox) convertView.findViewById(R.id.ck_id);
		}
	}
	
	@Override
	public Object[] getSections() {	
		return null;
	}
	
	//此时的position为选中第几个字母，要返回一个listView的位置
	@Override
	public int getPositionForSection(int sectionIndex) {
		//循环遍历集合
		for (int i = 0; i < list.size(); i++) {	
			//得到了拼音	
			int j = list.get(i).getNameFirst().charAt(0);
			if (sectionIndex == j) {
				return i;
			}
		}
		return 0;
	}

	
	@Override
	public int getSectionForPosition(int position) {
		return 0;
	}
}


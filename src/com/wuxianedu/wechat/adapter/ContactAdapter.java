package com.wuxianedu.wechat.adapter;

import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.ContactsOfContact;
import com.wuxianedu.wechat.listener.ContactListener;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

public class ContactAdapter extends BaseAdapter implements SectionIndexer {
	private Context context;
	private List<ContactsOfContact> list;
	private ContactListener contactListener;
	
	public ContactAdapter(Context context,List<ContactsOfContact> list) {
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
	public ContactsOfContact getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {	
		return position;
	}
	
	public void setOnItemListener(ContactListener contactListener){
		this.contactListener = contactListener;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context)
					.inflate(R.layout.item_listview_contact, parent,false);			
			convertView.setTag(new ViewHodler(convertView));
		}
		Initialized((ViewHodler) convertView.getTag(), position);
		return convertView;
	}
	
	private void Initialized(ViewHodler viewHodler,int position){
		viewHodler.setPostion(position);
		ContactsOfContact contact =  list.get(position);
		//设置他的头像
		SetImage.SetHeadImage(viewHodler.headView, contact.getHead());
		//设置他的名字
		viewHodler.nameTv.setText(contact.getName());	
		//设置标签
		String first = contact.getNameFirst();
		if(position != 0){
			//获取上一个联系人的姓名首字母
			String nextFirst = list.get(position-1).getNameFirst();
			//和当前联系人进行比较   如果相同隐藏  如果不同 显示
			if(!nextFirst.equals(first)){
				viewHodler.lableTv.setVisibility(View.VISIBLE);
				viewHodler.lableTv.setText(first);
			}else{
				viewHodler.lableTv.setVisibility(View.GONE);
			}
		}else{  //当为第一个的时候直接设置
			viewHodler.lableTv.setVisibility(View.VISIBLE);
			viewHodler.lableTv.setText(first);
		}
	}
	
	public class ViewHodler implements OnClickListener {
		TextView lableTv,nameTv;
		RoundImageView headView;	
		private int position;
		
		public ViewHodler(View convertView) {
			if (contactListener != null) {
				convertView.setOnClickListener(this);
			}		
			headView = (RoundImageView) convertView.findViewById(R.id.contact_head);
			lableTv = (TextView) convertView.findViewById(R.id.contact_lable);
			nameTv = (TextView) convertView.findViewById(R.id.contact_content);	
		}

		private void setPostion(int position){
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.item_contact:
				contactListener.onItemClick(this, position);
				break;
			}
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


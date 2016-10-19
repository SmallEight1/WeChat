package com.wuxianedu.wechat.activity;

import java.util.ArrayList;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.ContactsOfContact;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class FriedDetailsActivity extends BaseActivity implements OnClickListener{
	private TextView name,weChatnum,address,signature;
	private ContactsOfContact contact;
	private RoundImageView head;
	private ImageView[] photos = new ImageView[3];
	int[] intR = {R.id.photo1_id,R.id.photo2_id,R.id.photo3_id};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleName("详细资料");
		showRightImage(R.drawable.icon_more, new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showMessage("点击事件");
			}
		});		
	    initialize();		
	}

	@Override
	public int getContentView() {
		return R.layout.activity_fried_data;
	}
	
	//设置简单控件的属性
	private void initialize(){
		//得到对象
		name = (TextView) findViewById(R.id.name_id);
	    weChatnum = (TextView) findViewById(R.id.weChatnum_id);
		address = (TextView) findViewById(R.id.address_id);
		signature = (TextView) findViewById(R.id.signature_id);
		head = (RoundImageView) findViewById(R.id.head_id);
		setView();
	}
	
	private void setView(){
		Intent intent =getIntent();
	    contact =  (ContactsOfContact) intent.getSerializableExtra(Constant.FRIEDDATA);	   
		//设置姓名
		name.setText(contact.getName());
		//设置微信号
		weChatnum.setText(contact.getWeCode()+"");
		//设置地址
		address.setText(contact.getArea());
		//设置个人签名
	    signature.setText(contact.getAutograph());
	    SetImage.SetHeadImage(head, contact.getHead());
	    setPhotos();
	}
	
	/**
	 * 设置个人相册
	 */
	private void setPhotos(){
		for (int i = 0; i < intR.length; i++) {
			 photos[i] =(ImageView) findViewById(intR[i]);
		}
		//设置个人相册
		for(int i = 0;i<3;i++){	
			//判断图片数组的长度与i的大小，把小于i的部分显示，大于i的部分隐藏
			if (i<contact.getListImages().size()) {
				//显示小于部分
				photos[i].setVisibility(View.VISIBLE);
				photos[i].setOnClickListener(this);
				SetImage.SetRecImage(photos[i], contact.getListImages().get(i));
			}else {
				//隐藏大于部分
				photos[i].setVisibility(View.GONE);	
			}
		}
	}

	@Override
	public void onClick(View v) {
		Intent intent = new Intent(FriedDetailsActivity.this,ImageBrowseActivity.class);
		Bundle bundle = new Bundle();
		//传递图片集合     类型为 AarrayLiset
		bundle.putStringArrayList(Constant.IMAGE_LIST, 
				(ArrayList<String>) contact.getListImages());	
		for(int j =0;j<3;j++){
			if (v.getId() == intR[j]) {
				bundle.putInt(Constant.POSITION, j);
			}
		}
		intent.putExtras(bundle);
		startActivity(intent);	
	}
}

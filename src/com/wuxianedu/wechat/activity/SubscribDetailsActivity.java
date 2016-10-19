package com.wuxianedu.wechat.activity;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.adapter.SubscribDetailsAdapter;
import com.wuxianedu.wechat.asnycTask.SubscribDetailsTask;
import com.wuxianedu.wechat.bean.JsSubscribDetails;
import android.os.Bundle;
import android.widget.ListView;

public class SubscribDetailsActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleName("订阅号详情");
		//异步操作
		new SubscribDetailsTask(this) {
			
			@Override
			public void setView(JsSubscribDetails result) {
				//  listView设置适配器
				ListView listView = (ListView)findViewById(R.id.lv_id);
				SubscribDetailsAdapter apater = new 
						SubscribDetailsAdapter(SubscribDetailsActivity.this, 
				result.getListsubSribDetails());
				listView.setAdapter(apater);
			}
		}.execute();
	}

	@Override
	public int getContentView() {
		return R.layout.activity_subscrib_details;
	}		
}

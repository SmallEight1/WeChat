package com.wuxianedu.wechat.activity;

import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.adapter.SubscribeAdapter;
import com.wuxianedu.wechat.asnycTask.SubsrcribeTask;
import com.wuxianedu.wechat.bean.JsSubscribe;
import android.os.Bundle;
import android.widget.ListView;

public class SubscribeActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setTitleName("订阅号");
		//异步操作
		new SubsrcribeTask(this){
			public void setView(JsSubscribe result) {
				//订阅号  listView设置适配器
				ListView listView = (ListView)findViewById(R.id.sub_listView_id);
				SubscribeAdapter apater = new SubscribeAdapter(SubscribeActivity.this, 
						result.getListsubscribe());
				listView.setAdapter(apater);
				listView.setOnItemClickListener(this);
			};
		}.execute();
	}

	@Override
	public int getContentView() {
		return R.layout.activity_subscribe;
	}	
}

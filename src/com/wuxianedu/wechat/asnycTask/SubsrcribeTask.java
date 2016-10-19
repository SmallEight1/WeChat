package com.wuxianedu.wechat.asnycTask;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import com.wuxianedu.wechat.activity.SubscribDetailsActivity;
import com.wuxianedu.wechat.activity.SubscribeActivity;
import com.wuxianedu.wechat.bean.JsSubscribe;
import com.wuxianedu.wechat.bean.Subscribes;
import com.wuxianedu.wechat.utils.AssetsUtil;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public abstract class SubsrcribeTask extends AsyncTask<Void,Void,JsSubscribe> implements OnItemClickListener{
	private SubscribeActivity subscribeActivity;
	
	public SubsrcribeTask(SubscribeActivity subscribeActivity){
		this.subscribeActivity =subscribeActivity;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {	
		subscribeActivity.jumpActivity(SubscribDetailsActivity.class);
	}

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		subscribeActivity.showProgressDialog();
	}
	
	@Override
	protected JsSubscribe doInBackground(Void... params) {
		//获取json字符串
		String jsonStr = AssetsUtil.getAssets(subscribeActivity,"subscribe.json");
		try {
			Thread.sleep(1000L);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}	
		//解析json  返回	
		return getSubscribe(jsonStr);
	}
	
	@Override
	protected void onPostExecute(JsSubscribe result) {
		subscribeActivity.closeProgressDialog();
		int  status = result.getStatus();
		if(status !=0){ //如果为错误状态吗
			subscribeActivity.showMessage("错误："+result.getMessage());
			return;
		}		
		setView(result);
	}
	
	public abstract void setView(JsSubscribe result);
	/**
	 * 解析json文件
	 */
	private JsSubscribe getSubscribe(String jsonStr){
		try {
			JSONObject jsonObject = new JSONObject(jsonStr);	
			//三个参数
			int status = jsonObject.getInt("status");
			String message = jsonObject.getString("message");
			JSONArray jsonArray = jsonObject.getJSONArray("subscribe");	
			List<Subscribes>  listsubscribe = new ArrayList<>();
			for (int i = 0; i < jsonArray.length(); i++) {
				Subscribes subscribes = new Subscribes();
				//五个参数
				String name = jsonArray.getJSONObject(i).getString("name");	
				String lastStr = jsonArray.getJSONObject(i).getString("lastStr");
				String lastTime = jsonArray.getJSONObject(i).getString("lastTime");		
				long weCode = jsonArray.getJSONObject(i).getLong("weCode");
				int newsNum = jsonArray.getJSONObject(i).getInt("newsNum");				
				subscribes.setName(name);
				subscribes.setLastStr(lastStr);
				subscribes.setLastTime(lastTime);
				subscribes.setWeCode(weCode);
				subscribes.setNewsNum(newsNum);
				listsubscribe.add(subscribes);
			}			
			JsSubscribe subscribe = new JsSubscribe();
			subscribe.setStatus(status);
			subscribe.setMessage(message);
			subscribe.setListsubscribe(listsubscribe);
			return subscribe;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;	
	}
}

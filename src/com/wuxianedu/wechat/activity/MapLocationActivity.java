package com.wuxianedu.wechat.activity;

import java.util.List;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;
import com.wuxianedu.wechat.R;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;

public class MapLocationActivity extends Activity {
	private  MapView mv;
	private BaiduMap baidMap;
	private LocationManager locationManager;
	private String  provider;
	private boolean isFirstLocate = true;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_map_location);
		mv = (MapView) findViewById(R.id.mv);
		baidMap = mv.getMap();
		baidMap.setMyLocationEnabled(true);
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		//获取所有可用的位置提供器
		List<String>  providerList = locationManager.getProviders(true);
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;  
		}else if (providerList.contains(LocationManager.NETWORK_PROVIDER)){
			provider = LocationManager.NETWORK_PROVIDER; 
		}else{
			//当没有位置提供器时，弹出Toast提示用户
			Toast.makeText(this, "没有位置提供器", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Location location = locationManager.getLastKnownLocation(provider);
		if (location != null) {
			navigateTo(location);
		}
		
		locationManager.requestLocationUpdates(provider, 5000, 1, locationListener);
	}
	
	
	private void navigateTo(Location location){
		if (isFirstLocate) {
			//经纬
			LatLng ll = new LatLng(location.getLatitude(),location.getLongitude());
			MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(ll);
			baidMap.animateMapStatus(update);
			update = MapStatusUpdateFactory.zoomTo(16f);
			baidMap.animateMapStatus(update);
			isFirstLocate = false;
		}
		
		MyLocationData.Builder locationBuilder = new MyLocationData.Builder();
		locationBuilder.latitude(location.getLatitude());
		locationBuilder.longitude(location.getLongitude());
		MyLocationData locationData  = locationBuilder.build();
		baidMap.setMyLocationData(locationData);
	}
	
	LocationListener locationListener = new LocationListener() {
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {	
		}
		
		@Override
		public void onProviderEnabled(String provider) {
	
		}
		
		@Override
		public void onProviderDisabled(String provider) {
		}
		
		@Override
		public void onLocationChanged(Location location) {
			//更新当前设置的位置信息
			if (isFirstLocate) {
				navigateTo(location);
			}
		}
	};
	 @Override
	protected void onResume() {
	     super.onResume();
	     mv.onResume();
	 }
	
       @Override

     protected void onPause() {
          super.onPause();
          mv.onPause();
 
     }
	
      @Override
      protected void onDestroy() {
           super.onDestroy();
	       mv.onDestroy();
	       baidMap.setMyLocationEnabled(false);
	       if (locationListener != null) {
	    	   locationManager.removeUpdates(locationListener);
	       }
       }
}

package com.wuxianedu.wechat.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SetTime {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	private static SimpleDateFormat formatTime = new SimpleDateFormat("yyyy年MM月dd HH:mm:ss"); 
	private static SimpleDateFormat formatData = new SimpleDateFormat("MM月dd日"); 
	private static SimpleDateFormat formathm = new SimpleDateFormat("hh:mm");
	
	//这两个方法只在联系人详情中调用
	public static String getTime(String timeStr){
		Date date = null;
		try {
			 date = format.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatTime.format(date);
	}
	
	public static String getDate(String timeStr){
		Date date = null;
		try {
			 date = format.parse(timeStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return formatData.format(date);
	}
	
	public static String getNowTime(){
		Calendar calendar = Calendar.getInstance(); 
		Date dateNow = calendar.getTime();
		return format.format(dateNow);
	}
	
	/**
	 * 计算时间 ()
	 */
	public static String  getData(String timeStr){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		SimpleDateFormat formatTime = new SimpleDateFormat("HH:mm:ss"); 	 
		Date date = null;	
		Date dateTime = null;
		try {
			//得到发送的时间（Date对象）
			date = format.parse(timeStr);
			//得到当前时间（Date对象）
			Calendar calendar = Calendar.getInstance();
			Date dateNow = calendar.getTime();
			//得到这两兑现该时间相差的天数
			int i = daysBetween(date,dateNow);
			if (i==0) {    //是否等于0 则是是否为今天
				String dataString ="今天  "+formatTime.format(date);
				return dataString;
			}else{
				String dataString1 = i+"天前  "+formatTime.format(date);	
				return	dataString1;
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param 较小时间
	 * @param 较大时间
	 * @return 相差的天数
	 * @throws ParseException
	 */
    public static int daysBetween(Date smdate,Date bdate) throws ParseException    
    {    
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");  
        smdate=sdf.parse(sdf.format(smdate));  
        bdate=sdf.parse(sdf.format(bdate));  
        Calendar cal = Calendar.getInstance();    
        cal.setTime(smdate);    
        long time1 = cal.getTimeInMillis();                 
        cal.setTime(bdate);    
        long time2 = cal.getTimeInMillis();         
        long between_days=(time2-time1)/(1000*3600*24);         
       return Integer.parseInt(String.valueOf(between_days));           
    }  
}

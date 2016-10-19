package com.wuxianedu.wechat.bean;

import java.io.Serializable;

public class Subscribes implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1180237209163500518L;
	private String name,lastStr,lastTime;
	private long weCode;
	private int newsNum;
	public String getName() {
		return name;
	}
	@Override
	public String toString() {
		return "Subscribe_Subscribes [name=" + name + ", lastStr=" + lastStr + ", lastTime=" + lastTime + ", weCode="
				+ weCode + ", newsNum=" + newsNum + "]";
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastStr() {
		return lastStr;
	}
	public void setLastStr(String lastStr) {
		this.lastStr = lastStr;
	}
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public long getWeCode() {
		return weCode;
	}
	public void setWeCode(long weCode) {
		this.weCode = weCode;
	}
	public int getNewsNum() {
		return newsNum;
	}
	public void setNewsNum(int newsNum) {
		this.newsNum = newsNum;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	 
}

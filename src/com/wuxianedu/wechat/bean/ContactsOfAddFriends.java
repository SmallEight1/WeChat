package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

public class ContactsOfAddFriends implements Serializable{
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -9093770413457034270L;
	
	private String name,head,area,autograph,lastStr,lastTime;
	long weCode;
	int newsNum;
	boolean isAdd;
	private List<String> listImages;
	private String nameFirst; 	   //名字首字母
	private String fristNameFirst;
	public String getArea() {
		return area;
	}
	@Override
	public String toString() {
		return "Contact_Contacts [name=" + name + ", head=" + head + ", area=" + area + ", weCode=" + weCode
				+ ", autograph=" + autograph + ", lastStr=" + lastStr + ", newsNum=" + newsNum + ", lastTime="
				+ lastTime + ", isAdd=" + isAdd + ", listImages=" + listImages + ", nameFirst=" + nameFirst + "]";
	}
	public void setArea(String area) {
		this.area = area;
	}

	public String getAutograph() {
		return autograph;
	}
	public void setAutograph(String autograph) {
		this.autograph = autograph;
	}
	public String getLastStr() {
		return lastStr;
	}
	public void setLastStr(String lastStr) {
		this.lastStr = lastStr;
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
	public String getLastTime() {
		return lastTime;
	}
	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}
	public boolean isAdd() {
		return isAdd;
	}
	public void setAdd(boolean isAdd) {
		this.isAdd = isAdd;
	}
	public List<String> getListImages() {
		return listImages;
	}
	public void setListImages(List<String> listImages) {
		this.listImages = listImages;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getNameFirst() {
		return nameFirst;
	}
	public void setNameFirst(String nameFirst) {
		this.nameFirst = nameFirst;
	}
	public String getFristNameFirst() {
		return fristNameFirst;
	}
	public void setFristNameFirst(String fristNameFirst) {
		this.fristNameFirst = fristNameFirst;
	}
	
	
	
	
	
}

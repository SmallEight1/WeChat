package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

public class ContactsOfWeChat implements Serializable{
	/**
	 * 联系人信息
	 */
	private static final long serialVersionUID = 6753974629083965381L;
	private String name,area,head,autograph,lastStr,lastTime;
	private long weCode;
	private int newsNum,id;
	private boolean isAdd;
	private List<String> listImages;

	@Override
	public String toString() {
		return "Contacts [name=" + name + ", area=" + area + ", head=" + head + ", autograph=" + autograph
				+ ", lastStr=" + lastStr + ", lastTime=" + lastTime + ", weCode=" + weCode + ", newsNum=" + newsNum
				+ ", isAdd=" + isAdd + ", listImages=" + listImages + "]";
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
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
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

}

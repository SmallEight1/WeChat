package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 朋友圈信息
 * @author Administrator
 *
 */
public class Friends implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3988115977626389966L;
	private String userName,head,content,time;
	private List<String> listImages;
	public String getUserName() {
		return userName;
	}
	@Override
	public String toString() {
		return "friends [userName=" + userName + ", head=" + head + ", content=" + content + ", time=" + time
				+ ", listImages=" + listImages + "]";
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public List<String> getListImages() {
		return listImages;
	}
	public void setListImages(List<String> listImages) {
		this.listImages = listImages;
	}
}

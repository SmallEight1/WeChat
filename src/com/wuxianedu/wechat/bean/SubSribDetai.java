package com.wuxianedu.wechat.bean;

import java.io.Serializable;

public class SubSribDetai implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7613352465583535761L;
	private String title,image,content,time;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "SubscribDetail_SubSribDetail [title=" + title + ", image=" + image + ", content=" + content + ", time="
				+ time + "]";
	}
	
}

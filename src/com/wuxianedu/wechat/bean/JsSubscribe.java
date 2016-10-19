package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

public class JsSubscribe implements Serializable{

	/**
	 * 订阅号
	 */
	private static final long serialVersionUID = -7579912546371185154L;
	private int status;
	private String message;
	private List<Subscribes> listsubscribe;
	@Override
	public String toString() {
		return "Js_Subscribe [status=" + status + ", message=" + message + ", listsubscribe=" + listsubscribe + "]";
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public List<Subscribes> getListsubscribe() {
		return listsubscribe;
	}
	public void setListsubscribe(List<Subscribes> listsubscribe) {
		this.listsubscribe = listsubscribe;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}

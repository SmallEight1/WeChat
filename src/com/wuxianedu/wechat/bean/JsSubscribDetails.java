package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

public class JsSubscribDetails implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5057320749682823247L;
	private int status;
	private String message;
	private List<SubSribDetai> listsubSribDetails;
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
	public List<SubSribDetai> getListsubSribDetails() {
		return listsubSribDetails;
	}
	public void setListsubSribDetails(List<SubSribDetai> listsubSribDetails) {
		this.listsubSribDetails = listsubSribDetails;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	@Override
	public String toString() {
		return "Js_SubscribDetails [status=" + status + ", message=" + message + ", listsubSribDetails="
				+ listsubSribDetails + "]";
	}

	
}

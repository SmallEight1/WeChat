package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

public class JsaddFriends implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3546905989967692208L;
	private int status;
	private String message;
	private List<ContactsOfAddFriends> listContacts;
	public int getStatus() {
		return status;
	}
	@Override
	public String toString() {
		return "Js_Contact [status=" + status + ", message=" + message + ", listContacts=" + listContacts + "]";
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
	public List<ContactsOfAddFriends> getListContacts() {
		return listContacts;
	}
	public void setListContacts(List<ContactsOfAddFriends> listContacts) {
		this.listContacts = listContacts;
	}
}

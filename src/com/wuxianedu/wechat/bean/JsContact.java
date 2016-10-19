package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

public class JsContact implements Serializable{
	/**
	 * 联系人
	 */
	private static final long serialVersionUID = 314998455468373571L;
	private int status;
	private String message;
	private List<ContactsOfContact> listContacts;
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
	public List<ContactsOfContact> getListContacts() {
		return listContacts;
	}
	public void setListContacts(List<ContactsOfContact> listContacts) {
		this.listContacts = listContacts;
	}
}

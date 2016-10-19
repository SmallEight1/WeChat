package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

public class JsWechat  implements Serializable{
	/**
	 * 微信界面  总信息
	 */
	private static final long serialVersionUID = 7106799165670472455L;
	private int status;
	private String message;
	private List<ContactsOfWeChat> listContacts;
	
	@Override
	public String toString() {
		return "Wechat [status=" + status + ", message=" + message + ", listContacts=" + listContacts + "]";
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
	public List<ContactsOfWeChat> getListContacts() {
		return listContacts;
	}
	public void setListContacts(List<ContactsOfWeChat> listContacts) {
		this.listContacts = listContacts;
	}

}

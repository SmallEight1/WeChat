package com.wuxianedu.wechat.bean;

import java.io.Serializable;

public class JsPassCode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2951451225603930069L;
	private int status;
	private String message;
	private long passCode;
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
	public long getPassCode() {
		return passCode;
	}
	public void setPassCode(long passCode) {
		this.passCode = passCode;
	}
	@Override
	public String toString() {
		return "Js_PassCode [status=" + status + ", message=" + message + ", passCode=" + passCode + "]";
	}
}

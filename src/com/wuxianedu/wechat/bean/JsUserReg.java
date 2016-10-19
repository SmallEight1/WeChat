package com.wuxianedu.wechat.bean;

import java.io.Serializable;

public class JsUserReg implements Serializable{

	/**
	 * 用户注册界面  总信息
	 */
	private static final long serialVersionUID = -3020636880095574655L;
	
	private int status;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	
	@Override
	public String toString() {
		return "UserReg [status=" + status + ", message=" + message + ", userInfor=" + userInfor + "]";
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public UserInforOfUserLoginAndUserReg getUserInfor() {
		return userInfor;
	}
	public void setUserInfor(UserInforOfUserLoginAndUserReg userInfor) {
		this.userInfor = userInfor;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	private String message;
	private UserInforOfUserLoginAndUserReg userInfor;
	
	
}

/**
 * @Project Name:WeChat
 * @File Name:UserLogin.java
 * @Package Name:com.wuxianedu.wechat.bean
 * @Date:2016年8月23日下午5:14:47
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
*/

package com.wuxianedu.wechat.bean;

import java.io.Serializable;

/**
 * 用户登陆界面总信息
 */
public class JsUserLogin implements Serializable{

	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = 9200381465163035331L;
	
	
	private int status;
	private String message;
	private UserInforOfUserLoginAndUserReg userInfor;
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
	public UserInforOfUserLoginAndUserReg getUserInfor() {
		return userInfor;
	}
	public void setUserInfor(UserInforOfUserLoginAndUserReg userInfor) {
		this.userInfor = userInfor;
	}
	@Override
	public String toString() {
		return "UserLogin [status=" + status + ", message=" + message + ", userInfor=" + userInfor + "]";
	}
	
	
}


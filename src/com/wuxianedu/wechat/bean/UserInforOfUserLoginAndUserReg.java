/**
 * @Project Name:WeChat
 * @File Name:UserInfor.java
 * @Package Name:com.wuxianedu.wechat.bean
 * @Date:2016年8月23日下午5:15:31
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
*/

package com.wuxianedu.wechat.bean;

import java.io.Serializable;

/**
 *  用户信息    登陆界面 和注册界面公用
 */
public class UserInforOfUserLoginAndUserReg  implements Serializable{
	/**
	 * serialVersionUID:TODO(用一句话描述这个变量表示什么).
	 */
	private static final long serialVersionUID = -3746577857883933574L;
	
	
	private String userId,userName,head;
	private long userPhoneNum;
	
	
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
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
	public long getUserPhoneNum() {
		return userPhoneNum;
	}
	public void setUserPhoneNum(long userPhoneNum) {
		this.userPhoneNum = userPhoneNum;
	}
	
	@Override
	public String toString() {
		return "UserInfor [userId=" + userId + ", userName=" + userName + ", head=" + head + ", userPhoneNum="
				+ userPhoneNum + "]";
	}
	
	
	
}


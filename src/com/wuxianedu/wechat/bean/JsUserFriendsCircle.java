package com.wuxianedu.wechat.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 朋友圈总信息
 * @author Administrator
 *
 */
public class JsUserFriendsCircle implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2838986991449211420L;
	private String message,backGround;
	private int status;
	private List<Friends> listFriends;
	
	

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getBackGround() {
		return backGround;
	}

	public void setBackGround(String backGround) {
		this.backGround = backGround;
	}

	

	@Override
	public String toString() {
		return "UserFriendsCircle [message=" + message + ", backGround=" + backGround + ", status=" + status + ", list="
				+ listFriends + "]";
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Friends> getListFriends() {
		return listFriends;
	}

	public void setListFriends(List<Friends> list) {
		this.listFriends = list;
	}

}

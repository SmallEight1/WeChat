package com.wuxianedu.wechat.bean;

import java.io.Serializable;

public class ChatMsg implements Serializable{
	/**
	 * 微信聊天界面  对应消息发送适配器的对象
	 */
	private static final long serialVersionUID = 7148983888861418692L;
	private int id;
	private int contactsOfWeChat_id;
	private  String content,head,time;
	//消息是发送还是接受
	private int type;
	//内容是图片 还是文本
	private int typeContent;
	//接受消息
	public static final int TYPE_RECEIVED = 0;
	//发送消息
	public static final int TYPE_SENT = 1;
	//消息为文本
	public static final int TYPE_CONTENT_TEXT= 0;
	//消息为图片
	public static final int TYPE_CONTENT_IMG = 1;
	//消息为语言
	public static final int TYPE_CONTENT_VOICE = 2; //表示语音消息
	//构造器
	public ChatMsg(String content,String head,String time,int type,int typeContent) {
		this.content = content;
		this.head = head;
		this.time = time;
		this.type = type;
		this.typeContent = typeContent;
	}
	public ChatMsg(){
		
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	
	public int getTypeContent() {
		return typeContent;
	}
	public void setTypeContent(int typeContent) {
		this.typeContent = typeContent;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getHead() {
		return head;
	}
	public void setHead(String head) {
		this.head = head;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	@Override
	public String toString() {
		return "ChatMsg [content=" + content + ", head=" + head + ", type=" + type + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getContactsOfWeChat_id() {
		return contactsOfWeChat_id;
	}
	public void setContactsOfWeChat_id(int contactsOfWeChat_id) {
		this.contactsOfWeChat_id = contactsOfWeChat_id;
	}
	
}

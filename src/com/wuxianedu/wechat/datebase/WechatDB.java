package com.wuxianedu.wechat.datebase;

import java.util.ArrayList;
import java.util.List;

import com.wuxianedu.wechat.bean.ChatMsg;
import com.wuxianedu.wechat.bean.ContactsOfWeChat;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class WechatDB {
	 private SQLiteDatabase db;
	 private static WechatDB wechatDB;
	 
	 private WechatDB(Context context){
		 WechatOpenHelper dbHelper = new WechatOpenHelper(context);
		 db = dbHelper.getWritableDatabase();
	 }
	 
	 public synchronized static WechatDB getInstance(Context context){
		 if (wechatDB == null) {
			 synchronized(WechatDB.class){
				 if (wechatDB == null) {
					 wechatDB = new WechatDB(context);
				 }	 
			 }		
		 }
		 return wechatDB;
	 }
	 
	 /**
	  * 将聊天信息（ChatMsg）实例存储到数据库聊天信息表
	  * @param chatMsg
	  */
	 public void savaChatMsg(ChatMsg chatMsg){
		 if (chatMsg !=null) {
			ContentValues values = new ContentValues();
			values.put("content", chatMsg.getContent());
			values.put("head", chatMsg.getHead());
			values.put("time", chatMsg.getTime());
			values.put("type", chatMsg.getType());
			values.put("typeContent", chatMsg.getTypeContent());
			values.put("contactsOfWeChat_id", chatMsg.getContactsOfWeChat_id());
			db.insert(WechatOpenHelper.T_CHATMSG, null, values);
		 }
	 }
	 
	 
	 /**
	  * 从数据库读取聊天信息表所有对象
	  * @return
	  */
	 public List<ChatMsg> loadChatMsg(String id){
		 List<ChatMsg> list = new ArrayList<>();
		 String sql = "select *from "+ WechatOpenHelper.T_CONTACTSOFWECHAT + " p join "
				 +WechatOpenHelper.T_CHATMSG+" a on p._id=a.contactsOfWeChat_id "
				 +"where a.contactsOfWeChat_id ="+id;
		 Cursor cursor = db.rawQuery(sql, null);
		 while (cursor.moveToNext()){
			ChatMsg chatMsg = new ChatMsg();
			chatMsg.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			chatMsg.setType(cursor.getInt(cursor.getColumnIndex("type")));
			chatMsg.setTypeContent(cursor.getInt(cursor.getColumnIndex("typeContent")));
			chatMsg.setContent(cursor.getString(cursor.getColumnIndex("content")));
			chatMsg.setHead(cursor.getString(cursor.getColumnIndex("head")));
			chatMsg.setTime(cursor.getString(cursor.getColumnIndex("time")));
			list.add(chatMsg);
		 }
		 if (cursor != null) {
			 cursor.close();
		}
		 return list;		
	 }	 
	 
	 /**
	  * 将微信界面联系人信息保存进 数据库微信界面联系人表
	  */
	 public void savaContactsOfWeChat(ContactsOfWeChat contactsOfWeChat){
		 if (contactsOfWeChat !=null) {
			ContentValues values = new ContentValues();
			values.put("name", contactsOfWeChat.getName());
			values.put("head", contactsOfWeChat.getHead());
			values.put("lastTime", contactsOfWeChat.getLastTime());
			values.put("lastStr", contactsOfWeChat.getLastStr());
			values.put("newsNum", contactsOfWeChat.getNewsNum());
			db.insert(WechatOpenHelper.T_CONTACTSOFWECHAT, null, values);
		 }
	 }  
	 
	 /**
	  * 从数据库中取出所有微信联系人信息
	  */
	 public List<ContactsOfWeChat> loadContactsOfWeChat(){
		 List<ContactsOfWeChat> list = new ArrayList<>();
		 Cursor cursor = db.query(WechatOpenHelper.T_CONTACTSOFWECHAT, null, null,
				 null, null, null, null);
		 while (cursor.moveToNext()){
			ContactsOfWeChat contacts = new ContactsOfWeChat();
			contacts.setId(cursor.getInt(cursor.getColumnIndex("_id")));
			contacts.setName(cursor.getString(cursor.getColumnIndex("name")));
			contacts.setHead(cursor.getString(cursor.getColumnIndex("head")));
			contacts.setLastStr(cursor.getString(cursor.getColumnIndex("lastStr")));
			contacts.setNewsNum(cursor.getInt(cursor.getColumnIndex("newsNum")));
			contacts.setLastTime(cursor.getString(cursor.getColumnIndex("lastTime")));
			list.add(contacts);
		 }
		 if (cursor != null) {
			 cursor.close();
		}
		 return list;		
	 }	 
	 /**
	  * 修改微信界面数据表数据
	  *  
	  * */
	 public void updateContactsOfWeChat(ContentValues values,
			 String whereClause, String[] whereArgs){
		 
		 db.update(WechatOpenHelper.T_CONTACTSOFWECHAT, values, whereClause, whereArgs);
	 }
}

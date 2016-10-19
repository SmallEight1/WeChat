package com.wuxianedu.wechat.datebase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WechatOpenHelper extends SQLiteOpenHelper{
	/**
	 * 聊天信息表建表语句
	 */
	private static final String DB_NAME = "wuxianedu.db";
	private static int DB_VERSION = 2; 
	public static final String T_CHATMSG = "t_chatMsg";  //聊天信息表
	public static final String T_CONTACTSOFWECHAT = "t_contactsOfWeChat "; //微信界面联系人表
	
	public WechatOpenHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);	
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String create_chatMsg = "CREATE TABLE IF NOT EXISTS "+T_CHATMSG
				+ "(_id integer primary key autoincrement," 
				+ " content text, head text, time text,"
				+ " type integer,typeContent integer,"
				+ "	contactsOfWeChat_id integer)";
		
		String createContactsOfWeChat = "CREATE TABLE IF NOT EXISTS "+T_CONTACTSOFWECHAT
				+"(_id integer primary key autoincrement," 
				+ " name text, head text, lastTime text,"
				+ " lastStr text,newsNum integer)";
		
		db.execSQL(create_chatMsg);
		db.execSQL(createContactsOfWeChat);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		 switch (oldVersion){
         case 1:
             db.execSQL("DROP TABLE IF EXISTS "+ T_CHATMSG);
             db.execSQL("DROP TABLE IF EXISTS "+ T_CONTACTSOFWECHAT);
     }
     onCreate(db);
	}
}

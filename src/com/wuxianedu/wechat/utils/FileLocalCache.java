package com.wuxianedu.wechat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.security.MessageDigest;

import android.content.Context;


/**
 * 缓存
 * @author caibing.zhang
 *
 */
public class FileLocalCache {

	/**
	 * 检查文件是否存在
	 * @return
	 */
	public static boolean checkDir(String filePath) {
		File f = new File(filePath);
		if (!f.exists()) {
			return f.mkdirs();
		}
		return true;
	}

	/**
	 * 返回md5后的值
	 * @param url
	 * @return
	 */
	public static String md5(String url) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			md5.update(url.getBytes("UTF-8"));
			byte messageDigest[] = md5.digest();

			StringBuffer hexString = new StringBuffer();
			for (int i = 0; i < messageDigest.length; i++) {
				String t = Integer.toHexString(0xFF & messageDigest[i]);
				if (t.length() == 1) {
					hexString.append("0" + t);
				} else {
					hexString.append(t);
				}
			}
			return hexString.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 清除SD卡中的数据缓存
	 */
/*	public static void clearCache1() {
		File f = new File(FancusApplication.CACHE_DIR);
		if (f.exists() && f.isDirectory()) {
			File flist[] = f.listFiles();
			for (int i = 0; flist != null && i < flist.length; i++) {
				flist[i].delete();
			}
		}
	}*/


	
	/**
	 * 取得序列化数据
	 *@param fileName  @return
	 */
	public static Object getSerializableData(Context context,String fileName) {
		String dir= FileUtil.getCacheDir(context);
		Object obj = null;
		try {
			File d = new File(dir);
			if (!d.exists()){
				d.mkdirs();
			}
			File f = new File(dir +md5(fileName));
			if (!f.exists()){
				f.createNewFile();
			}
			if (f.length() == 0){
				return null;
			}
			FileInputStream byteOut = new FileInputStream(f);
			ObjectInputStream out = new ObjectInputStream(byteOut);
			obj=out.readObject();
			out.close();
			try {
	            if(byteOut!=null){
	            	byteOut.close();
	            }
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		} catch (StreamCorruptedException e) {
			e.printStackTrace();
		} catch (OptionalDataException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return obj;
	}


	/**
	 * 进行序列化
	 * @param type 缓存文件在SD卡中，还是在SYSTEM中
	 * @param obj
	 * @param fileName
	 */
	public static void setSerializableData(Context context,Object obj,String fileName) {
		String dir= FileUtil.getCacheDir(context);
		try {
			FileOutputStream bytetOut = new FileOutputStream(new File(dir + md5(fileName)));
			ObjectOutputStream outer = new ObjectOutputStream(bytetOut);
			outer.writeObject(obj);
			outer.flush();
			outer.close();
			bytetOut.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @Description: 删除序列化数据
	 * @param type
	 * @param fileName
	 */
	public static void delSerializableData(Context context,String fileName){
		String dir= FileUtil.getCacheDir(context);
		File d = new File(dir);
		if (!d.exists()){
			d.mkdirs();
		}
		File f = new File(dir +md5(fileName));
		if (f.exists()){
			f.delete();
		}
	}
}

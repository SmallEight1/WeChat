/**
 * @Project Name:wxhlcorelibs
 * @File Name:FileUtil.java
 * @Package Name:com.wuxianedu.libs
 * @Date:2016��8��16������2:21:28
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
*/

package com.wuxianedu.wechat.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import android.content.Context;


/**
 * 获取 Assets目录下文件对应的  字符串
 * @ClassName: AssetsUtil 
 * @Function: TODO ADD FUNCTION
 * @date: 2016年8月23日 下午5:12:22 
 * @author YuanDong.Qiao
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class AssetsUtil {
	
	public  static String getAssets(Context context,String fileName){
		BufferedReader bufferedReader = null;
		try {
			InputStream is = context.getAssets().open(fileName);
			bufferedReader = new BufferedReader(new InputStreamReader(is));
			String str;
			StringBuilder sb = new StringBuilder();
			while ((str = bufferedReader.readLine()) != null) {
				sb.append(str);
			}
			return sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			
		}finally {
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
					
				}
			}
		}
		return null;
	}
}


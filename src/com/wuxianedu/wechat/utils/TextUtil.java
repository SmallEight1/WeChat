/**
 * @Project Name:WeChat
 * @File Name:TextUtil.java
 * @Package Name:com.wuxianedu.wechat.utils
 * @Date:2016年8月23日下午5:06:05
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
*/

package com.wuxianedu.wechat.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName:TextUtil 
 * @Function: TODO ADD FUNCTION. 
 * @Date:     2016年8月23日 下午5:06:05 
 * @author    YuanDong.Qiao
 * @Copyright(c)2016 www.wuxianedu.com Inc. All rights reserved.
 */
public class TextUtil {


	/**
	 * 判断字符串是否为空
	 * isEmpty:(这里用一句话描述这个方法的作用)
	 * @date: 2016年8月23日 下午5:06:56 
	 * @author YuanDong.Qiao
	 */
	public static boolean isEmpty(String str) {
		if (str == null || str.trim().length() == 0){
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
     * 判断是否为字母
     * @param str
     * @return
     */
    public static boolean isEnglish(String fstrData){  
    	//根据首字母进行判断,是否为英文
        char   c   =   fstrData.charAt(0);   
        if(((c>='a'&&c<='z')   ||   (c>='A'&&c<='Z'))){   
              return   true;   
        }else{   
              return   false;   
        }
    }
    
    /**
     * 判断是否为中文
     * @param str
     * @return
     */
    public static boolean isChinese(String str){
    	//正则表达式
        Pattern p=Pattern.compile("^[\u4e00-\u9fa5]*$");
        Matcher m=p.matcher(str);
        if(m.matches()){
            return true;
        }else{
            return false;
        }
    } 
}


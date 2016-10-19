package com.wuxianedu.wechat.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.wuxianedu.wechat.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

public class ExpressionUtil {
	
	public static Map<String,String> drawableIdToFaceName = new HashMap<String, String>();
	public static Map<String,Integer> faceNameToDrawableId = new HashMap<String,Integer>();
	
	static{
		drawableIdToFaceName.put("f_static_000", "[可怜]");
		drawableIdToFaceName.put("f_static_001", "[微笑]");
		drawableIdToFaceName.put("f_static_002", "[撇嘴]");
		drawableIdToFaceName.put("f_static_003", "[色]");
		drawableIdToFaceName.put("f_static_004", "[得意]");
		drawableIdToFaceName.put("f_static_005", "[流泪]");
		drawableIdToFaceName.put("f_static_006", "[闭嘴]");
		drawableIdToFaceName.put("f_static_007", "[睡觉]");
		drawableIdToFaceName.put("f_static_008", "[大哭]");
		drawableIdToFaceName.put("f_static_009", "[尴尬]");
		drawableIdToFaceName.put("f_static_010", "[发怒]");
		drawableIdToFaceName.put("f_static_011", "[调皮]");
		drawableIdToFaceName.put("f_static_012", "[呲牙]");
		drawableIdToFaceName.put("f_static_013", "[惊讶]");
		drawableIdToFaceName.put("f_static_014", "[难过]");
		drawableIdToFaceName.put("f_static_015", "[呕吐]");
		drawableIdToFaceName.put("f_static_016", "[偷笑]");
		drawableIdToFaceName.put("f_static_017", "[可爱]");
		drawableIdToFaceName.put("f_static_018", "[傲慢]");
		drawableIdToFaceName.put("f_static_019", "[流汗]");
		drawableIdToFaceName.put("f_static_020", "[憨笑]");
		drawableIdToFaceName.put("f_static_021", "[奋斗]");
		drawableIdToFaceName.put("f_static_022", "[咒骂]");
		drawableIdToFaceName.put("f_static_023", "[疑问]");
		drawableIdToFaceName.put("f_static_024", "[晕]");
		drawableIdToFaceName.put("f_static_025", "[折磨]");
		drawableIdToFaceName.put("f_static_026", "[衰]");
		drawableIdToFaceName.put("f_static_027", "[再见]");
		drawableIdToFaceName.put("f_static_028", "[抠鼻]");
		drawableIdToFaceName.put("f_static_029", "[鼓掌]");
		drawableIdToFaceName.put("f_static_030", "[糗大了]");
		drawableIdToFaceName.put("f_static_031", "[坏笑]");
		drawableIdToFaceName.put("f_static_032", "[哈欠]");
		drawableIdToFaceName.put("f_static_033", "[鄙视]");
		drawableIdToFaceName.put("f_static_034", "[委屈]");
		drawableIdToFaceName.put("f_static_035", "[快哭了]");
		drawableIdToFaceName.put("f_static_036", "[阴险]");
		drawableIdToFaceName.put("f_static_037", "[亲亲]");
		
		faceNameToDrawableId.put("[可怜]",R.drawable.f_static_000);
		faceNameToDrawableId.put("[微笑]",R.drawable.f_static_001);
		faceNameToDrawableId.put("[撇嘴]",R.drawable.f_static_002);
		faceNameToDrawableId.put("[色]",R.drawable.f_static_003);
		faceNameToDrawableId.put("[得意]",R.drawable.f_static_004);
		faceNameToDrawableId.put("[流泪]",R.drawable.f_static_005);
		faceNameToDrawableId.put("[闭嘴]",R.drawable.f_static_006);
		faceNameToDrawableId.put("[睡觉]",R.drawable.f_static_007);
		faceNameToDrawableId.put("[大哭]",R.drawable.f_static_008);
		faceNameToDrawableId.put("[尴尬]",R.drawable.f_static_009);
		faceNameToDrawableId.put("[发怒]",R.drawable.f_static_010);
		faceNameToDrawableId.put("[调皮]",R.drawable.f_static_011);
		faceNameToDrawableId.put("[呲牙]",R.drawable.f_static_012);
		faceNameToDrawableId.put("[惊讶]",R.drawable.f_static_013);
		faceNameToDrawableId.put("[难过]",R.drawable.f_static_014);
		faceNameToDrawableId.put("[呕吐]",R.drawable.f_static_015);
		faceNameToDrawableId.put("[偷笑]",R.drawable.f_static_016);
		faceNameToDrawableId.put("[可爱]",R.drawable.f_static_017);
		faceNameToDrawableId.put("[傲慢]",R.drawable.f_static_018);
		faceNameToDrawableId.put("[流汗]",R.drawable.f_static_019);
		faceNameToDrawableId.put("[憨笑]",R.drawable.f_static_020);
		faceNameToDrawableId.put("[奋斗]",R.drawable.f_static_021);
		faceNameToDrawableId.put("[咒骂]",R.drawable.f_static_022);
		faceNameToDrawableId.put("[疑问]",R.drawable.f_static_023);
		faceNameToDrawableId.put("[晕]",R.drawable.f_static_024);
		faceNameToDrawableId.put("[折磨]",R.drawable.f_static_025);
		faceNameToDrawableId.put("[衰]",R.drawable.f_static_026);
		faceNameToDrawableId.put("[再见]",R.drawable.f_static_027);
		faceNameToDrawableId.put("[抠鼻]",R.drawable.f_static_028);
		faceNameToDrawableId.put("[鼓掌]",R.drawable.f_static_029);
		faceNameToDrawableId.put("[糗大了]",R.drawable.f_static_030);
		faceNameToDrawableId.put("[坏笑]",R.drawable.f_static_031);
		faceNameToDrawableId.put("[哈欠]",R.drawable.f_static_032);
		faceNameToDrawableId.put("[鄙视]",R.drawable.f_static_033);
		faceNameToDrawableId.put("[委屈]",R.drawable.f_static_034);
		faceNameToDrawableId.put("[快哭了]",R.drawable.f_static_035);
		faceNameToDrawableId.put("[阴险]",R.drawable.f_static_036);
		faceNameToDrawableId.put("[亲亲]",R.drawable.f_static_037);
	}
	
	/**
	 * 得到表情
	 * @return
	 */
	public static List<Map<String,Object>> buildExpressionsList(Context context){
		List<Map<String,Object>> list = new ArrayList<Map<String, Object>>();
		DecimalFormat df = new DecimalFormat("000");//格式化数字
		for(int i = 0;i<36;i++){
			Map<String,Object> map = new HashMap<String, Object>();
			//通过特定的拼接本地的表情名
			String formatStr = "f_static_"+df.format(i);
			int drawableId = 0 ;
			try {
				//反射
				drawableId = R.drawable.class.getDeclaredField(formatStr).getInt(context);
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			}
			Drawable drawable = context.getResources().getDrawable(drawableId);
			//字符串的形式的表情id
			map.put("drawableId", formatStr);
			//存放图片
			map.put("drawable",drawable);
			//表情的Id
			map.put("drawableRId", drawableId);
			list.add(map);
		}
		return list;
	}
	
	/**
	 * 表情文字混合解析      解析[哈哈]为表情
	 * @param context
	 * @param str
	 * @return
	 */
	public static SpannableString decorateFaceInStr(Context context,String str){
		SpannableString spannable = new SpannableString(str);
		SpannableString content= decorateFaceInStr(context.getResources(),spannable,
		ExpressionUtil.getStartAndEndIndex(spannable.toString(), "\\[+[^x00-xff]+\\]"));
		return content;
	}

		
	
	/**
	 * 解析表情
	 * @param resources
	 * @param spannable
	 * @param list
	 * @return
	 */
	public static SpannableString decorateFaceInStr(
			Resources resources,SpannableString spannable,List<Map<String,Object>> list){
		Drawable drawable = null;
		for(Map<String,Object> map:list){
			if(faceNameToDrawableId.containsKey(map.get("faceName"))){
				drawable = resources.getDrawable(faceNameToDrawableId.get(map.get("faceName")));
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());
				ImageSpan span = new ImageSpan(drawable,ImageSpan.ALIGN_BASELINE);
				spannable.setSpan(span, (Integer)map.get("startIndex"), (Integer)map.get("endIndex"), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
			}
		}
		list.clear();
		return spannable;
	}
	
	/**
	 * 正则匹配
	 * @param sourceStr
	 * @param pat
	 * @return 
	 */
	public static List<Map<String,Object>> getStartAndEndIndex(String sourceStr,String pat){
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Pattern pattern=Pattern.compile(pat);
		Matcher matcher = pattern.matcher(sourceStr);
		//尝试查找与该模式匹配的输入序列的下一个子序列。
		while (matcher.find()) {
			Map<String,Object> map = new HashMap<String, Object>();
			String faceName = matcher.group().substring(0,matcher.group().length());//表情名称
//			String str=matcher.group(); //内容
			map.put("startIndex",matcher.start());
			map.put("endIndex",matcher.end());
			map.put("faceName",faceName);
			list.add(map);
		}
		return list;
	}
	
}

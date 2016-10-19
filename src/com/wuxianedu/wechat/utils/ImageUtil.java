package com.wuxianedu.wechat.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore.Images.ImageColumns;

public class ImageUtil {

	/**
	 * 压缩图像
	 * @param path
	 * @return
	 */
	public static String compressionImage(Context context,String path) {
		File f = new File(path);
		if (f.exists()) {
			String extensionName = FileUtil.getExtensionName(f.getName());
			String fileName = String.valueOf(System.currentTimeMillis());
			String newName = fileName+"."+extensionName;
			String cache = FileUtil.getCacheDir(context)+"temp_image/"; 
			FileUtil.checkDir(cache);
			String newPath = cache+ newName;
			FileInputStream fis;
			try {
				fis = new FileInputStream(f);
				long size= fis.available();
	            fis.close();
	            fis=null;
	            if(size>204800L){   //200KB以内不压缩
	    			Bitmap bm = getSmallBitmap(path,480,800);
	    			FileOutputStream fos = new FileOutputStream(new File(newPath));
	    			bm.compress(Bitmap.CompressFormat.JPEG, 90, fos);
	    			if(fos!=null){
	    				fos.close();
	    				fos=null;
	    			}
	    			bm.recycle();
	    			bm=null;
	    			
	    			return newPath;
	            }
			} catch (Exception e) {
					e.printStackTrace();
			}
        }
		return path;
	}
	
	/**
	 * 根据路径获得图片并压缩返回bitmap用于显示
	 * @param imagesrc
	 * @return
	 */
	public static Bitmap getSmallBitmap(String filePath,int width,int height) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		//这里返回bitmap为null 只是返回一个宽高
		BitmapFactory.decodeFile(filePath, options);
		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, width, height);
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bitmap =BitmapFactory.decodeFile(filePath, options);
		options.inJustDecodeBounds = true;
		return bitmap;
	}
	
	/**
	 * 计算图片的缩放值
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height / (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}
		return inSampleSize;
	}
	
	/**
	 * 将uri转换为据对路径
	 * @param context
	 * @param uri
	 * @return
	 */
	public static String getRealFilePath( final Context context, final Uri uri ) {
	    if ( null == uri ) return null;
	    final String scheme = uri.getScheme();
	    String data = null;
	    if ( scheme == null )
	        data = uri.getPath();
	    else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
	        data = uri.getPath();
	    } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
	        Cursor cursor = context.getContentResolver().query( uri, new String[] { ImageColumns.DATA }, null, null, null );
	        if ( null != cursor ) {
	            if ( cursor.moveToFirst() ) {
	                int index = cursor.getColumnIndex( ImageColumns.DATA );
	                if ( index > -1 ) {
	                    data = cursor.getString( index );
	                }
	            }
	            cursor.close();
	        }
	    }
	    return data;
	}
	
}

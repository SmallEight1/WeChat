package com.wuxianedu.wechat.adapter;


import java.io.IOException;

import java.lang.ref.WeakReference;
/**
 * 聊天界面 适配器listView的
 */
import java.util.List;
import com.wuxianedu.wechat.R;
import com.wuxianedu.wechat.bean.ChatMsg;
import com.wuxianedu.wechat.bean.UserInforOfUserLoginAndUserReg;
import com.wuxianedu.wechat.listener.ChatListener;
import com.wuxianedu.wechat.utils.Constant;
import com.wuxianedu.wechat.utils.ExpressionUtil;
import com.wuxianedu.wechat.utils.FileLocalCache;
import com.wuxianedu.wechat.utils.ImageUtil;
import com.wuxianedu.wechat.utils.SetImage;
import com.wuxianedu.wechat.utils.SetTime;
import com.wuxianedu.wechat.widget.RoundImageView;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter{
	private Context context;
	private List<ChatMsg> listchat;
	private MediaPlayer player;
	private ChatListener chatListener;
	
	public ChatAdapter(Context context,List<ChatMsg> listchat,MediaPlayer player) {
		this.context =context;
		this.listchat =listchat;
		this.player = player;
	}
		
	public void setList(List<ChatMsg> listchat){
		this.listchat = listchat;
		notifyDataSetChanged();
	}
	
	@Override
	public int getCount() {	
		return listchat.size();
	}

	@Override
	public ChatMsg getItem(int position) {	
		return listchat.get(position);
	}

	@Override
	public long getItemId(int position) {		
		return position;
	}
	
	public void setOnItemListener(ChatListener chatListener){
		this.chatListener = chatListener;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(R.layout.item_listview_chat, parent,false);			
			convertView.setTag(new ViewHolder(convertView));
		}
		Initialized((ViewHolder) convertView.getTag(), position);
		return convertView;
	}
	
	
	private void Initialized(ViewHolder viewHolder,int position){
		viewHolder.setPostion(position);
		//得到对象
		final ChatMsg chatMsg = getItem(position);
		if(chatMsg.getType() == ChatMsg.TYPE_RECEIVED){			
			
			reMessage(viewHolder,chatMsg);
		}else if(chatMsg.getType() == ChatMsg.TYPE_SENT){//如果是发送的消息  显示右边图像		
			viewHolder.ly_me.setVisibility(View.VISIBLE);
			viewHolder.ly_your.setVisibility(View.GONE);	
			if (chatMsg.getTypeContent() == ChatMsg.TYPE_CONTENT_TEXT) {	
				//发送文本
				sendTextMessage(viewHolder,chatMsg);
			}else if (chatMsg.getTypeContent() == ChatMsg.TYPE_CONTENT_IMG) {
				//发送图片
				sendImgMessage(viewHolder,chatMsg);
			}else if(chatMsg.getTypeContent() == ChatMsg.TYPE_CONTENT_VOICE){
				//发送声音
				sendVoieMessage(viewHolder,chatMsg);
			}
			//得到本地对象   并设置头像
			UserInforOfUserLoginAndUserReg userInfor = (UserInforOfUserLoginAndUserReg) 
					FileLocalCache.getSerializableData(context,Constant.USER_INFOR);
			SetImage.SetHeadImage(viewHolder.head_me, userInfor.getHead());			
			//设置时间(得到当前时间，格式化)		
			//设置时间转换类型
			viewHolder.time.setText(SetTime.getData(chatMsg.getTime()));		
		}
	}
	
	/**
	 * 如果是接受的消息  显示左边图像
	 */
	private void reMessage(ViewHolder viewHolder,ChatMsg chatMsg){
		viewHolder.time.setText(SetTime.getData(chatMsg.getTime()));		
		viewHolder.ly_your.setVisibility(View.VISIBLE);
		viewHolder.ly_me.setVisibility(View.GONE);
		viewHolder.content_your.setText(chatMsg.getContent());
		SetImage.SetHeadImage(viewHolder.head_your, chatMsg.getHead());
	}
	
	/**
	 * 发送的消息是文本，设置文本，隐藏图片，语言
	 */
	private void sendTextMessage(ViewHolder viewHolder,ChatMsg chatMsg){	
		viewHolder.content_me.setVisibility(View.VISIBLE);
		viewHolder.content_imgbk.setVisibility(View.GONE);
		viewHolder.content_voicebk.setVisibility(View.GONE);
		//设置  SpannableString 的 Text
		viewHolder.content_me.setText(
				ExpressionUtil.decorateFaceInStr(context, chatMsg.getContent()));
	}
	
	/**
	 * 如果消息是图片,设置图片， 隐藏文本和语音
	 */
	private void sendImgMessage(ViewHolder viewHolder,final ChatMsg chatMsg){
		viewHolder.content_imgbk.setVisibility(View.VISIBLE);
		viewHolder.content_me.setVisibility(View.GONE);
		viewHolder.content_voicebk.setVisibility(View.GONE);
		loadBitmap(chatMsg.getContent(),viewHolder.content_img_me);	
	}
	
	/**
	 * 如果消息是语言，设置语言，隐藏文本和图片
	 */
	private void sendVoieMessage(ViewHolder viewHolder,final ChatMsg chatMsg){	
		viewHolder.content_voicebk.setVisibility(View.VISIBLE);
		viewHolder.content_me.setVisibility(View.GONE);
		viewHolder.content_imgbk.setVisibility(View.GONE);	
		final AnimationDrawable animationDrawable = 
				(AnimationDrawable) viewHolder.content_voice_me.getDrawable();
		viewHolder.content_voice_me.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if (player.isPlaying()) {
					//关闭
					stopPlayer();
					animationDrawable.stop();
				}else {
					//传入内容   和 animationDrawable对象
					startPlayer(chatMsg.getContent(), animationDrawable);
					animationDrawable.start();
				}
				
			}
		});
	}
	
	public class ViewHolder implements OnClickListener{
		TextView  time,content_me,content_your;
		RoundImageView head_me,head_your;
		RelativeLayout ly_me,ly_your,content_imgbk,content_voicebk;
		ImageView content_img_me,content_voice_me;	
		private int position;
		
		public ViewHolder(View convertView) {
			head_your = (RoundImageView) convertView.findViewById(R.id.head_your);
			content_your = (TextView) convertView.findViewById(R.id.content_your_id);
			time = (TextView) convertView.findViewById(R.id.time);
			head_me = (RoundImageView) convertView.findViewById(R.id.head_me);
			content_me = (TextView) convertView.findViewById(R.id.content_me_id);
			content_img_me = (ImageView) convertView.findViewById(R.id.content_img_id);
			content_voice_me = (ImageView) convertView.findViewById(R.id.content_voice_id);
			//得到视图
			ly_me= (RelativeLayout) convertView.findViewById(R.id.me_ly_id);
			ly_your= (RelativeLayout) convertView.findViewById(R.id.your_ly_id);
			content_imgbk = (RelativeLayout) convertView.findViewById(R.id.content_imgbk_id);
			content_voicebk = (RelativeLayout) convertView.findViewById(R.id.content_voicebk_id);
			content_img_me.setOnClickListener(this);
		}
		
		private void setPostion(int position){
			this.position = position;
		}
		
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.content_img_id:
				chatListener.onItemClick(this, position);
				break;

			}
		}
	}
	
	/**
	 * 停止播放
	 */
	private void stopPlayer(){	
		if (player != null && player.isPlaying()) {
			player.stop();
		}
	}
	
	/**
	 * 开始播放
	 * @param string
	 * @param animationDrawable
	 */
	private void startPlayer(String string,final AnimationDrawable animationDrawable){
		if (player != null) {
			try {
				player.reset(); //重置播放器
				player.setDataSource(string); //设置播放的资源
				player.prepare(); //准备播放
				player.start(); //开始播放
				player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
					
					@Override
					public void onCompletion(MediaPlayer mp) {
						animationDrawable.stop();
					}
				});
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
				
			} catch (SecurityException e) {
				e.printStackTrace();
				
			} catch (IllegalStateException e) {
				e.printStackTrace();
				
			} catch (IOException e) {
				e.printStackTrace();
				
			}
		}
	}
	

	class BitmapWorkerTask extends AsyncTask<String, Void, Bitmap> {
	    private final WeakReference<ImageView> imageViewReference;
	    private String data = "";
	    public BitmapWorkerTask(ImageView imageView) {
	        imageViewReference = new WeakReference<ImageView>(imageView);
	    }

	    @Override
	    protected Bitmap doInBackground(String... params) {
	        data = params[0];
	        return ImageUtil.getSmallBitmap(data, 240, 240);
	    }
	 
	    @Override
	    protected void onPostExecute(Bitmap bitmap) {
	    	 if (isCancelled()) {
	    		   bitmap = null;
	    	 }
	         if (imageViewReference != null && bitmap != null) {
	 	            final ImageView imageView = imageViewReference.get();
	 	            final BitmapWorkerTask bitmapWorkerTask =
	                     getBitmapWorkerTask(imageView);
	            if (this == bitmapWorkerTask && imageView != null) {
	                 imageView.setImageBitmap(bitmap);
	             }
	 	     }
	    }
	}
	
	static class AsyncDrawable extends BitmapDrawable {
		//一个BitmapWorkerTask的弱引用
	    private final WeakReference<BitmapWorkerTask> bitmapWorkerTaskReference;
	 
	    public AsyncDrawable(Resources res,Bitmap bitmap,
	            BitmapWorkerTask bitmapWorkerTask) {
	    	super(res,bitmap);
	        bitmapWorkerTaskReference =
	            new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);
	    }
	 
	    public BitmapWorkerTask getBitmapWorkerTask() {
	        return bitmapWorkerTaskReference.get();
	    }
	}
	
	public void loadBitmap(String filepath, ImageView imageView) {
	    if (cancelPotentialWork(filepath, imageView)) {
	        final BitmapWorkerTask task = new BitmapWorkerTask(imageView);
	        final AsyncDrawable asyncDrawable =
	                new AsyncDrawable(context.getResources(),null, task);
	        imageView.setImageDrawable(asyncDrawable);
	        task.execute(filepath);
	    }
	}
	
	public static boolean cancelPotentialWork(String filepath, ImageView imageView) {
	    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
 
	    if (bitmapWorkerTask != null) {
	        final String bitmapData = bitmapWorkerTask.data;
	        if (bitmapData != filepath) {
	            // 取消之前的任务
	            bitmapWorkerTask.cancel(true);
	        } else {
	            // 相同任务已经存在，直接返回false，不再进行重复的加载
	            return false;
	        }
	    }
	    // 没有Task和ImageView进行绑定，或者Task由于加载资源不同而被取消，返回true
	    return true;
	}
	
	private static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView) {
	   if (imageView != null) {
	       final Drawable drawable = imageView.getDrawable();
	       if (drawable instanceof AsyncDrawable) {
	           final AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
	           return asyncDrawable.getBitmapWorkerTask();
	       }
	    }
	    return null;
	}

}

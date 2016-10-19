package com.wuxianedu.wechat.listener;

import com.wuxianedu.wechat.adapter.ChatAdapter;

public interface ChatListener {
	void onItemClick(ChatAdapter.ViewHolder viewHolder, int position);
}

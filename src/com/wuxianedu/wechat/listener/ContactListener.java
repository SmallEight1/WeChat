package com.wuxianedu.wechat.listener;

import com.wuxianedu.wechat.adapter.ContactAdapter;

public interface ContactListener {
	void onItemClick(ContactAdapter.ViewHodler viewHolder, int position);
}

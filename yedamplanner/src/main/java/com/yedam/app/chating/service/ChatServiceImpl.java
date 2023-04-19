package com.yedam.app.chating.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.chating.ChatingVO;
import com.yedam.app.chating.mapper.ChatMapper;

@Service
public class ChatServiceImpl implements ChatService{
	
	@Autowired
	ChatMapper chatMapper;
	
	@Override
	public int insertChatLog(String planId, String userId, String chatLog) {
		return chatMapper.insertChatInfo(planId, userId, chatLog);
	}

	@Override
	public List<ChatingVO> getChatList(String planId) {
		return chatMapper.getChatList(planId);
	}

}

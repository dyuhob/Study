package com.yedam.app.chating.service;

import java.util.List;

import com.yedam.app.chating.ChatingVO;

public interface ChatService {
	//채팅 db에 저장
	public int insertChatLog(String planId, String userId, String ChatLog);
	
	//채팅 내역 출력
	public List<ChatingVO> getChatList(String planId);
}

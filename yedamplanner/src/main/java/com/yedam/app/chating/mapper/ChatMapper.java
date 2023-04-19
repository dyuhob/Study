package com.yedam.app.chating.mapper;

import java.util.List;

import com.yedam.app.chating.ChatingVO;

public interface ChatMapper {

	public int insertChatInfo(String planId, String userId, String chatLog);

	public List<ChatingVO> getChatList(String planId);


}

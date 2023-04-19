package com.yedam.app.notice.service;

import java.util.List;

public interface NoticeService {
//	공지 리스트
	List<NoticeVO> noticeList();
//	단건 조회
	NoticeVO noticeBoardView(String notice_boardNum);
//	공지 작성
	void noticeInsert(NoticeVO noticeVO);
//	공지 수정
	void noticeUpdate(NoticeVO noticeVO);
//	공지 조회수
	void noticeViews(NoticeVO noticeVO);
//	공지 삭제
	void noticeDelete(String noticeBoardNum);
}

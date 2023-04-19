package com.yedam.app.notice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.notice.mapper.NoticeMapper;

@Service
public class NoticeServiceImpl implements NoticeService{
	
	@Autowired
	NoticeMapper noticeMapper;
	
	@Override
	public List<NoticeVO> noticeList() {
		return noticeMapper.noticeList();
	}

	@Override
	public NoticeVO noticeBoardView(String notice_boardNum) {
		return noticeMapper.noticeBoardView(notice_boardNum);
	}

	@Override
	public void noticeInsert(NoticeVO noticeVO) {
		noticeMapper.noticeInsert(noticeVO);
	}

	@Override
	public void noticeUpdate(NoticeVO noticeVO) {
		noticeMapper.noticeUpdate(noticeVO);
	}

	@Override
	public void noticeViews(NoticeVO noticeVO) {
	    noticeMapper.noticeViews(noticeVO);
	}

	@Override
	public void noticeDelete(String noticeBoardNum) {
		noticeMapper.noticeDelete(noticeBoardNum);
	}
}

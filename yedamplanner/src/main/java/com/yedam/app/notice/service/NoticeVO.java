package com.yedam.app.notice.service;

import java.util.Date;

import lombok.Data;

@Data
public class NoticeVO {

	String noticeBoardNum;
	Date noticeBoardDate;
	String noticeBoardTitle;
	String noticeBoardContent;
	String noticeBoardDelete;
	String memberId;
	int noticeBoardViews;
	
}

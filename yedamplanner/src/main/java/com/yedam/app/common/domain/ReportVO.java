package com.yedam.app.common.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ReportVO {
	
	String reportKey;
	Date reportDate;
	char reportApprove;
	Date approveDate;
	
	// 필수 입력 요망 속성
	String reportReason;
	String reporterId;
	String reportedId;
	String reportPost;
	
}

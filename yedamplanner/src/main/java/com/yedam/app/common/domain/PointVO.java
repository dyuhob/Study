package com.yedam.app.common.domain;

import java.util.Date;

import lombok.Data;

@Data
public class PointVO {
	
	Date HistoryDate;
	int historyNo;
	
	// 포인트 기능 작동시 필수 입력
	char pointType;
	int pointNumber;
	String pointWhere;
	String memberId;
	
}

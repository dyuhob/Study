package com.yedam.app.influencer.domain;

import java.util.Date;

import lombok.Data;

@Data
public class ConvertVO {
	
	String convertKey;
	int convertPoint;
	Date convertDate;
	String applicantId;
	char applyApprove;
	Date applyDate;
}

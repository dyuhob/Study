package com.yedam.app.influencer.domain;

import java.util.Date;

import lombok.Data;

@Data
public class InfluencerApplyVO {
	
	int applyNo;
	Date applyDate;
	char applyApprove;
	String memberId;
}

package com.yedam.app.planner.domain;

import lombok.Data;

@Data
public class MemberReviewVO {
	
	String mrKey;
	String mrContent;
	int mrLike;
	String mrPlanId;
	String mrRater;
	String mrSubject;
	
	// NULL 방지용 속성값
	String memberId;
}

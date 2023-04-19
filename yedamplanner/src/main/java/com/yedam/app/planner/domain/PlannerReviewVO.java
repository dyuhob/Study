package com.yedam.app.planner.domain;

import lombok.Data;

@Data
public class PlannerReviewVO {
	
	String planReviewId;
	String content;
	int prStar;
	String rater;
	String planId;
	String planName;
}

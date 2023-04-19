package com.yedam.app.influencer.domain;

import lombok.Data;

@Data
public class OfferingVO {
	
	int offeringNo;
	String applicantId;
	int offeringPoint;
	int offeringAmount;
	String listingNo;
	int buyingPoint;
	int buyingAmount;
	
	// listing_apply 조인
	String influencerId;
	char listingApprove;
	int listingPrice;
	
	// 합계
	int count;
	int sum;
	int avgPoint;
	
}

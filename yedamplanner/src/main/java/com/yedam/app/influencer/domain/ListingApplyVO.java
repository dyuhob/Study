package com.yedam.app.influencer.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ListingApplyVO {

	String listingNo;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	Date applyDate;
	String influencerId;
	char listingApprove;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	Date endDate;
	Date startDate;
	int stockAmount;
	int applicantAmount;
	int listingPrice;
}

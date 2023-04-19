package com.yedam.app.planner.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ReviewVO {
	String reviewID;
	String reviewContent;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	Date reviewDate;
	int reviewStar;
	String placeId;
	String reviewWriter;
	
	//리뷰 갯수
	int reviewCount;
	//별점 평균
	double avg;
	
	//리뷰 백분율
	int starPercent;
	
	//List<ReviewImgVO> imgList;
	String memberPhoto;
}

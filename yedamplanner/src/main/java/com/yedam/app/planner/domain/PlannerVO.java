package com.yedam.app.planner.domain;

import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class PlannerVO {
	//planner 테이블
	String planId;
	String planName;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd", timezone ="Asia/Seoul")
	Date planStart;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd", timezone ="Asia/Seoul")
	Date planEnd;
	int planMax;
	String planTag;
	char planClass;
	String hostId;
	char planFin;
	int planLike;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd", timezone ="Asia/Seoul")
	Date planDate;
	String city;
	String planCafe;
	String deldata;
	
	//플래너 후기 조회용 속성
	PlannerReviewVO prvo;
	List<MemberReviewVO> mrList;
	char planClose;
	String memberId;
	
	//메인화면 조회용
	String cityImg;
	String cityName;
	
	
}

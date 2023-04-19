package com.yedam.app.planner.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class TimetableVO {
	String programId;
	@DateTimeFormat(pattern="yyyy-MM-dd HH:mm")
	@JsonFormat(pattern="yyyy-MM-dd HH:mm", timezone ="Asia/Seoul")
	Date programStart;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd", timezone ="Asia/Seoul")
	Date programEnd;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JsonFormat(pattern="yyyy-MM-dd", timezone ="Asia/Seoul")
	Date programDate;
	String programPlace;
	String programTraffic;
	String programContent;
	int programPrice;
	String programWriter;
	String planId;
	String deldata;
}

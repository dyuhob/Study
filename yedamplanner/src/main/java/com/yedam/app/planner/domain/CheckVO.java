package com.yedam.app.planner.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class CheckVO {
	String CheckId;
	char checkCate;
	String writerId;
	String checkContent;
	char checkOrNot;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	Date checkWriteTime;
	String planId;
	
}

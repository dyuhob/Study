package com.yedam.app.seller.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class ProductVO {

	String packageId;
	String sellerId;
	int packagePrice;
	int packageNum;
	String planId;
	String planName;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	Date planStart;
	@DateTimeFormat(pattern="yyyy-MM-dd")
	Date planEnd;
}

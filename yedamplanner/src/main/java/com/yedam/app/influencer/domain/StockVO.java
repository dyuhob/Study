package com.yedam.app.influencer.domain;

import java.util.Date;

import lombok.Data;

@Data
public class StockVO {

	String stockId;
	String stockPrice;
	String stockQuantity;
	String marketValue;
	char stockDelisted;
	String stockIncrease;
	Date delistedDate;
	int allocation;
}

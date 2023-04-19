package com.yedam.app.influencer.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ExchangeVO {
	
	String exchangeNo;
	int exchangePrice;
	int exchangeAmount;
	String exchangeType;
	String stockId;
	String memberId;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	Date exchangeDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
	Date settleDate;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH시", timezone = "Asia/Seoul")
	Date todaychart;
	
	public String getExchangeType() {
		return exchangeType;
	}
}

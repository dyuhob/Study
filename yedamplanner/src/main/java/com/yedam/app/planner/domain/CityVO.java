package com.yedam.app.planner.domain;

import lombok.Data;

@Data
public class CityVO {
	//city 테이블
		String city;
		String cityName;
		double latitude;
		double longitude;
		String cityContent;
		String country;
		String cityImg;
	//rownum
		int num;
		int rownum;
		int count;
		String city_1;
}

package com.yedam.app.planner.domain;

import lombok.Data;

@Data
public class PlaceVO {
	//place 테이블
		String placeID;
		String placeName;
		String city;
		double latitude;
		double longitude;
		String placeContent;
		String placeCate;
		int placeLike;
		String placeImg;
		String placeAdd;
		String placePhone;
}

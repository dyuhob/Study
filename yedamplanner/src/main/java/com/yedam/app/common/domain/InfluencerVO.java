package com.yedam.app.common.domain;

import lombok.Data;

@Data
public class InfluencerVO {
	int rownum;
	String followId;
	int count;
	String followerId;
	
	//List<ReviewImgVO> imgList;
	String memberPhoto;
}

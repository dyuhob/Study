package com.yedam.app.follow.service;

import lombok.Data;

@Data
public class FollowVO {
	
	String followId; // 내가 상대에게 팔로우
	String followerId; // 상대가 나에게 팔로우
}

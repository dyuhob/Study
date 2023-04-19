package com.yedam.app.follow.service;

import java.util.List;

public interface FollowService {

	// 팔로우 추가
	void insertfollow (FollowVO followVO);
	// 팔로잉 체크(팔로우가 되어있나 안되어있나)
	boolean followCheck(FollowVO followVO);
	// 팔로우중인 리스트 ( 내가 상대에게 )
	List<String> followingList(String followerId);
	// 팔로워된 리스트 (상대가 나에게)
	List<String> followerList(String followId);
	// 언팔로우
	void unFollow (FollowVO followVO);
}

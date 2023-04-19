package com.yedam.app.follow.mapper;

import java.util.List;

import com.yedam.app.follow.service.FollowVO;

public interface FollowMapper {

	// 팔로우 추가
	int insertFollow(FollowVO followVO);
	// 팔로잉 체크(팔로우가 되어있나 안되어있나)
	int followCheck(FollowVO followVO);
	// 팔로우중인 리스트 (내가 상대에게)
	List<String> followingList(String followerId);
	// 팔로워된 리스트 (상대가 나에게)
	List<String> followerList(String followId);
	// 언팔로우
	void unFollow (FollowVO followVO);
}

package com.yedam.app.follow.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.follow.mapper.FollowMapper;

@Service
public class FollowServiceImpl implements FollowService{
	
	@Autowired
	FollowMapper followMapper;

	@Override
	public void insertfollow(FollowVO followVO) {
		followMapper.insertFollow(followVO);
	}

	@Override
	public boolean followCheck(FollowVO followVO) {
		int cnt = followMapper.followCheck(followVO);
		return cnt > 0;
	}

	@Override
	public List<String> followingList(String followerId) {
		return followMapper.followingList(followerId);
	}

	@Override
	public List<String> followerList(String followId) {
		return followMapper.followerList(followId);
	}

	@Override
	public void unFollow(FollowVO followVO) {
		followMapper.unFollow(followVO);
	}
	
	
}

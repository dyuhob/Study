package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.common.domain.PointVO;

public interface PointMapper {
	
	// 포인트 이력 입력
	public int pointHistory(PointVO vo);
	
	// 멤버 포인트 수정
	public int updatePoint(PointVO vo);
	
	// 포인트 이력 조회
	public List<PointVO> selectPointHistory(String memberId);
	
	// 공모 포인트 이력 조회	
	public PointVO selectOfferPoint(PointVO pvo);
	
	// 포인트 이력 수정
	public int updatePointHistory(PointVO vo);
}

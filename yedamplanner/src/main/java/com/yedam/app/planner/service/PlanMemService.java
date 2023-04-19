package com.yedam.app.planner.service;

import java.util.List;

import com.yedam.app.planner.domain.PlanMemVO;

public interface PlanMemService {

	//플래너 가입 신청
	public int planJoinReq(PlanMemVO vo);

	//가입 신청 목록
	public List<PlanMemVO> getPlanJoinList(String planId);

	//플래너 가입 승인(맴버 추가)
	public int insertMember(PlanMemVO vo);

	//플래너 가입 거절
	public int deleteReq(PlanMemVO vo);

	//플래너 맴버 조회
	public List<PlanMemVO> getPlanMemberList(String planId);

	//개설시 호스트 추가
	public int insertHost(PlanMemVO planMemVO);

	//맴버 강퇴
	public int deleteMember(String pmemKey);

	//플래너 종료
	public int endPlanner(String pmemKey);

	//호스트 이양
	public int changeHost(String pmemKey);
	public int dropHost(String memberId);
	public int changePlanHost(String planId, String memberId);

	//플래너 탈퇴
	public int quitPlanner(String memberId, String planId);
	
	//플래너 최종 종료처리
	public int endAllPlanner(String planId);



	
}

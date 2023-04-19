package com.yedam.app.planner.mapper;

import java.util.List;

import com.yedam.app.planner.domain.PlanMemVO;

public interface PlanMemMapper {

	public int planJoinReq(PlanMemVO vo);

	public List<PlanMemVO> getPlanJoinList(String planId);

	public int insertMember(PlanMemVO vo);

	public int deleteReq(PlanMemVO vo);

	public List<PlanMemVO> getPlanMemberList(String planId);

	public int insertHost(PlanMemVO planMemVO);

	public int deleteMember(String pmemKey);

	public int endPlanner(String pmemKey);

	public int changeHost(String pmemKey);

	public int dropHost(String memberId);

	public int quitPlanner(String memberId, String planId);

	public int changePlanHost(String planId, String memberId);

	public int endAllPlanner(String planId);


}

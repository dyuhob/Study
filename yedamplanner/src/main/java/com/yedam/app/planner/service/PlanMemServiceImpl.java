package com.yedam.app.planner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.planner.domain.PlanMemVO;
import com.yedam.app.planner.mapper.PlanMemMapper;

@Service
public class PlanMemServiceImpl implements PlanMemService{

	@Autowired
	PlanMemMapper planMemMapper;

	@Override
	public int planJoinReq(PlanMemVO vo) {
		
		int result = planMemMapper.planJoinReq(vo);
		
		return result;
	}

	@Override
	public List<PlanMemVO> getPlanJoinList(String planId) {
		return planMemMapper.getPlanJoinList(planId);
	}

	@Override
	public int insertMember(PlanMemVO vo) {
		
		int result = planMemMapper.insertMember(vo);
		
		return result;
	}

	@Override
	public int deleteReq(PlanMemVO vo) {
		
		int result = planMemMapper.deleteReq(vo);
		
		return result;
	}

	@Override
	public List<PlanMemVO> getPlanMemberList(String planId) {
		return planMemMapper.getPlanMemberList(planId);
	}

	@Override
	public int insertHost(PlanMemVO planMemVO) {
		
		int result = planMemMapper.insertHost(planMemVO);
		
		return result;
	}

	@Override
	public int deleteMember(String pmemKey) {
		
		int result = planMemMapper.deleteMember(pmemKey);
		
		return result;
	}

	@Override
	public int endPlanner(String pmemKey) {

		int result = planMemMapper.endPlanner(pmemKey);
		
		return result;
	}

	@Override
	public int changeHost(String pmemKey) {
		
		int result = planMemMapper.changeHost(pmemKey);
		
		return result;
	}

	@Override
	public int dropHost(String memberId) {

		int result = planMemMapper.dropHost(memberId);
		
		return result;
	}
	

	@Override
	public int changePlanHost(String planId, String memberId) {

		int result = planMemMapper.changePlanHost(planId, memberId);
		
		return result;
	}

	// 플래너 탈퇴
	@Override
	public int quitPlanner(String memberId, String planId) {
		
		int result = planMemMapper.quitPlanner(memberId, planId);
		
		return result;
	}

	@Override
	public int endAllPlanner(String planId) {
		
		int result = planMemMapper.endAllPlanner(planId);
		
		return result;
	}


}

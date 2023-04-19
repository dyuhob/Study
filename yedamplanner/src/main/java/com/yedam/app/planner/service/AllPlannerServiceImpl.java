package com.yedam.app.planner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlannerReviewVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.mapper.AllPlannerMapper;
import com.yedam.app.seller.domain.ProductVO;

@Service
public class AllPlannerServiceImpl implements AllPlannerService{

	@Autowired
	AllPlannerMapper allPlannerMapper;
	
	@Override
	public List<PlannerVO> getPlannerList() {
		return allPlannerMapper.selectPlannerList();
	}

	@Override
	public int insertPlannerInfo(PlannerVO plannerVO) {
		int result = allPlannerMapper.insertPlannerInfo(plannerVO);
		
		return result;
	}

	@Override
	public List<PlannerVO> getMyPlannerList(String hostId) {
		return allPlannerMapper.selectMyPlannerList(hostId);
	}

	@Override
	public List<PlannerVO> getPlannerPackageList() {
		return allPlannerMapper.selectPlanPackageList();
	}

	@Override
	public ProductVO payPackage(String planId) {
		return allPlannerMapper.payPackage(planId);
	}

	@Override
	public List<PlannerReviewVO> getPlannerReviewList() {
		return allPlannerMapper.getPlannerReviewList();
	}

	@Override
	public PlannerReviewVO getPlanReviewInfo(String planReviewId) {
		return allPlannerMapper.getPlanReviewInfo(planReviewId);
	}

	@Override
	public List<CityVO> getCityList() {
		return allPlannerMapper.getCityList();
	}

}

package com.yedam.app.planner.service;

import java.util.List;

import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlannerReviewVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.seller.domain.ProductVO;

public interface AllPlannerService {

	//플래너 목록 조회
	public List<PlannerVO> getPlannerList();

	//플래너 등록
	public int insertPlannerInfo(PlannerVO plannerVO);

	//내가 등록한 플래너 목록 조회
	public List<PlannerVO> getMyPlannerList(String hostId);

	public List<PlannerVO> getPlannerPackageList();

	//결제창
	public ProductVO payPackage(String planId);

	//플래너 리뷰조회
	public List<PlannerReviewVO> getPlannerReviewList();

	public PlannerReviewVO getPlanReviewInfo(String planReviewId);

	//도시 목록 출력
	public List<CityVO> getCityList();
}

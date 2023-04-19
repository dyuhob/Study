package com.yedam.app.planner.mapper;

import java.util.List;

import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.MemberReviewVO;
import com.yedam.app.planner.domain.PlannerReviewVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.domain.ReviewVO;
import com.yedam.app.seller.domain.ProductVO;

public interface AllPlannerMapper {

	// 플래너 리스트 조회
	public List<PlannerVO> selectPlannerList();

	// 플래너 등록
	public int insertPlannerInfo(PlannerVO plannerVO);
		
	// 내가 등록한 플래너 조회
	public List<PlannerVO> selectMyPlannerList(String hostId);

	// 패키지 리스트 조회
	public List<PlannerVO> selectPlanPackageList();
	
	// 참가한 플래너 조회
	public List<PlannerVO> selectJoinPlannerList(String memberId);
	
	// 내가 작성한 특정 플래너 리뷰 조회
	public PlannerReviewVO selectPlannerReview(PlannerVO vo);
	
	// 내가 작성한 특정 플래너 참여자 리뷰 조회
	public List<MemberReviewVO> selectMyMemberReviewList(PlannerVO vo);
	
	// 플래너 리뷰 등록, 수정
	public int insertPlannerReview(PlannerReviewVO vo);
	public int updatePlannerReview(PlannerReviewVO mrvo);
	
	// 플래너 멤버 리뷰 등록, 수정
	public int insertMemberReview(MemberReviewVO vo);
	public int updateMemberReview(MemberReviewVO vo);

	// 패키지 결제 페이지
	public ProductVO payPackage(String planId);

	// 플래너 리뷰 목록
	public List<PlannerReviewVO> getPlannerReviewList();

	// 플래너 리뷰 상세 조회
	public PlannerReviewVO getPlanReviewInfo(String planReviewId);

	// 도시 목록 출력
	public List<CityVO> getCityList();
}

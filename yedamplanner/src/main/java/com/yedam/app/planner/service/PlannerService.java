package com.yedam.app.planner.service;

import java.util.List;

import com.yedam.app.planner.domain.ReviewImgVO;
import com.yedam.app.planner.domain.BudgetVO;
import com.yedam.app.planner.domain.CheckVO;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlaceLikeVO;
import com.yedam.app.planner.domain.PlaceVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.domain.ReviewVO;

public interface PlannerService {
	
	//플래너 조회
	public PlannerVO getPlannerInfo(String planId);
	
	//여행지 리스트(지도마크)
	public List<PlaceVO> getPlace(String planId);
	
	//플래너 여행지 조회
	public CityVO getPlannerCity(String planId);	
	
	//인기 여행지 조회
	public List<PlaceVO> getPopularPlace(String planId);
	
	//추천 여행지 조회
	public List<PlaceVO> getRecommendPlace(String planId);
	
	//인기 플래너 조회
	public List<PlannerVO> getPopularPlanner(String planId);

	//플래너 단건 조회
	public PlaceVO getPlaceInfo(String placeId);
	
	//리뷰 리스트
	public List<ReviewVO> getReviewList(String placeId);
	
	//리뷰 이미지
	public List<ReviewImgVO> getReviewImgList(String placeId, String userId);
	
	//리뷰 갯수
	public int reviewCount(String placeId);
	
	//별점 평균
	public double starAvg(String placeId);
	
	//별점 백분율
	public List<ReviewVO> getStarPercent(String placeId);
	
	//찜 플레이스 리스트
	public List<PlaceVO> getLikePlaceList(String planId);
	
	//찜 플레이스 유무
	public PlaceLikeVO getLikePlace(String placeLikeId);
	
	//찜 플레이스 추가
	public int insertLikePlace(PlaceLikeVO vo);
	
	//찜 플레이스 삭제
	public int deleteLikePlace(String placeId, String planId);
	
	//개인 체크리스트 호출
	public List<CheckVO> getCheckList(String planId, String memberId);
	
	//전체 체크리스트 호출
	public List<CheckVO> getAllCheckList(String planId);
	
	//개인 체크리스트 추가
	public int addCheckList(String checkContent, String planId, char checkCate, String memberId);
	
	//checked 사항 수정
	public int updateChecked(String checkId);
	
	//수정중 상태 변경
	public int updatingAllCheck(String checkId);
	
	//개인 체크리스트 수정
	public int updateCheckContent(String checkId, String checkContent);
	//전체 체크리스트 수정
	public int updateAllCheckContent(String checkId, String checkContent, String userId);
	
	//체크리스트 삭제
	public int deleteCheckList(String checkId);
	
	//장부 리스트 호출
	public List<BudgetVO> getBugetList(String planId, String budgetDate);
	
	//장부 수정
	public int updateBudget(String budgetId, String className, String content);
	
	//장부 추가
	public int insertBudget(BudgetVO vo);
	
	//장부 선택 삭제
	public int deleteBudget(List<BudgetVO> vo);
	
	//일별 장부 합계
	public List<BudgetVO> dayBySum(List<BudgetVO> vo);

	

	
}

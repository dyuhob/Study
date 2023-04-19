package com.yedam.app.planner.mapper;

import java.util.List;

import com.yedam.app.planner.domain.BudgetVO;
import com.yedam.app.planner.domain.CheckVO;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlaceLikeVO;
import com.yedam.app.planner.domain.PlaceVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.domain.ReviewImgVO;
import com.yedam.app.planner.domain.ReviewVO;

public interface PlannerMapper {
	
	//플래너 조회
	public PlannerVO getPlannerInfo(String planId);
	
	//플래너 여행지 조회
	public CityVO getPlannerCity(String planId);
	
	//여행지 리스트(지도마크)
	public List<PlaceVO> getPlace(String planId);
	
	//인기 여행지 조회 >> 페이지네이션
	public List<PlaceVO> getPopularPlace(String planId);
	
	//여행지 정보 페이지로 이동	
	
	//추천 여행지 조회
	public List<PlaceVO> getRecommendPlace(String planId);
	
	//인기 플래너 조회
	public List<PlannerVO> getPopularPlanner(String planId);

	// 플래너 리스트 조회
	public List<PlannerVO> selectPlannerList();
	
	//장소 단건 조회
	public PlaceVO getPlaceInfo(String placeId);
	
	//리뷰 리스트
	public List<ReviewVO> getReviewList(String placeId);
	
	//리뷰 이미지
	public List<ReviewImgVO> getReviewImgList(String placeId, String userId);
	
	//리뷰 갯수
	public int reviewCount(String placeId);
	
	//별점 평균
	public double getStarAvg(String placeId);
	
	//별점 백분율
	public List<ReviewVO> getStarPercent(String placeId);
	
	//찜 플레이스
	public List<PlaceLikeVO> getLikePlaceList(String planId);
	
	//찜 유무
	public PlaceLikeVO getLikePlace(String placeLikeId);
	
	//찜 추가
	public int insertLikePlace(PlaceLikeVO vo);
	
	//찜 삭제
	public int deleteLikePlace(String placeId, String planId);
	
	//개인 체크리스트 호출
	public List<CheckVO> getCheckList(String planId, String memberId);
	
	//전체 체크리스트 호출
	public List<CheckVO> getAllCheckList(String planId);
	
	//체크리스트 추가
	public int addCheckList(CheckVO vo);
	
	//체크리스트 갯수
	public int countCheckList(CheckVO vo);
	
	//checked 수정
	public int updateChecked(CheckVO vo);
	
	//체크리스트 단건 조회
	public CheckVO selectCheck(String checkId);
	
	//수정중 상태 변경
	public int updatingCheck(String checkId);
	
	//개인 체크리스트 변경
	public int updateCheckContent(String checkId, String checkContent);
	public int updateAllCheckContent(String checkId, String checkContent, String userId);
	
	//체크리스트 삭제
	public int deleteCheckList(String checkId);
	
	//장부 리스트 호출
	public List<BudgetVO> getBudgetList(String planId, String budgetDate);
	
	//장부 수정
	public int updateBudget(String budgetId, String column, String content);
	
	//장부 추가
	public int insertBudget(BudgetVO vo);
	
	//장부 삭제
	public int deleteBudget(String budgetId);
	
	//일별 합계
	public BudgetVO dayBySum(String budgetDate, String planId);

	
	
}

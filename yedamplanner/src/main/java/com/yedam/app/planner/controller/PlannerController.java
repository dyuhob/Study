package com.yedam.app.planner.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.planner.domain.BudgetVO;
import com.yedam.app.planner.domain.CheckVO;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlaceLikeVO;
import com.yedam.app.planner.domain.PlaceVO;
import com.yedam.app.planner.domain.PlanMemVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.domain.ReviewImgVO;
import com.yedam.app.planner.domain.ReviewVO;
import com.yedam.app.planner.service.PlanMemService;
import com.yedam.app.planner.service.PlannerService;
import com.yedam.app.planner.service.TimetableService;

import lombok.AllArgsConstructor;

@Controller
@AllArgsConstructor
@RequestMapping("/planner/*")
public class PlannerController {
	
	@Autowired
	PlannerService plannerService;
	
	@Autowired
	TimetableService timetableService;
	
	@Autowired
	PlanMemService planMemService;
	
	//플래너 조회
	@RequestMapping(value="/main/{planId}", method=RequestMethod.GET)
	public String PlannerStart(@PathVariable String planId, Model model, HttpSession session) {
		String memberId = (String) session.getAttribute("id");
		model.addAttribute("userId", memberId);
		//플래너 정보 조회
		PlannerVO plannerVO = plannerService.getPlannerInfo(planId);
		model.addAttribute("plannerInfo", plannerVO);
		//플래너 도시 조회
		CityVO cityVO = plannerService.getPlannerCity(planId);
		model.addAttribute("cityInfo", cityVO);
		//여행지 리스트(지도마크)
		List<PlaceVO> placeMarkList = plannerService.getPlace(planId);
		model.addAttribute("placeList", placeMarkList);
		//인기 여행지 출력 >> 페이지 네이션
		List<PlaceVO> placeList = plannerService.getPopularPlace(planId);
		model.addAttribute("popularPlaces", placeList);
		//추천 여행지
		List<PlaceVO> recomList = plannerService.getRecommendPlace(planId);
		model.addAttribute("recommendPlaces", recomList);
		//인기 플래너
		List<PlannerVO> plannerList = plannerService.getPopularPlanner(planId);
		model.addAttribute("popularPlanner", plannerList);
		//플래너 맴버 정보
		List<PlanMemVO> list = planMemService.getPlanMemberList(planId);
		model.addAttribute("planMemberList", list);
		//좋아요한 장소
		List<PlaceVO> likeList = plannerService.getLikePlaceList(planId);
	    model.addAttribute("likeList", likeList);
	    //플래너 가입요청
		List<PlanMemVO> joinList = planMemService.getPlanJoinList(planId);
		model.addAttribute("planJoinList", joinList);
		
		return "page/planner/planner-startpage";
	}
	
	@RequestMapping(value="/placeDetail")
	@ResponseBody
	public HashMap<String, Object> PlaceDetail(@RequestBody HashMap<String, String> map) {
		HashMap<String, Object> list = new HashMap<String, Object>();
		String placeId = map.get("placeId");
		
		//여행지 단건 조회
		PlaceVO placeVO = plannerService.getPlaceInfo(placeId);
		//후기 리스트
		List<ReviewVO> reviewList = plannerService.getReviewList(placeId);
		//후기 갯수
		int reviewCount = plannerService.reviewCount(placeId);
		//별점 평균
		double starAvg = plannerService.starAvg(placeId);
	    //별점 백분율
	    List<ReviewVO> starPercent = plannerService.getStarPercent(placeId);
	      
	    list.put("placeInfo", placeVO);
	    list.put("reviewList", reviewList);
	    list.put("reviewCount", reviewCount);
	    list.put("starAvg", starAvg);
	    list.put("starPercent", starPercent);
	    return list;
	}
	
	 //리뷰 후기 이미지
	   @RequestMapping(value="/reviewImg", method=RequestMethod.GET)
	   @ResponseBody
	   public List<ReviewImgVO> ReviewImg(@RequestParam(value="placeId") String placeId, 
			                              @RequestParam(value="userId") String userId){
	      //후기 이미지 정보
	      List<ReviewImgVO> reImgList = plannerService.getReviewImgList(placeId, userId);
	      
	      return reImgList;   
	   };
	   
	   //찜 플레이스
	   @RequestMapping(value="/placeLike", method=RequestMethod.GET)
	   @ResponseBody
	   public List<PlaceVO> PlaceLike(@RequestParam(value="planId") String planId) {
	      //찜 플레이스 리스트
	      List<PlaceVO> likeList = plannerService.getLikePlaceList(planId);
	      return likeList;
	   };
	   
	   //찜 플레이스 추가
	   @RequestMapping(value="/insertLikePlace", method=RequestMethod.POST)
	   @ResponseBody
	   public int insertPlaceLike(@RequestParam(value="planId") String planId, 
			                      @RequestParam(value="placeId") String placeId){
	      
	      //찜 플레이스 추가
	      PlaceLikeVO vo = new PlaceLikeVO();
	      //복합키가 더 나음
	      vo.setPlaceLikeId(placeId+"/"+planId);
	      vo.setPlanId(planId);
	      vo.setPlaceId(placeId);
	      
	      int result = plannerService.insertLikePlace(vo);
	      
	      return result;
	   }
	   
	   //찜 플레이스 삭제
	   @RequestMapping(value="/deleteLikePlace", method=RequestMethod.POST)
	   @ResponseBody
	   public int deletePlaceLike(@RequestParam(value="placeId") String placeId, String planId) {
		   
		   int result = plannerService.deleteLikePlace(placeId, planId);		   
		   return result;
	   }
	   
	   //찜 플레이스 후기 리스트
	   @RequestMapping(value="/likePlaceReview", method=RequestMethod.GET)
	   @ResponseBody
	   public List<ReviewVO> likePlaceReview(@RequestParam(value="placeId") String placeId){
		   List<ReviewVO> list = plannerService.getReviewList(placeId);
		   return list;
	   }
	   
	   //체크리스트
	   //개인 체크리스트 호출
	   @RequestMapping(value="/checkList", method=RequestMethod.GET)
	   @ResponseBody
	   public List<CheckVO> checkList(@RequestParam("planId") String planId, HttpSession session){
			String memberId = (String) session.getAttribute("id");
		   List<CheckVO> checkList = plannerService.getCheckList(planId, memberId);
		   
		   return checkList;   
	   }
	   //전체 체크리스트 호출
	   @RequestMapping(value="/allCheckList", method=RequestMethod.GET)
	   @ResponseBody
	   public List<CheckVO> allCheckList(String planId){
		   List<CheckVO> allCheckList = plannerService.getAllCheckList(planId);
		   return allCheckList;
	   }  
	   //전체 체크리스트 추가
	   @RequestMapping(value="/addAllCheckList", method=RequestMethod.POST)
	   @ResponseBody
	   public int addAllCheckList(String checkContent, String planId, char checkCate, String memberId) {
		   int result = plannerService.addCheckList(checkContent, planId, checkCate, memberId);
		   return result;			   
	   }
	   
	   //개인 체크리스트 추가
	   @RequestMapping(value="/addCheckList", method=RequestMethod.POST)
	   @ResponseBody
	   public int addCheckList(String checkContent, String planId, char checkCate, HttpSession session) {
		   String memberId = (String) session.getAttribute("id");
		   int result = plannerService.addCheckList(checkContent, planId, checkCate, memberId);
		   return result;			   
	   }
	   
	   //checked or not
	   @RequestMapping(value="/updateChecked", method=RequestMethod.POST)
	   @ResponseBody
	   public int updateChecked(String checkId) {
		   int result = plannerService.updateChecked(checkId);
		   return result;
	   }
	   //수정중 상태 변경
	   @RequestMapping(value="/updatingCheck", method=RequestMethod.POST)
	   @ResponseBody
	   public int updatingCheck(String checkId) {
		   int result = plannerService.updatingAllCheck(checkId);
		   return result;
	   }
	   
	   
	   //체크박스 내용 수정
	   @RequestMapping(value="/updateCheckContent", method=RequestMethod.POST)
	   @ResponseBody
	   public int updateCheckContent(String checkId, String checkContent) {
		   int result = plannerService.updateCheckContent(checkId, checkContent);
		   return result;
	   }
	   //체크박스 전체 내용 수정
	   @RequestMapping(value="/updateAllCheckContent", method=RequestMethod.POST)
	   @ResponseBody
	   public int updateAllCheckContent(String checkId, String checkContent, String userId) {
		   int result = plannerService.updateAllCheckContent(checkId, checkContent, userId);
		   return result;
	   }
	   //체크박스 삭제
	   @RequestMapping(value="/deleteCheckList", method=RequestMethod.POST)
	   @ResponseBody
	   public int deleteCheckList(String checkId) {
		   int result = plannerService.deleteCheckList(checkId);
		   return result;
	   }
	   
	   //장부 정보 가지고 오기
	    @RequestMapping(value="/budgetList", method=RequestMethod.GET)
		@ResponseBody
		public List<BudgetVO> budgetList(String planId, String budgetDate) {
			List<BudgetVO> budgetList = plannerService.getBugetList(planId, budgetDate);
			return budgetList;
	    }
	   
	    //장부 정보 수정
	    @RequestMapping(value="updateBudget", method=RequestMethod.POST)
	    @ResponseBody
	    public int updateBudget(String budgetId, String className, String content) {
	    	int result = plannerService.updateBudget(budgetId, className, content);
	    	return result;
	    }
	    
	    //장부 추가
	    @RequestMapping(value="insertBudget", method=RequestMethod.POST)
	    @ResponseBody
	    public int insertBudget(BudgetVO vo) {
	    	System.out.println(vo);
	    	int result = plannerService.insertBudget(vo);
	    	return result;
	    }
	    //장부 정보 삭제
	    @RequestMapping(value="deleteBudget",  method=RequestMethod.POST)
	    @ResponseBody
	    //배열을 객체로 싸서... 어쩌구 저쩌구...
	    public HashMap<String, Object> deleteBudget(@RequestBody List<BudgetVO> vo) {
	    	HashMap<String, Object> map = new HashMap<String, Object>();
	    	int result = plannerService.deleteBudget(vo);
	    	map.put("result", result);
	    	return map;
	    }
	    //장부 날짜별 합계
	    @RequestMapping(value="dayBySum", method=RequestMethod.POST)
	    @ResponseBody
	    public List<BudgetVO> dayBySum(@RequestBody List<BudgetVO> vo) {
	    	List<BudgetVO> sumList = plannerService.dayBySum(vo);
	    	return sumList;
	    }
	    
	    //여행지 단건 조회
	    @RequestMapping(value="getPlace", method=RequestMethod.POST)
	    @ResponseBody
	    public PlaceVO getPlace(String placeId) {
	    	PlaceVO vo = plannerService.getPlaceInfo(placeId);
	    	return vo;
	    }
	    
}	

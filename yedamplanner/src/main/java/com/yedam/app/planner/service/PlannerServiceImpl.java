package com.yedam.app.planner.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.common.mapper.MemberMapper;
import com.yedam.app.planner.domain.BudgetVO;
import com.yedam.app.planner.domain.CheckVO;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlaceLikeVO;
import com.yedam.app.planner.domain.PlaceVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.domain.ReviewImgVO;
import com.yedam.app.planner.domain.ReviewVO;
import com.yedam.app.planner.mapper.PlannerMapper;

@Service
public class PlannerServiceImpl implements PlannerService{
	
	@Autowired
	PlannerMapper plannerMapper;
	@Autowired
	MemberMapper memberMapper;
	
	@Override
	public PlannerVO getPlannerInfo(String planId) {
		return plannerMapper.getPlannerInfo(planId);
	}
	
	@Override
	public List<PlaceVO> getPlace(String planId) {
		return plannerMapper.getPlace(planId);
	}

	@Override
	public CityVO getPlannerCity(String planId) {
		return plannerMapper.getPlannerCity(planId);
	}
	
	@Override
	public List<PlaceVO> getPopularPlace(String planId) {
		return plannerMapper.getPopularPlace(planId);
	}


	@Override
	public List<PlaceVO> getRecommendPlace(String planId) {
		return plannerMapper.getRecommendPlace(planId);
	}

	@Override
	public List<PlannerVO> getPopularPlanner(String planId) {
		return plannerMapper.getPopularPlanner(planId);
	}


	@Override
	public PlaceVO getPlaceInfo(String placeId) {
		return plannerMapper.getPlaceInfo(placeId);
	}

	@Override
	public List<ReviewVO> getReviewList(String placeId) {
		List<ReviewVO> list = plannerMapper.getReviewList(placeId);
		for(ReviewVO rvo : list ) {
			String photo = memberMapper.memberPhoto(rvo.getReviewWriter());
			rvo.setMemberPhoto(photo);
		}
		//for(int i = 0; i < list.size(); i++) {
		//	list.get(i).setImgList(plannerMapper.getReviewImgList(placeId, placeId));
		//}
		return list;
	}

	@Override
	public List<ReviewImgVO> getReviewImgList(String city, String userId) {
		return plannerMapper.getReviewImgList(city, userId);
	}

	@Override
	public int reviewCount(String placeId) {
		return plannerMapper.reviewCount(placeId);
	}

	@Override
	public double starAvg(String placeId) {
		return plannerMapper.getStarAvg(placeId);
	}

	@Override
	public List<ReviewVO> getStarPercent(String placeId) {
		return plannerMapper.getStarPercent(placeId);
	}
	
	@Transactional
	@Override
	public List<PlaceVO> getLikePlaceList(String planId) {
		List<PlaceLikeVO> vo = plannerMapper.getLikePlaceList(planId);
		List<PlaceVO> placeList = new ArrayList<PlaceVO>();
			for(PlaceLikeVO place: vo) {
				PlaceVO placeInfo = plannerMapper.getPlaceInfo(place.getPlaceId());
				placeList.add(placeInfo);
			}
		return placeList;
	}
	@Override
	public PlaceLikeVO getLikePlace(String placeLikeId) {
		return plannerMapper.getLikePlace(placeLikeId);
	}

	@Override
	public int insertLikePlace(PlaceLikeVO vo) {
		//조회?
		int result;
		PlaceLikeVO like = plannerMapper.getLikePlace(vo.getPlaceId()+'/'+vo.getPlanId());
		if (like == null) {
			result = plannerMapper.insertLikePlace(vo);
		}else {
			result = 2;
		}
		return result;
	}

	@Override
	public int deleteLikePlace(String placeId, String planId) {
		return plannerMapper.deleteLikePlace(placeId, planId);
	}

	@Override
	public List<CheckVO> getCheckList(String planId, String memberId) {
		return plannerMapper.getCheckList(planId, memberId);
	}

	@Override
	public List<CheckVO> getAllCheckList(String planId) {
		return plannerMapper.getAllCheckList(planId);
	}

	@Override
	public int addCheckList(String checkContent, String planId, char checkCate, String memberId) {
		CheckVO vo = new CheckVO();
		vo.setWriterId(memberId);
		vo.setCheckContent(checkContent);
		vo.setPlanId(planId);
		vo.setCheckCate(checkCate);
		System.out.println(vo);
		//int number = plannerMapper.countCheckList(vo)+1;
		vo.setCheckId(planId+'/'+memberId+'/'+checkCate);
		return plannerMapper.addCheckList(vo);
	}

	@Override
	public int updateChecked(String checkId) {
		CheckVO vo = plannerMapper.selectCheck(checkId);
		System.out.println(vo);
		int result = 0;
		if (vo.getCheckOrNot() == 'C') {
			vo.setCheckOrNot('N');
			result = plannerMapper.updateChecked(vo);
		}else if (vo.getCheckOrNot() == 'N') {
			vo.setCheckOrNot('C');
			result = plannerMapper.updateChecked(vo);
		}
		return result;
	}

	@Override
	public int updatingAllCheck(String checkId) {
		CheckVO vo = plannerMapper.selectCheck(checkId);
		int result = 0;
		if(vo == null) {
			result = plannerMapper.updatingCheck(checkId);
		}else if (vo != null) {
			result = 2;
		}
		
		return result;
	}

	@Override
	public int updateCheckContent(String checkId, String checkContent) {
		int result = plannerMapper.updateCheckContent(checkId, checkContent);
		return result;
	}

	@Override
	public List<BudgetVO> getBugetList(String planId, String budgetDate) {
		return plannerMapper.getBudgetList(planId, budgetDate);
	}

	@Override
	public int updateBudget(String budgetId, String className, String content) {
		String column = null;
		//문자열 비교시
		//className == "info" X
		//className.equals("memo") O
		if (className.equals("info")) {
			column = "budget_list";
			System.out.println(column);
		}else if (className.equals("won")) {
			column = "budget_price";
		}else if (className.equals("detail")) {
			column = "budget_content";
		}else if (className.equals("memo")) {
			column = "budget_memo";
		}
		int result = plannerMapper.updateBudget(budgetId, column, content);
		return result;
	}

	@Override
	public int insertBudget(BudgetVO vo) {
		vo.setBudgetId(vo.getPlanId()+"/B");
		if (vo.getBudgetContent() == null) {
			vo.setBudgetContent("");
		}
		if (vo.getBudgetMemo() == null) {
			vo.setBudgetMemo("");
		}
		if (vo.getBudgetPrice() == 0) {
			vo.setBudgetPrice(0);
		}
	
		int result = plannerMapper.insertBudget(vo);
		return result;
	}
	
	@Transactional
	@Override
	public int deleteBudget(List<BudgetVO> vo) {
		//List<BudgetVO> list = new ArrayList<BudgetVO>(vo);
		int result = 0;
		for(int i = 0; i < vo.size(); i++) {
			result += plannerMapper.deleteBudget(vo.get(i).getBudgetId());
		}
		//전부 다 되야지 결과 값 1
		int conclusion = 0;
		if(result == vo.size()) {
			conclusion = 1;
		}else {
			conclusion = 0;
		}
		
		return conclusion;
	}

	@Override
	public int updateAllCheckContent(String checkId, String checkContent, String userId) {
		return plannerMapper.updateAllCheckContent(checkId, checkContent, userId);
	}

	@Override
	public List<BudgetVO> dayBySum(List<BudgetVO> vo) {
		List<BudgetVO> sumList = new ArrayList<BudgetVO>();
		for(int i = 0; i < vo.size()-1; i++) {
			BudgetVO sum = plannerMapper.dayBySum(vo.get(i).getBudgetDate() , vo.get(vo.size()-1).getPlanId());
			sum.setBudgetDate(vo.get(i).getBudgetDate());
			sumList.add(sum);
		}
		return sumList;
	}

	@Override
	public int deleteCheckList(String checkId) {
		return plannerMapper.deleteCheckList(checkId);
	}






}

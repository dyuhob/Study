package com.yedam.app.user.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.PointVO;
import com.yedam.app.common.mapper.PointMapper;
import com.yedam.app.planner.domain.MemberReviewVO;
import com.yedam.app.planner.domain.PlannerReviewVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.mapper.AllPlannerMapper;
import com.yedam.app.user.mapper.UserMapper;

import lombok.Setter;

@Service
public class UserServiceImpl implements UserService{
	
	@Setter(onMethod_= @Autowired)
	UserMapper userMapper;
	@Autowired
	PointMapper pointMapper;
	@Autowired
	AllPlannerMapper allPlannerMapper;
	
	@Override
	public int modifyMemberInfo(MemberVO vo) {
		// TODO Auto-generated method stub
		return userMapper.modifyMemberInfo(vo);
	}

	@Override
	public int modifyPw(MemberVO vo) {
		// TODO Auto-generated method stub
		return userMapper.modifyPw(vo);
	}

	@Override
	public List<PointVO> selectPointHistory(String memberId) {
		// TODO Auto-generated method stub
		return pointMapper.selectPointHistory(memberId);
	}

	@Override
	public List<PlannerVO> selectMyPlanner(String memberId) {
		// TODO Auto-generated method stub
		
		List<PlannerVO> lists = allPlannerMapper.selectJoinPlannerList(memberId);
		for(PlannerVO list : lists) {
	
			PlannerReviewVO prvo = allPlannerMapper.selectPlannerReview(list);
			if(prvo == null) {
				prvo = new PlannerReviewVO();
			}
			prvo.setRater(memberId);
			list.setPrvo(prvo);
			list.setMrList(allPlannerMapper.selectMyMemberReviewList(list));
		}
		return lists;
	}

	@Override
	public int insertPlannerReview(PlannerReviewVO vo) {
		// TODO Auto-generated method stub
		if(vo.getPlanReviewId().equals("")) {
			return allPlannerMapper.insertPlannerReview(vo);
		} else {
			return allPlannerMapper.updatePlannerReview(vo);
		}
	}
	
	@Transactional
	@Override
	public int insertMemberReview(List<MemberReviewVO> list) {
		
		int result = 0;
		for(MemberReviewVO mrvo : list) {
			System.out.println(mrvo);
			if(mrvo.getMrKey().equals("")) {
				result += allPlannerMapper.insertMemberReview(mrvo);
			} else {
				result += allPlannerMapper.updateMemberReview(mrvo);
			}
		}
		if(result == list.size()) {
			return 1;
		} else {
			return 0;
		}
		
	}

}

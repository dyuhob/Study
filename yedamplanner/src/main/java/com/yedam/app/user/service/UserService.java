package com.yedam.app.user.service;

import java.util.List;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.PointVO;
import com.yedam.app.planner.domain.MemberReviewVO;
import com.yedam.app.planner.domain.PlannerReviewVO;
import com.yedam.app.planner.domain.PlannerVO;

public interface UserService {
	public int modifyMemberInfo(MemberVO vo);
	public int modifyPw(MemberVO vo);
	List<PointVO> selectPointHistory(String memberId);
	List<PlannerVO> selectMyPlanner(String memberId);
	int insertPlannerReview(PlannerReviewVO vo);
	int insertMemberReview(List<MemberReviewVO> list);
}

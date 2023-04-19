package com.yedam.app.common.service;

import java.util.HashMap;
import java.util.List;

import com.yedam.app.common.domain.InfluencerVO;
import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.ReportVO;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlannerVO;

public interface CommonService {
	
	public MemberVO getMemberInfo(String id);
	public int signup(MemberVO vo);
	public int kakaoSignup(MemberVO vo);
	public int sellerSignup(MemberVO vo);
	public MemberVO getMemberWithCrn(String crn);
	public MemberVO getMemberWithSsh(String ssh);
	List<MemberVO> allMemberList();
	public HashMap<String, String> insertReport(ReportVO vo);
	public List<MemberVO> forgot(MemberVO vo);
	public int temporaryPw(MemberVO vo);
	public List<CityVO> getTravelPlace();
	public List<InfluencerVO> getInfluencerList();
	public List<PlannerVO> getPlanList();
}

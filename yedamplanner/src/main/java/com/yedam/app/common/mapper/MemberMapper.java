package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.common.domain.InfluencerVO;
import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.ReportVO;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlannerVO;

public interface MemberMapper {
	
	public MemberVO getMemberInfo(String id);

	public int insertMember(MemberVO vo);
	
	public int kakaoInsertMember(MemberVO vo);
	
	public int sellerInsertMember(MemberVO vo);
	
	public MemberVO getMemberWithCrn(String crn);
	
	public MemberVO getMemberWithSsh(String ssh);
	
	List<MemberVO> allMemberList();
	
	List<MemberVO> getBlackList();
	
	int insertBlackList(ReportVO vo);
	
	List<MemberVO> forgot(MemberVO vo);
	
	int temporaryPw(MemberVO vo);

	public List<CityVO> getTravelPlace();

	public List<InfluencerVO> getInfluencerList();

	public List<PlannerVO> getPlanList();
	
	public String memberPhoto(String id);
}

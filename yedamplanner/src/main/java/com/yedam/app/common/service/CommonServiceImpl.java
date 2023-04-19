package com.yedam.app.common.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.common.domain.InfluencerVO;
import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.ReportVO;
import com.yedam.app.common.mapper.MemberMapper;
import com.yedam.app.common.mapper.PointMapper;
import com.yedam.app.common.mapper.ReportMapper;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.domain.ReviewVO;

@Service
public class CommonServiceImpl implements CommonService, UserDetailsService {

	@Autowired
	MemberMapper memberMapper;
	@Autowired
	PointMapper pointMapper;
	@Autowired
	ReportMapper reportMapper;
	
	@Override
	public MemberVO getMemberInfo(String id) {
		// TODO Auto-generated method stub
		return memberMapper.getMemberInfo(id);
	}

	@Override
	public int signup(MemberVO vo) {
		// TODO Auto-generated method stub
		return memberMapper.insertMember(vo);
	}
	
	@Override
	public int kakaoSignup(MemberVO vo) {
		// TODO Auto-generated method stub
		return memberMapper.kakaoInsertMember(vo);
	}
	
	@Override
	public int sellerSignup(MemberVO vo) {
		// TODO Auto-generated method stub
		return memberMapper.sellerInsertMember(vo);
	}
	
	@Override
	public MemberVO getMemberWithCrn(String crn) {
		// TODO Auto-generated method stub
		return memberMapper.getMemberWithCrn(crn);
	}

	@Override
	public MemberVO getMemberWithSsh(String ssh) {
		// TODO Auto-generated method stub
		return memberMapper.getMemberWithSsh(ssh);			
	}
	
	
	// 시큐리티
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return memberMapper.getMemberInfo(username);
	}
	
	@Transactional
	@Override
	public HashMap<String, String> insertReport(ReportVO vo) {
		// TODO Auto-generated method stub
		HashMap<String, String> map = new HashMap<String, String>();
		if(reportMapper.selectReport(vo) != null) {
			map.put("result", "dup");
		} else {
			if(reportMapper.insertReport(vo) == 1) {
				map.put("result", "success");
			} else {
				map.put("result", "fail");
			}
		}
		return map;
	}

	@Override
	public List<MemberVO> allMemberList() {
		// TODO Auto-generated method stub
		return memberMapper.allMemberList();
	}

	@Override
	public List<MemberVO> forgot(MemberVO mvo) {
		// TODO Auto-generated method stub
		return memberMapper.forgot(mvo);
	}

	@Override
	public int temporaryPw(MemberVO vo) {
		// TODO Auto-generated method stub
		return memberMapper.temporaryPw(vo);
	}

	@Override
	public List<CityVO> getTravelPlace() {
		return memberMapper.getTravelPlace();
	}

	@Override
	public List<InfluencerVO> getInfluencerList() {
		List<InfluencerVO> list =  memberMapper.getInfluencerList();
		for(InfluencerVO rvo : list ) {
			String photo = memberMapper.memberPhoto(rvo.getFollowId());
			rvo.setMemberPhoto(photo);
		}
		return list;
	}

	@Override
	public List<PlannerVO> getPlanList() {
		return memberMapper.getPlanList();
	}

}

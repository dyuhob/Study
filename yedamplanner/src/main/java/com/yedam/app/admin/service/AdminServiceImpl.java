package com.yedam.app.admin.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.admin.mapper.AdminMapper;
import com.yedam.app.common.domain.Criteria;
import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.ReportVO;
import com.yedam.app.common.mapper.MemberMapper;
import com.yedam.app.common.mapper.ReportMapper;
import com.yedam.app.influencer.domain.ConvertVO;
import com.yedam.app.influencer.domain.InfluencerApplyVO;
import com.yedam.app.influencer.domain.ListingApplyVO;
import com.yedam.app.sns.mapper.SnsMapper;
import com.yedam.app.sns.service.SnsVO;


@Service
public class AdminServiceImpl implements AdminService{
	
	@Autowired
	AdminMapper adminMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	ReportMapper reportMapper;
	@Autowired
	SnsMapper snsMapper;

	@Override
	public List<ListingApplyVO> selectListingInfo(Criteria cri) {
		// TODO Auto-generated method stub
		return adminMapper.selectListingInfo(cri);
	}
	
	@Transactional
	@Override
	public int approveListingStock(int no) {
		// TODO Auto-generated method stub
		try {
			adminMapper.approveListingApply(no);
			return 1;
		} catch (Exception e) {
			return 0;
		}
		
	}

	@Override
	public int rejectListingStock(int no) {
		// TODO Auto-generated method stub
		return adminMapper.rejectListingApply(no);
	}

	@Override
	public List<InfluencerApplyVO> selectInfluencerInfo() {
		// TODO Auto-generated method stub
		return adminMapper.selectInfluencerInfo();
	}

	@Override
	public int approveInfluencer(int no) {
		// TODO Auto-generated method stub
		return adminMapper.approveInfluencer(no);
	}

	@Override
	public int rejectInfluencer(int no) {
		// TODO Auto-generated method stub
		return adminMapper.rejectInfluencer(no);
	}
	
	@Transactional
	@Override
	public int completeListing(int no) {
		// TODO Auto-generated method stub
		adminMapper.completeListing(no);
		return adminMapper.endListing(no);
	}
	
	@Override
	public List<ConvertVO> allConvertList() {
		// TODO Auto-generated method stub
		return adminMapper.allConvertList();
	}

	@Override
	public int totalAllListing() {
		// TODO Auto-generated method stub
		return adminMapper.totalAllListing();
	}

	@Override
	public List<MemberVO> getBlackList() {
		// TODO Auto-generated method stub
		
		List<MemberVO> list = memberMapper.getBlackList();
		for(MemberVO mvo : list) {
			mvo.setRvo(reportMapper.getReportedInfo(mvo));
		}
		return list; 
	}

	@Override
	public List<ReportVO> getReportList() {
		// TODO Auto-generated method stub
		return reportMapper.getReportList();
	}
	
	@Transactional
	@Override
	public int reportApprove(ReportVO vo) {
		// TODO Auto-generated method stub
		int result = 0;
		result += reportMapper.approveReport(vo);
		result += memberMapper.insertBlackList(vo);
		/*if(vo.getReportPost().substring(0,1).equals("sp")) {
			SnsVO svo = new SnsVO();
			svo.setSnsPostNum(0);
			result += snsMapper.deletePost(svo);
		} */
		
		return result;
	}

	@Override
	public int reportReject(ReportVO vo) {
		// TODO Auto-generated method stub
		return reportMapper.rejectReport(vo);
	}


}

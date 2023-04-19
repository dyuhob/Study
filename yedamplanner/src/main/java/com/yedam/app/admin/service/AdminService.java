package com.yedam.app.admin.service;

import java.util.HashMap;
import java.util.List;

import com.yedam.app.common.domain.Criteria;
import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.ReportVO;
import com.yedam.app.influencer.domain.ConvertVO;
import com.yedam.app.influencer.domain.InfluencerApplyVO;
import com.yedam.app.influencer.domain.ListingApplyVO;

public interface AdminService {
	
	List<ListingApplyVO> selectListingInfo(Criteria cri);
	int approveListingStock(int no);
	int rejectListingStock(int no);
	List<InfluencerApplyVO> selectInfluencerInfo();
	int approveInfluencer(int no);
	int rejectInfluencer(int no);
	int completeListing(int no);
	List<MemberVO> getBlackList();
	List<ReportVO> getReportList();
	List<ConvertVO> allConvertList();
	int totalAllListing();
	int reportApprove(ReportVO vo);
	int reportReject(ReportVO vo);
}

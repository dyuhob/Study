package com.yedam.app.admin.mapper;

import java.util.List;

import com.yedam.app.common.domain.Criteria;
import com.yedam.app.common.domain.ReportVO;
import com.yedam.app.influencer.domain.ConvertVO;
import com.yedam.app.influencer.domain.InfluencerApplyVO;
import com.yedam.app.influencer.domain.ListingApplyVO;


public interface AdminMapper {
	
	List<ListingApplyVO> selectListingInfo(Criteria cri);
	int approveListingApply(int no);
	int rejectListingApply(int no);
	List<InfluencerApplyVO> selectInfluencerInfo();
	int approveInfluencer(int no);
	int rejectInfluencer(int no);
	int completeListing(int no);
	int endListing(int no);
	List<ConvertVO> allConvertList();
	int totalAllListing();
}

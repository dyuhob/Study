package com.yedam.app.influencer.service;

import java.util.HashMap;
import java.util.List;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.influencer.domain.ConvertVO;
import com.yedam.app.influencer.domain.ExchangeVO;
import com.yedam.app.influencer.domain.HolderVO;
import com.yedam.app.influencer.domain.InfluencerApplyVO;
import com.yedam.app.influencer.domain.ListingApplyVO;
import com.yedam.app.influencer.domain.OfferingVO;

public interface InfluencerService {
	
	int insertListing(String id);
	List<ListingApplyVO> myListingInfo(String id);
	List<ExchangeVO> stockgraph(String id, String type);
	HashMap<String, Object> askgraph(String id);
	int insertExchange(ExchangeVO vo);
	List<InfluencerApplyVO> selectApplyInfo(String id);
	int insertInfluencer(String id);
	List<MemberVO> getInfluencerList();
	List<ListingApplyVO> ProgressLisintg();
	ListingApplyVO getListingInfo(String id);
	List<OfferingVO> getMyOfferingList(String id);
	OfferingVO sumOfferingInfo(String listingNo);
	OfferingVO getMyOfferingInfo(OfferingVO vo);
	int insertOffering(OfferingVO vo);
	int stockDivide();
	List<ConvertVO> getConvertList(MemberVO vo);
	int insertConvert(ConvertVO vo);
	HashMap<String, Object> stockDetailInfo(HolderVO vo);
	List<ExchangeVO> myExchangeInfo(ExchangeVO vo);
	int cancelExchange(ExchangeVO vo);
	
}

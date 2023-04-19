package com.yedam.app.influencer.mapper;

import java.util.Date;
import java.util.List;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.influencer.domain.ConvertVO;
import com.yedam.app.influencer.domain.ExchangeVO;
import com.yedam.app.influencer.domain.HolderVO;
import com.yedam.app.influencer.domain.InfluencerApplyVO;
import com.yedam.app.influencer.domain.ListingApplyVO;
import com.yedam.app.influencer.domain.OfferingVO;
import com.yedam.app.influencer.domain.StockVO;

public interface InfluencerMapper {
	public int insertListing(String id);
	public List<ListingApplyVO> myListingInfo(String id);
	public List<ExchangeVO> stockgraph(String id);
	public List<ExchangeVO> threeStockgraph(String id);
	public List<ExchangeVO> todayStockgraph(String id);
	public List<ExchangeVO> askgraph(String id);
	public List<ExchangeVO> bidgraph(String id);
	public int insertExchange(ExchangeVO vo);
	public int stockSettlement(ExchangeVO vo);
	public List<InfluencerApplyVO> selectApplyInfo(String id);
	public int insertInfluencer(String id);
	List<MemberVO> getInfluencerList();
	List<ListingApplyVO> ProgressListing();
	ListingApplyVO getListingInfo(String id);
	List<OfferingVO> getMyOfferingList(String id);
	OfferingVO sumOfferingInfo(String no);
	OfferingVO getMyOfferingInfo(OfferingVO vo);
	int insertOffering(OfferingVO vo);
	int stockDivide();
	List<ConvertVO> getConvertList(MemberVO vo);
	int insertConvert(ConvertVO vo);
	String getMyStockInfo(HolderVO vo);
	List<HolderVO> getMyStockList(HolderVO vo);
	String largeHolder(HolderVO vo);
	StockVO getStockInfo(HolderVO vo);
	Date firstListingDate(HolderVO vo);
	Date lastListingDate(HolderVO vo);
	int ListingUpdate(OfferingVO vo);
	int ListingModify(OfferingVO vo);
	int modifyOffering(OfferingVO vo);
	List<ExchangeVO> myExchangeInfo(ExchangeVO vo);
	int cancelExchange(ExchangeVO vo);
	int updateHolder(HolderVO vo);
	
}

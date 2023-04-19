package com.yedam.app.influencer.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.PointVO;
import com.yedam.app.common.mapper.MemberMapper;
import com.yedam.app.common.mapper.PointMapper;
import com.yedam.app.influencer.domain.ConvertVO;
import com.yedam.app.influencer.domain.ExchangeVO;
import com.yedam.app.influencer.domain.HolderVO;
import com.yedam.app.influencer.domain.InfluencerApplyVO;
import com.yedam.app.influencer.domain.ListingApplyVO;
import com.yedam.app.influencer.domain.OfferingVO;
import com.yedam.app.influencer.domain.StockVO;
import com.yedam.app.influencer.mapper.InfluencerMapper;

@Service
public class InfluencerServiceImpl implements InfluencerService{
	
	@Autowired
	InfluencerMapper influencerMapper;
	@Autowired
	MemberMapper memberMapper;
	@Autowired
	PointMapper pointMapper;
	
	@Override
	public int insertListing(String id) {
		// TODO Auto-generated method stub
		return influencerMapper.insertListing(id);
	}

	@Override
	public List<ListingApplyVO> myListingInfo(String id) {
		// TODO Auto-generated method stub
		return influencerMapper.myListingInfo(id);
	}

	@Override
	public List<ExchangeVO> stockgraph(String id, String type) {
		// TODO Auto-generated method stub
		if(type.equals("1")) {
		return influencerMapper.stockgraph(id);
	} else if(type.equals("3")) {
		return influencerMapper.threeStockgraph(id);
	} else if(type.equals("T")) {
		return influencerMapper.todayStockgraph(id);
	} else {
		return null;
	}
		
	}

	@Override
	public HashMap<String, Object> askgraph(String id) {
		// TODO Auto-generated method stub
		List<ExchangeVO> askList = influencerMapper.askgraph(id);
		List<ExchangeVO> bidList = influencerMapper.bidgraph(id);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("bidgraph", bidList);
		map.put("askgraph", askList);
		
		return map;
	}

	@Override
	public int insertExchange(ExchangeVO vo) {
		
		return influencerMapper.insertExchange(vo);
	}

	@Override
	public List<InfluencerApplyVO> selectApplyInfo(String id) {
		// TODO Auto-generated method stub
		return influencerMapper.selectApplyInfo(id);
	}

	@Override
	public int insertInfluencer(String id) {
		// TODO Auto-generated method stub
		return influencerMapper.insertInfluencer(id);
	}

	@Override
	public List<MemberVO> getInfluencerList() {
		// TODO Auto-generated method stub
		return influencerMapper.getInfluencerList();
	}

	@Override
	public List<ListingApplyVO> ProgressLisintg() {
		// TODO Auto-generated method stub
		return influencerMapper.ProgressListing();
	}

	@Override
	public ListingApplyVO getListingInfo(String id) {
		// TODO Auto-generated method stub
		return influencerMapper.getListingInfo(id);
	}

	@Override
	public List<OfferingVO> getMyOfferingList(String id) {
		// TODO Auto-generated method stub
		return influencerMapper.getMyOfferingList(id);
	}

	@Override
	public OfferingVO sumOfferingInfo(String listingNo) {
		// TODO Auto-generated method stub
		return influencerMapper.sumOfferingInfo(listingNo);
	}

	@Override
	public OfferingVO getMyOfferingInfo(OfferingVO vo) {
		// TODO Auto-generated method stub
		return influencerMapper.getMyOfferingInfo(vo);
	}
	
	@Transactional
	@Override
	public int insertOffering(OfferingVO vo) {
		// TODO Auto-generated method stub
		
		OfferingVO ovo = influencerMapper.getMyOfferingInfo(vo);
		if(ovo == null) {
			influencerMapper.ListingUpdate(vo);
			influencerMapper.insertOffering(vo);
		} else {
			influencerMapper.modifyOffering(vo);
			int offerAmount =  vo.getOfferingAmount() - ovo.getOfferingAmount();
			ovo.setOfferingAmount(offerAmount);
			influencerMapper.ListingModify(ovo);
		}
		
		PointVO pvo = new PointVO();
		pvo.setPointWhere("공모" + vo.getListingNo() + " 포인트 투자");
		pvo.setMemberId(vo.getApplicantId());
		pvo = pointMapper.selectOfferPoint(pvo);
		if(pvo == null) {
			pvo = new PointVO();
			pvo.setPointWhere("공모" + vo.getListingNo() + " 포인트 투자");
			pvo.setPointType('U');
			pvo.setPointNumber(vo.getOfferingPoint());
			pvo.setMemberId(vo.getApplicantId());
			pointMapper.pointHistory(pvo);
			pvo.setPointNumber(-1 * vo.getOfferingPoint());
			pointMapper.updatePoint(pvo);
		} else {
			int prevPoint = pvo.getPointNumber();
			pvo.setPointNumber(vo.getOfferingPoint());
			pointMapper.updatePointHistory(pvo);
			int modPoint = ((-1 * vo.getOfferingPoint()) + prevPoint);
			if(modPoint > 0) {
				pvo.setPointType('A');
			}
			pvo.setPointNumber(modPoint);
			System.out.println(modPoint);
			pointMapper.updatePoint(pvo);
		}	
		return 1; 
	}

	@Override
	public int stockDivide() {
		// TODO Auto-generated method stub
		return influencerMapper.stockDivide();
	}

	@Override
	public List<ConvertVO> getConvertList(MemberVO vo) {
		// TODO Auto-generated method stub
		return influencerMapper.getConvertList(vo);
	}

	@Override
	public int insertConvert(ConvertVO vo) {
		// TODO Auto-generated method stub
		return influencerMapper.insertConvert(vo);
	}
	
	@Transactional
	@Override
	public HashMap<String, Object> stockDetailInfo(HolderVO hvo) {
		// TODO Auto-generated method stub
		HashMap<String, Object> map = new HashMap<String, Object>();
		try {
		String stockAmount = influencerMapper.getMyStockInfo(hvo);
		map.put("stockAmount", stockAmount);
		} catch(Exception e){
			e.printStackTrace();
			int stockAmount = 0;
			map.put("stockAmount", stockAmount);
		} 
		List<HolderVO> hvoList = influencerMapper.getMyStockList(hvo);
		map.put("hvoList", hvoList);
		String largeHolder = influencerMapper.largeHolder(hvo);
		map.put("largeHolder", largeHolder);
		StockVO svo = influencerMapper.getStockInfo(hvo);
		map.put("svo", svo);
		Date lastDate = influencerMapper.lastListingDate(hvo);
		map.put("lastDate", lastDate);
		Date firstDate = influencerMapper.firstListingDate(hvo);
		map.put("firstDate", firstDate);
		MemberVO mvo = memberMapper.getMemberInfo(hvo.getMemberId());
		map.put("mvo", mvo);
		return map;
	}

	@Override
	public List<ExchangeVO> myExchangeInfo(ExchangeVO vo) {
		// TODO Auto-generated method stub
		return influencerMapper.myExchangeInfo(vo);
	}
	
	@Transactional
	@Override
	public int cancelExchange(ExchangeVO vo) {
		// TODO Auto-generated method stub
		if(vo.getExchangeType().equals("B")) {
			PointVO pvo = new PointVO();
			pvo.setMemberId(vo.getMemberId());
			pvo.setPointType('A');
			pvo.setPointWhere(vo.getExchangeNo() + "거래 취소 포인트 반환");
			pvo.setPointNumber(vo.getExchangeAmount() * vo.getExchangePrice());
			pointMapper.updatePoint(pvo);
			pointMapper.updatePointHistory(pvo);
		} else if (vo.getExchangeType().equals("S")) {
			HolderVO hvo = new HolderVO();
			hvo.setMemberId(vo.getMemberId());
			hvo.setStockId(vo.getStockId());
			hvo.setStockAmount(vo.getExchangeAmount());
			influencerMapper.updateHolder(hvo);
		} else {
			
		}
		
		return influencerMapper.cancelExchange(vo);
	}


	
	
	
}

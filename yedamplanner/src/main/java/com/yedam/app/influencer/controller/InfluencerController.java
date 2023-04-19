package com.yedam.app.influencer.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.service.CommonService;
import com.yedam.app.influencer.domain.ConvertVO;
import com.yedam.app.influencer.domain.ExchangeVO;
import com.yedam.app.influencer.domain.HolderVO;
import com.yedam.app.influencer.domain.InfluencerApplyVO;
import com.yedam.app.influencer.domain.ListingApplyVO;
import com.yedam.app.influencer.domain.OfferingVO;
import com.yedam.app.influencer.service.InfluencerService;

@Controller
@RequestMapping("/influ/*")
public class InfluencerController {
	
	@Autowired
	InfluencerService influencerService;
	@Autowired
	CommonService commonService;

	
	// 인플루언서 상장신청 페이지 이동
		@GetMapping("/listingapplyform")
		public String listingApplyForm(Model model, HttpServletRequest request) {
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			MemberVO vo = (MemberVO) session.getAttribute("login");
			List<ListingApplyVO> list = influencerService.myListingInfo(id);
			System.out.println("===========" + list);
			model.addAttribute("list", list);
			model.addAttribute("vo", vo);
			return "page/influencer/listing_apply";
		}
		
	// 상장 신청
		@GetMapping("/insertlisting")
		@ResponseBody
		public HashMap<String, String> insertListing(HttpServletRequest request){
			HashMap<String, String> list = new HashMap<String, String>();
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			try {
			influencerService.insertListing(id);
			list.put("result", "success");
			} catch(Exception e) {
				e.printStackTrace();
				list.put("result", "fail");
			}
			return list;
		}
		
	// 주식 상세 페이지
		@GetMapping("/stockdetail")
		public String stockDetail(@RequestParam String id, Model model, HttpServletRequest request) {
			HttpSession session = request.getSession();
			String memberId = (String) session.getAttribute("id");
			MemberVO influencerInfo = commonService.getMemberInfo(id);
			model.addAttribute("influencerInfo", influencerInfo);
			model.addAttribute("stockId", id);
			model.addAttribute("memberId", memberId);
			return "page/influencer/stock_detail";
		}
		
	// 주식 그래프 데이터
		@PostMapping("/stockgraph")
		@ResponseBody
		public List<ExchangeVO> stockgraph(@RequestBody HashMap<String, String> map) {
			String id = map.get("stockId");
			String type = map.get("type");
			List<ExchangeVO> list = influencerService.stockgraph(id, type);
			return list;
		}
		
	// 호가 그래프 데이터 조회
		@PostMapping("/askgraph")
		@ResponseBody
		public HashMap<String, Object> askgraph(@RequestBody HashMap<String, String> map) {
			String id = map.get("stockId");
			return influencerService.askgraph(id);
		}
	
	// 거래 등록
		@PostMapping("/insertexchange")
		@ResponseBody
		public HashMap<String, String> insertExchange(@RequestBody ExchangeVO vo){
			HashMap<String, String> result = new HashMap<String, String>();
			if(influencerService.insertExchange(vo) == -1) {	
				result.put("result", "success");
			} else {
				result.put("result", "fail");
			}
			return result;
		}
	
	// 인플루언서 메인
		@GetMapping("/main")
		public String influencerMain(Model model, HttpServletRequest request){
			List<MemberVO> lists = influencerService.getInfluencerList();
			List<ListingApplyVO> offers = influencerService.ProgressLisintg();
			model.addAttribute("lists", lists);
			model.addAttribute("offers", offers);
			return "page/influencer/influencer_main";
		}
		
	// 인플루언서 신청 페이지
		@GetMapping("/influencerapplyform")
		public String influencerApplyForm(Model model, HttpServletRequest request) {
			HttpSession session = request.getSession();
			String id = (String) session.getAttribute("id");
			MemberVO vo = (MemberVO)session.getAttribute("login");
			if(session.getAttribute("id") == null) {
				return "/page/common/login";
			} else {
				List<InfluencerApplyVO> list = influencerService.selectApplyInfo(id);
				System.out.println(list);
				model.addAttribute("list", list);
				model.addAttribute("vo", vo);
				return "page/influencer/influencer_apply";
			}
		}
		
		// 인플루언서 신청
		@PostMapping("/influencerapply")
		@ResponseBody
		public HashMap<String, String> influencerApply(HttpServletRequest request) {
			HttpSession session = request.getSession();
			HashMap<String, String> list = new HashMap<String, String>();
			String id = (String) session.getAttribute("id");
				try {
			int test = influencerService.insertInfluencer(id);
					list.put("result", "success");
					} catch(Exception e) {
						e.printStackTrace();
						list.put("result", "fail");
					}
					return list;
		}
	
		// 공모 신청 페이지
		@GetMapping("/offerapplyform")
		public String offerApplyForm(String listingNo, Model model, HttpServletRequest request) {
			OfferingVO ovo = new OfferingVO();
			HttpSession session = request.getSession();
			String memberId = (String) session.getAttribute("id");
			List<OfferingVO> offerList = influencerService.getMyOfferingList(memberId);
			OfferingVO sumOffer = influencerService.sumOfferingInfo(listingNo);
			ovo.setApplicantId(memberId);
			ovo.setListingNo(listingNo);
			OfferingVO myOffer = influencerService.getMyOfferingInfo(ovo);
			if(myOffer == null) {
				myOffer = new OfferingVO();
				myOffer.setOfferingPoint(0);
				myOffer.setOfferingAmount(0);
				myOffer.setBuyingAmount(0);
				myOffer.setBuyingPoint(0);
			}
			if(sumOffer == null) {
				sumOffer = new OfferingVO();
				sumOffer.setAvgPoint(0);
				sumOffer.setAvgPoint(0);
				sumOffer.setSum(0);
			}
			MemberVO memberInfo = commonService.getMemberInfo(memberId);
			ListingApplyVO lvo = influencerService.getListingInfo(listingNo);
			model.addAttribute("listingInfo", lvo);
			model.addAttribute("sumOffer", sumOffer);
			model.addAttribute("offerList", offerList);
			model.addAttribute("offerInfo", myOffer);
			model.addAttribute("memberId", memberId);
			model.addAttribute("memberInfo", memberInfo);
			return "page/influencer/offering_apply";
		}
		
		// 공모 신청
		@PostMapping("offerapply")
		public String offerApply(OfferingVO vo) {
			influencerService.insertOffering(vo);
			return "redirect:/influ/offerapplyform?listingNo=" + vo.getListingNo();
		}
		
		// 주식 배당
		@Scheduled(cron="0 0 0 1 * *")
		public void stockdivide() {
			influencerService.stockDivide();
		}
		
		// 환전 신청 페이지
		@GetMapping("/pointconvertform")
		public String pointExchangeForm(HttpServletRequest request, Model model) {
			HttpSession session = request.getSession();
			MemberVO mvo = (MemberVO)session.getAttribute("login");
			List<ConvertVO> list = influencerService.getConvertList(mvo);
			model.addAttribute("list", list);
			model.addAttribute("vo", mvo);
			return "/page/influencer/convert_apply";
		}
		
		// 환전 신청
		@PostMapping("/convertapply")
		public String convertApply(HttpServletRequest request, Model model, int convertPoint) {
			HttpSession session = request.getSession();
			String memberId = (String) session.getAttribute("id");
			ConvertVO cvo = new ConvertVO();
			cvo.setApplicantId(memberId);
			cvo.setConvertPoint(convertPoint);
			influencerService.insertConvert(cvo);
			
			return "redirect:/influ/pointconvertform";
		}
		
		// 주식 상세 정보 조회
		@PostMapping("stockdetailinfo")
		@ResponseBody
		public HashMap<String, Object> stockDetailInfo(@RequestBody HolderVO hvo) {
		
			return influencerService.stockDetailInfo(hvo);
		}
		
		// 나의 거래 내역 조회
		@PostMapping("myexchangeinfo")
		@ResponseBody
		public List<ExchangeVO> myExchangeInfo(@RequestBody ExchangeVO vo){
			
			return influencerService.myExchangeInfo(vo);
		}
		
		// 거래 취소
		@PostMapping("cancelexchange")
		@ResponseBody
		public HashMap<String, String> cancelExchange(@RequestBody ExchangeVO vo, HttpServletRequest request){
			HttpSession session = request.getSession();
			String memberId = (String)session.getAttribute("id");
			vo.setMemberId(memberId);
			int result = influencerService.cancelExchange(vo);
			HashMap<String, String> map = new HashMap<String, String>();
			if(result == 1) {
				map.put("result", "success");
			} else {
				map.put("result", "fail");
			}
			return map;
		}		
				
		
		
				
}
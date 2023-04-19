package com.yedam.app.admin.controller;


import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.admin.service.AdminService;
import com.yedam.app.common.domain.Criteria;
import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.PageDTO;
import com.yedam.app.common.domain.ReportVO;
import com.yedam.app.common.service.CommonService;
import com.yedam.app.influencer.domain.ConvertVO;
import com.yedam.app.influencer.domain.InfluencerApplyVO;
import com.yedam.app.influencer.domain.ListingApplyVO;

@RequestMapping("/admin/*")
@Controller
public class AdminController {

	@Autowired
	AdminService adminService;
	@Autowired
	CommonService commonService;
	
	// 멤버 관리 페이지
	@GetMapping("/memberadmin")
	public String memberAdmin(Model model) {
		List<MemberVO> list = commonService.allMemberList();
		model.addAttribute("list", list);
		return "page/admin/member_admin";
	}
	
	// 상장 신청 관리 페이지
	@GetMapping("/listingadmin")
	public String listingAdmin(Model model, Criteria cri) {
		if(cri == null) {
			cri = new Criteria();
		}
		System.out.println(cri);
		List<ListingApplyVO> list = adminService.selectListingInfo(cri);
		int total = adminService.totalAllListing();
		PageDTO page = new PageDTO(cri, total);
		model.addAttribute("list", list);
		model.addAttribute("page", page);
		return "page/admin/listing_admin";
	}
	
	// 상장 신청 승인
		@PostMapping("/approvelisting")
		@ResponseBody
		public HashMap<String, String> approveListing(@RequestBody HashMap<String, Integer> listingNo) {
			HashMap<String, String> list = new HashMap<String, String>();		
			int result = adminService.approveListingStock(listingNo.get("no"));
			if(result == 1) {
			list.put("result", "success");
			} else {
				list.put("result", "fail");
			}
			return list;
		}
		
		// 상장 신청 거부
		@PostMapping("/rejectlisting")
		@ResponseBody
		public HashMap<String, String> rejectListing(@RequestBody HashMap<String, Integer> listingNo) {
			HashMap<String, String> list = new HashMap<String, String>();		
			int result = adminService.rejectListingStock(listingNo.get("no"));
			if(result == 1) {
			list.put("result", "success");
			} else {
				list.put("result", "fail");
			}
			return list;
		}
		
		// 인플루언서 신청 관리 페이지
		@GetMapping("/influenceradmin")
		public String influencerAdmin(Model model) {
			List<InfluencerApplyVO> list = adminService.selectInfluencerInfo();
			model.addAttribute("list", list);
			return "page/admin/influencer_admin";
		}
		
		// 인플루언서 신청 승인
		@PostMapping("/approveinfluencer")
		@ResponseBody
		public HashMap<String, String> approveInfluencer(@RequestBody HashMap<String, String> applyNo) {
			HashMap<String, String> list = new HashMap<String, String>();		
			int number = Integer.parseInt(applyNo.get("no"));
			int result = adminService.rejectInfluencer(number);
			if(result == 1) {
			list.put("result", "success");
			} else {
				list.put("result", "fail");
			}
			System.out.println(list);
			return list;
		}
		
		// 인플루언서 신청 거부
		@PostMapping("/rejectinfluencer")
		@ResponseBody
		public HashMap<String, String> rejectInfluencer(@RequestBody HashMap<String, String> applyNo) {
			HashMap<String, String> list = new HashMap<String, String>();
			int number = Integer.parseInt(applyNo.get("no"));
			int result = adminService.rejectInfluencer(number);
			if(result == 1) {
			list.put("result", "success");
			} else {
				list.put("result", "fail");
			}
			System.out.println(list);
			return list;
		}
		
		// 상장 완료
		@PostMapping("/completelisting")
		@ResponseBody
		public HashMap<String, String> completeListing(@RequestBody HashMap<String, Integer> applyNo) {
			int result = 0;
			HashMap<String, String> list = new HashMap<String, String>();
			result = adminService.completeListing(applyNo.get("no"));
			System.out.println(result);
			list.put("result", "success");
			return list;
		}
		
		// 상세 조회 페이지 이동
		@PostMapping("/memberdetail")
		public String MemberDetail(String memberId, Model model) {
			System.out.println(memberId);
			MemberVO vo = commonService.getMemberInfo(memberId);
			model.addAttribute("vo", vo);
			
			return "page/admin/member_detail";
		}
		
		// 블랙리스트 조회 페이지 이동
		@GetMapping("/blacklistadmin")
		public String blackListAdmin(Model model) {
			List<MemberVO> list = adminService.getBlackList();
			model.addAttribute("list", list);
			
			return "page/admin/blacklist_admin";
		}
		
		// 신고 조회 페이지 이동
		
		@GetMapping("/reportadmin")
		public String reportListAdmin(Model model) {
			List<ReportVO> list = adminService.getReportList();
			model.addAttribute("list", list);
			return "page/admin/report_admin";
			
		}
		
		// 신고 승인
		@PostMapping("/reportapprove")
		@ResponseBody
		public HashMap<String, String> reportApprove(@RequestBody ReportVO vo) {
			
			HashMap<String, String> map = new HashMap<String, String>();
			if(adminService.reportApprove(vo) == 3) {
				map.put("result", "success");
			} else {
				map.put("result", "fail");
			}
			return map;
		}
		
		// 환전 신청 관리 페이지
		@GetMapping("/convertadmin")
		public String convertAdmin(Model model) {
			
		List<ConvertVO> list = adminService.allConvertList();
		model.addAttribute("list", list);	
			return "page/admin/convert_admin";
		}
		
		// 신고 거부
		@PostMapping("/reportreject")
		@ResponseBody
		public HashMap<String, String> reportReject(@RequestBody ReportVO vo){
			HashMap<String, String> map = new HashMap<String, String>();
			if(adminService.reportReject(vo) == 1) {
				map.put("result", "success");
			} else {
				map.put("result", "fail");
			}
			return map;
		}
		
}
		
		




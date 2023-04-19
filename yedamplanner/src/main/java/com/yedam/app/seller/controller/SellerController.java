package com.yedam.app.seller.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.service.CommonService;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.service.AllPlannerService;
import com.yedam.app.seller.domain.ProductVO;
import com.yedam.app.seller.service.SellerService;
import com.yedam.app.user.service.UserService;

@Controller
@RequestMapping("/seller/*")
public class SellerController {

	@Autowired
	UserService userService;
	
	@Autowired
	SellerService sellerService;
	
	@Autowired
	CommonService commonSerivce;
	
	@Autowired
	AllPlannerService allPlannerService;
	
	// 판매자 마이페이지 내 정보
	@GetMapping("/profile")
	public String profile(Model model, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (id == null) {
			return "redirect:/common/loginform";
		} else {
			MemberVO vo = commonSerivce.getMemberInfo(id);
			model.addAttribute("vo", vo);
			return "page/seller/profile";
		}
	}
	
	// 일반회원 정보 수정
	@PostMapping("/modifymemberinfo")
	public String modifyMemberInfo(MemberVO vo) {
		userService.modifyMemberInfo(vo);

		return "redirect:/seller/profile";
	}

	// 비밀번호 정보 수정
	@PostMapping("/modifypw")
	@ResponseBody
	public HashMap<String, String> modifypw(@RequestBody HashMap<String, String> pw, HttpSession session) {
		HashMap<String, String> list = new HashMap<String, String>();
		String id = (String) session.getAttribute("id");
		MemberVO vo = commonSerivce.getMemberInfo(id);
		if (vo.getMemberPw().equals(pw.get("pwnow"))) {
			vo.setMemberPw(pw.get("pwchange"));
			if (userService.modifyPw(vo) == 1) {
				list.put("result", "success");
			} else {
				list.put("result", "fail1");
			}
		} else {
			list.put("result", "fail2");
		}
		return list;
		
	}
	
	// 판매상품 리스트 이동
	@GetMapping("/product")
	public String productList(Model model, HttpSession session) {
	    String id = (String) session.getAttribute("id");
	    
	    List<ProductVO> productList = sellerService.getProductList(id);
	    model.addAttribute("list", productList);
	    
	    List<PlannerVO> plannerList = allPlannerService.getMyPlannerList(id);
	    model.addAttribute("myPlanList", plannerList);
	    
	    return "page/seller/productList";
	}
	
	
	// 판매상품 등록
	@PostMapping("/productInsert")
	public String inputProduct(ProductVO vo, HttpSession session) {
		
		String id = (String) session.getAttribute("id");
		
		vo.setSellerId(id);
		
		sellerService.insertProduct(vo);
		
		return "redirect:/seller/product";
		
	}

}

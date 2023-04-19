package com.yedam.app.user.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.PointVO;
import com.yedam.app.common.service.CommonService;
import com.yedam.app.planner.domain.MemberReviewVO;
import com.yedam.app.planner.domain.PlannerReviewVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.user.service.UserService;

@Controller
@RequestMapping("/user/*")
public class UserController {

	@Autowired
	UserService userService;
	@Autowired
	CommonService commonSerivce;
	@Autowired
	PasswordEncoder passwordEncoder;

	// 일반회원 마이페이지 내 정보
	@GetMapping("/profile")
	public String profile(Model model, HttpServletRequest request) {
		HttpSession session = request.getSession();
		String id = (String)session.getAttribute("id");
		MemberVO mvo = commonSerivce.getMemberInfo(id);
		model.addAttribute("vo", mvo);
		return "page/user/profile";
	}

	// 일반회원 정보 수정
	@PostMapping("/modifymemberinfo")
	public String modifyMemberInfo(MemberVO vo) {
		userService.modifyMemberInfo(vo);
		return "redirect:/user/profile";
	}

	// 비밀번호 정보 수정
	@PostMapping("/modifypw")
	@ResponseBody
	public HashMap<String, String> modifypw(@RequestBody HashMap<String, String> pw, HttpServletRequest request) {
		HashMap<String, String> list = new HashMap<String, String>();
		
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO)session.getAttribute("login");
		String EncPwNew = passwordEncoder.encode(pw.get("pwchange"));
		if (passwordEncoder.matches(pw.get("pwnow"), vo.getMemberPw())) {
			vo.setMemberPw(EncPwNew);
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
	
	// 후기 작성 페이지
	@GetMapping("/myplanner")
	public String myReview(String id, HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO)session.getAttribute("login");
		String memberId = (String) session.getAttribute("id");
		List<PlannerVO> list = userService.selectMyPlanner(memberId);
		model.addAttribute("list", list);
		model.addAttribute("vo", vo);
		return "page/user/myplanner";
	}
	
	// 포인트 이력 조회 페이지
	@GetMapping("/pointhistoryform")
	public String pointHistoryForm(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession();
		MemberVO vo = (MemberVO)session.getAttribute("login");
		String memberId = (String) session.getAttribute("id");
		List<PointVO> list = userService.selectPointHistory(memberId);
		model.addAttribute("list", list);
		model.addAttribute("vo", vo);
		return "page/user/pointhistoryform";
	}
	
	
	// 플래너 후기 등록, 수정
	@PostMapping("/insertplannerreview")
	@ResponseBody
	public HashMap<String, String> insertPlannerReview(@RequestBody PlannerReviewVO vo, HttpServletRequest request){
		System.out.println(vo);
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("id");
		vo.setRater(memberId);
		
		HashMap<String, String> map = new HashMap<>();
		int result = userService.insertPlannerReview(vo);
		if(result == 1) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	// 플래너 멤버 후기 등록, 수정
	@PostMapping("/insertmemberreview")
	@ResponseBody
	public HashMap<String, String> insertMemberReview(@RequestBody List<MemberReviewVO> list, HttpServletRequest request){
		HttpSession session = request.getSession();
		String memberId = (String) session.getAttribute("id");
		
		for(MemberReviewVO mrvo : list) {
			
			mrvo.setMrRater(memberId);
		}
		System.out.println(list);
		int result = userService.insertMemberReview(list);
	
		HashMap<String, String> map = new HashMap<>();
		if(result == 1) {
			map.put("result", "success");
		} else {
			map.put("result", "fail");
		}
		return map;
	}
	
	
}

package com.yedam.app.planner.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlanMemVO;
import com.yedam.app.planner.domain.PlannerReviewVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.service.AllPlannerService;
import com.yedam.app.planner.service.PlanMemService;
import com.yedam.app.planner.service.PlannerService;
import com.yedam.app.seller.domain.ProductVO;

import lombok.AllArgsConstructor;

//개발자: 권근희, 기능: 플래너 관리

@Controller
@RequestMapping("/planner/*")
public class AllPlannerController {

	@Autowired
	AllPlannerService allPlannerService;

	@Autowired
	PlanMemService planMemService;
	
	@Autowired
	PlannerService plannerService;

	// 플래너 전체 조회
	@RequestMapping(value = "/planList", method = RequestMethod.GET)
	public String planList(Model model) {
		//도시 정보
		List<CityVO> cityList = allPlannerService.getCityList();
		model.addAttribute("cityList", cityList);
		model.addAttribute("planList", allPlannerService.getPlannerList());
		return "page/planner/planList";
	}

	// 플래너 패키지 조회
	@RequestMapping(value = "/packageList", method = RequestMethod.GET)
	public String packageList(Model model) {
		List<CityVO> cityList = allPlannerService.getCityList();
		model.addAttribute("cityList", cityList);
		model.addAttribute("packageList", allPlannerService.getPlannerPackageList());
		return "page/planner/planPackageList";
	}

	// 플래너 추가, 입장
	@PostMapping("/plannerInsert")
	public ResponseEntity<String> inputPlannerProcess(PlannerVO plannerVO, PlanMemVO planMemVO, HttpSession session) {
	    String id = (String) session.getAttribute("id");
	    if (id == null) {
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("로그인 후 이용해주세요.");
	    }

	    plannerVO.setHostId(id);
	    planMemVO.setMemberId(id);
	    allPlannerService.insertPlannerInfo(plannerVO); //플래너 개설 
	    planMemVO.setPlanId(plannerVO.getPlanId());
	    planMemService.insertHost(planMemVO); //개설자에게 호스트 등급 부여

	    return ResponseEntity.ok(plannerVO.getPlanId());
	}

	// 플래너 결제창
	@RequestMapping(value = "/main/{planId}/payPackage", method = RequestMethod.GET)
	public String payPackage(@PathVariable String planId, Model model, HttpSession session, PlanMemVO planMemVO) {
		String id = (String) session.getAttribute("id");

		if (id == null) {
			return "redirect:/common/loginform";
		}
		
		//플래너 정보
		PlannerVO plannerVO = plannerService.getPlannerInfo(planId);
		model.addAttribute("plannerInfo", plannerVO);
		//도시 정보
		CityVO cityVO = plannerService.getPlannerCity(planId);
		model.addAttribute("cityInfo", cityVO);
		List<PlanMemVO> joinList = planMemService.getPlanJoinList(planId);
		model.addAttribute("planJoinList", joinList);
		List<PlanMemVO> list = planMemService.getPlanMemberList(planId);
		model.addAttribute("planMemberList", list);
		ProductVO vo = allPlannerService.payPackage(planId);
		model.addAttribute("vo", vo);
		
		return "page/planner/paymentPackage";
	}
	
	// 플래너 리뷰 목록
	@RequestMapping(value = "/planReviewList", method = RequestMethod.GET)
	public String planReviewList(Model model) {
		model.addAttribute("planReviewList", allPlannerService.getPlannerReviewList());
		return "page/planner/planReviewList";
	}
	
	// 리뷰 상세 조회
	@RequestMapping(value="/planReviewInfo/{planReviewId}", method=RequestMethod.GET)
	public String planReviewInfo(@PathVariable String planReviewId, Model model) {
		PlannerReviewVO vo = allPlannerService.getPlanReviewInfo(planReviewId);
		model.addAttribute("info", vo);
		return "page/planner/planReviewInfo";
	}

}

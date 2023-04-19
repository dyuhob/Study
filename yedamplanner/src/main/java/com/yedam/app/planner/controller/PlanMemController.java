package com.yedam.app.planner.controller;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlanMemVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.service.PlanMemService;
import com.yedam.app.planner.service.PlannerService;

// 개발자: 권근희 / 플래너 회원 관리

@Controller
@RequestMapping("/planner/*")
public class PlanMemController {

	@Autowired
	PlanMemService planMemService;
	
	@Autowired
	PlannerService plannerService;

	// 플래너 신청자 목록
	@RequestMapping(value = "/main/{planId}/planReq", method = RequestMethod.GET)
	public String planReqList(@PathVariable String planId, Model model) {

		//플래너 정보
		PlannerVO plannerVO = plannerService.getPlannerInfo(planId);
		model.addAttribute("plannerInfo", plannerVO);
		//도시 정보
		CityVO cityVO = plannerService.getPlannerCity(planId);
		model.addAttribute("cityInfo", cityVO);
		//맴버 목록
		List<PlanMemVO> memList = planMemService.getPlanMemberList(planId);
		model.addAttribute("planMemberList", memList);
		//신청자 리스트 조회
		List<PlanMemVO> list = planMemService.getPlanJoinList(planId);
		model.addAttribute("planJoinList", list);

		return "page/planner/planRequest";
	}

	// 플래너 가입 신청
	@PostMapping("/main/joinPlanner/{planId}")
	public String joinPlanner(@PathVariable("planId") String planId, PlanMemVO vo, HttpSession session) {
		String id = (String) session.getAttribute("id");
		if (session.getAttribute("id") == null) {
			return "/page/common/login";
		}
		vo.setMemberId(id);
		vo.setPlanId(planId);
		planMemService.planJoinReq(vo);
		return "redirect:/planner/main/" + planId;
	}

	// 플래너 가입 승인
	@ResponseBody
	@PostMapping("/main/insertMember/{planId}/{memberId}/{applicantNo}")
	public boolean insertMember(@PathVariable("planId") String planId, @PathVariable("memberId") String memberId,
								@PathVariable("applicantNo") int applicantNo, PlanMemVO vo) {
		vo.setPlanId(planId);
		vo.setMemberId(memberId);
		vo.setApplicantNo(applicantNo);
		planMemService.insertMember(vo); // plan_member table에 insert
		planMemService.deleteReq(vo); // PLAN_APPLICANT table에서 delete
		return true;
	}

	// 플래너 가입 거절
	@ResponseBody
	@PostMapping("/main/deleteReq/{applicantNo}")
	public boolean deleteReq(@PathVariable("applicantNo") int applicantNo, PlanMemVO vo) {

		vo.setApplicantNo(applicantNo);
		planMemService.deleteReq(vo);
		return true;
	}

	// 플래너 맴버 목록
	@RequestMapping(value = "/main/{planId}/planMember", method = RequestMethod.GET)
	public String pMemberList(@PathVariable String planId, Model model) {

		//플래너 정보
		PlannerVO plannerVO = plannerService.getPlannerInfo(planId);
		model.addAttribute("plannerInfo", plannerVO);
		//도시 정보
		CityVO cityVO = plannerService.getPlannerCity(planId);
		model.addAttribute("cityInfo", cityVO);
		List<PlanMemVO> list = planMemService.getPlanMemberList(planId);
		model.addAttribute("planMemberList", list);

		return "page/planner/planMember";
	}

	// 플래너 맴버 강퇴
	@PostMapping("/deleteMember")
	@ResponseBody
	public HashMap<String, String> deleteMember(String pmemKey) {
	    HashMap<String, String> map = new HashMap<String, String>();
	    int result = planMemService.deleteMember(pmemKey);
	    if (result == 1) {
	        map.put("result", "success");
	    } else {
	        map.put("result", "fail");
	    }
	    return map;
	}
	
	// 플래너 종결
	@PostMapping("/endPlanner")
	@ResponseBody
	public HashMap<String, String> endPlanner(String pmemKey) {
	    HashMap<String, String> map = new HashMap<String, String>();
	    int result = planMemService.endPlanner(pmemKey);
	    if (result == 1) {
	        map.put("result", "success");
	    } else {
	        map.put("result", "fail");
	    }
	    return map;
	}
	
	//호스트 이양
	@PostMapping("/changeHost/{planId}")
	@ResponseBody
	public HashMap<String, String> changeHost(@PathVariable String planId, String pmemKey, String memberId, PlanMemVO planMemVO, PlannerVO plannerVO, HttpSession session) {
	    HashMap<String, String> map = new HashMap<String, String>();
	    String id = (String) session.getAttribute("id");
	    planMemVO.setMemberId(id);
	    plannerVO.setHostId(memberId);
	    int result = planMemService.changeHost(pmemKey); //해당 맴버 'H' 등급으로 전환
	    if (result == 1) {
	        String hostId = planMemVO.getMemberId();
	        planMemService.dropHost(hostId); //기존 호스트 'N' 등급으로 전환
	        planMemService.changePlanHost(planId, memberId); //플래너 호스트 ID 변경
	        map.put("result", "success");
	    } else {
	        map.put("result", "fail");
	    }
	    return map;
	}
	
	//플래너 패키지 결제 완료
	@ResponseBody
	@PostMapping("/main/insertMember/{planId}")
	public String buyPackage(@PathVariable("planId") String planId, PlanMemVO vo, HttpSession session) {
		String id = (String) session.getAttribute("id");
		vo.setPlanId(planId);
		vo.setMemberId(id);
		planMemService.insertMember(vo);
		return "redirect:/planner/main/" + planId;
	}
	
	// 플래너 탈퇴
	@PostMapping("/quitPlanner/{planId}")
	@ResponseBody
	public HashMap<String, String> quitPlanner(@PathVariable("planId") String planId, PlanMemVO vo, HttpSession session) {
		String id = (String) session.getAttribute("id");
	    HashMap<String, String> map = new HashMap<String, String>();
	    vo.setPlanId(planId);
	    vo.setMemberId(id);
	    int result = planMemService.quitPlanner(planId, id);
	    if (result == 1) {
	        map.put("result", "success");
	    } else {
	        map.put("result", "fail");
	    }
	    return map;
	}
	
	//플래너 종료 처리
	@PostMapping("/endAllPlanner/{planId}")
	@ResponseBody
	public HashMap<String, String> endAllPlanner(@PathVariable("planId") String planId, PlannerVO vo) {
	    HashMap<String, String> map = new HashMap<String, String>();
	    vo.setPlanId(planId);
	    int result = planMemService.endAllPlanner(planId);
	    if (result == 1) {
	        map.put("result", "success");
	    } else {
	        map.put("result", "fail");
	    }
	    return map;
	}
	
}

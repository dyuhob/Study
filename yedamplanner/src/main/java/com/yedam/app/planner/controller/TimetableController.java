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

import com.yedam.app.planner.domain.BudgetVO;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlaceVO;
import com.yedam.app.planner.domain.PlanMemVO;
import com.yedam.app.planner.domain.PlannerVO;
import com.yedam.app.planner.service.PlanMemService;
import com.yedam.app.planner.service.PlannerService;
import com.yedam.app.planner.service.TimetableService;
import com.yedam.app.planner.service.TimetableVO;

@Controller
@RequestMapping("/planner/*")
public class TimetableController {

	@Autowired
	TimetableService timetableService;
	
	@Autowired
	PlannerService plannerService;
	
	@Autowired
	PlanMemService planMemService;

	// 타임테이블 조회
	@RequestMapping(value = "main/{planId}/timetable", method = RequestMethod.GET)
	public String timetableList(@PathVariable String planId, Model model) {
		PlannerVO plannerVO = plannerService.getPlannerInfo(planId);
		//플래너 정보 조회
		CityVO cityVO = plannerService.getPlannerCity(planId);
		model.addAttribute("cityInfo", cityVO);
		//여행지 리스트(지도마크)
		List<PlaceVO> placeMarkList = plannerService.getPlace(planId);
		model.addAttribute("placeList", placeMarkList);
		model.addAttribute("plannerInfo", plannerVO);
		model.addAttribute("timetableList", timetableService.getTimetableList(planId));
		//플래너 맴버 정보
		List<PlanMemVO> list = planMemService.getPlanMemberList(planId);
		model.addAttribute("planMemberList", list);
	    //플래너 가입요청
		List<PlanMemVO> joinList = planMemService.getPlanJoinList(planId);
		model.addAttribute("planJoinList", joinList);
		return "page/planner/timetableList";
	}

	// 일자별 타임테이블 조회
	@RequestMapping(value = "main/{planId}/timetable/{programDate}", method = RequestMethod.GET)
	public String timetableDateList(@PathVariable String planId, @PathVariable String programDate, Model model) {
		PlannerVO plannerVO = plannerService.getPlannerInfo(planId);
		//플래너 정보 조회
		CityVO cityVO = plannerService.getPlannerCity(planId);
		model.addAttribute("cityInfo", cityVO);
		//여행지 리스트(지도마크)
		List<PlaceVO> placeMarkList = plannerService.getPlace(planId);
		model.addAttribute("placeList", placeMarkList);
		model.addAttribute("plannerInfo", plannerVO);
		model.addAttribute("timetableList", timetableService.getTimetableList(planId, programDate));
		//플래너 맴버 정보
		List<PlanMemVO> list = planMemService.getPlanMemberList(planId);
		model.addAttribute("planMemberList", list);
		return "page/planner/timetableList";
	}


	// 타임테이블 추가
	@PostMapping("main/{planId}/timetableInsert")
	public String inputTimetableProcess(@PathVariable String planId, TimetableVO timetableVO, HttpSession session) {
		String id = (String) session.getAttribute("id");
		
		timetableVO.setProgramWriter(id);
		timetableVO.setPlanId(planId);
		
		timetableService.insertTimetableInfo(timetableVO);
		return "redirect:/planner/main/{planId}/timetable";
	}

	// 타임테이블 삭제
	@PostMapping("/timetableRemove")
	@ResponseBody
	public HashMap<String, String> deleteTimetableProcess(String programId, TimetableVO vo) {
	    HashMap<String, String> map = new HashMap<String, String>();
	    vo.setProgramId(programId);
	    try {
	        int result = timetableService.deleteTimetableInfo(programId);
	        System.out.println(result);
	        map.put("result", "success");
	    } catch (Exception e) {
	        e.printStackTrace();
	        map.put("result", "fail");
	    }

	    return map;
	}
}

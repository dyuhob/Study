package com.yedam.app.notice.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yedam.app.notice.service.NoticeService;
import com.yedam.app.notice.service.NoticeVO;

import lombok.AllArgsConstructor;

@Controller
@RequestMapping("/notice/*")
@AllArgsConstructor
public class NoticeController {

	@Autowired
	NoticeService noticeService;
	
	// 공지사항 리스트
	@GetMapping("/main")
	public String noticeList(Model model) {
		model.addAttribute("noticeList", noticeService.noticeList());
		return "page/notice/notice_home";
	}
	
	// 공지사항 단건 조회
	@GetMapping("/noticeViews/{notice_boardNum}")
	public String noticeViews(@PathVariable String notice_boardNum, Model model) {
	    NoticeVO noticeVO = noticeService.noticeBoardView(notice_boardNum);
	    model.addAttribute("notice", noticeVO);
	    return "page/notice/notice_view";
	}
	
	// 공지사항 글 작성 페이지
	@GetMapping("/noticeWrite")
	public String noticeWrite(Model model) {
	    model.addAttribute("change", "write");
	    return "page/notice/notice_write";
	}
	
	// 공지사항 글 등록
	@PostMapping({"/noticeAdd", "/noticeUpdate"})
	public String noticeAddOrUpdate(@RequestParam("change") String change,
	                                @RequestParam("noticeBoardNum") String noticeBoardNum,
	                                @RequestParam("noticeBoardTitle") String noticeBoardTitle,
	                                @RequestParam("noticeBoardContent") String noticeBoardContent,
	                                HttpSession session) {
		// 로그인 체크
	    String id = (String)session.getAttribute("id");
	    if (id == null) {
	        return "redirect:/common/loginform";
	    }

	    NoticeVO noticeVO = new NoticeVO();
	    noticeVO.setNoticeBoardNum(change.equals("edit") ? noticeBoardNum : null);
	    noticeVO.setNoticeBoardTitle(noticeBoardTitle);
	    noticeVO.setNoticeBoardContent(noticeBoardContent);
	    noticeVO.setMemberId(id);

	    // 작성 Form 구분 (등록,수정 버튼 유무)
	    if (change.equals("edit")) {
	        noticeService.noticeUpdate(noticeVO);
	        return "redirect:/notice/noticeView/" + noticeBoardNum;
	    } else { 
	        noticeService.noticeInsert(noticeVO);
	        return "redirect:/notice/main";
	    }
	}
	
	// 공지사항 글 수정 페이지
	@GetMapping("/noticeEdit/{noticeBoardNum}")
	public String noticeEdit(@PathVariable String noticeBoardNum,
							 			   Model model) {
		
	    NoticeVO noticeVO = noticeService.noticeBoardView(noticeBoardNum);
	    model.addAttribute("notice", noticeVO);
	    model.addAttribute("change", "edit");
	    return "page/notice/notice_write";
	}

	// 공지사항 조회수 카운트
	@GetMapping("/noticeView/{noticeBoardNum}")
	public String noticeView(@PathVariable String noticeBoardNum, Model model) {
	    NoticeVO noticeVO = new NoticeVO();
	    noticeVO.setNoticeBoardNum(noticeBoardNum);
	    noticeService.noticeViews(noticeVO); // 조회수 증가 메소드 호출

	    noticeVO = noticeService.noticeBoardView(noticeBoardNum);
	    model.addAttribute("notice", noticeVO);
	    return "page/notice/notice_view";
	}
	
	// 공지사항 삭제
	@GetMapping("/noticeDelete/{noticeBoardNum}")
	public String noticeDelete(@PathVariable String noticeBoardNum) {
	    noticeService.noticeDelete(noticeBoardNum);
	    return "redirect:/notice/main";
	}

}

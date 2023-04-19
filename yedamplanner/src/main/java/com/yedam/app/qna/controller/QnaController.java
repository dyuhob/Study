package com.yedam.app.qna.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.yedam.app.qna.service.QnaService;
import com.yedam.app.qna.service.QnaVO;

@Controller
@RequestMapping("/qna/*")
public class QnaController {

	@Autowired
	QnaService qnaService;
	
	// QnA 게시판
	@GetMapping("/main")
	public String qnaList(Model model) {
		model.addAttribute("qnaList", qnaService.qnaList());
		return "page/qna/qna_home";
	}
	
	// QnA 글쓰기 폼
	@GetMapping("/qnaWrite")
    public String qnaWrite(Model model) {
        model.addAttribute("change", "write");
        return "page/qna/qna_write";
    }

	// QnA 글 등록 및 수정 처리
	@PostMapping({"/qnaQuestionAdd", "/qnaQuestionUpdate"})
	public String qnaQuestionAdd(@RequestParam("change") String change,
	                             @RequestParam("communityBoardNum") String communityBoardNum,
	                             @RequestParam("communityBoardTitle") String communityBoardTitle,
	                             @RequestParam("communityBoardContent") String communityBoardContent,
	                             HttpSession session) {

	    // 로그인 체크
	    String id = (String)session.getAttribute("id");
	    if (id == null) {
	        return "redirect:/common/loginform";
	    }

	    QnaVO qnaVO = new QnaVO();
	    qnaVO.setCommunityBoardNum(change.equals("edit") ? communityBoardNum : null);
	    qnaVO.setCommunityBoardTitle(communityBoardTitle);
	    qnaVO.setCommunityBoardContent(communityBoardContent);
	    qnaVO.setMemberId(id);

	    if (change.equals("edit")) {
	        qnaService.qnaQuestionUpdate(qnaVO);
	    } else {
	        qnaService.qnaQuestionAdd(qnaVO);
	    }

	    return "redirect:/qna/main";
	}
    
    // 게시글 삭제
 	@GetMapping("/qnaQuestionDelete/{communityBoardNum}")
 	public String qnaQuestionDelete(@PathVariable String communityBoardNum) {
 	    qnaService.qnaQuestionDelete(communityBoardNum);
 	    return "redirect:/qna/main";
 	}
    
    // QnA 글 수정 페이지
 	@GetMapping("/qnaQuestionUpdate/{communityBoardNum}")
 	public String qnaQuestionUpdate(@PathVariable String communityBoardNum,
 							 		Model model) {
 		
 	    QnaVO qnaVO = qnaService.qnaPostLooking(communityBoardNum);
 	    model.addAttribute("qna", qnaVO);
 	    model.addAttribute("change", "edit");
 	    return "page/qna/qna_write";
 	}
    
 	// QnA 단건 조회
 	@GetMapping("/qnaPostLooking/{community_boardNum}")
 	public String qnaPostLooking(@PathVariable String community_boardNum,
 								 HttpSession session,
 								 Model model) {
 		
 	    QnaVO qnaVO = new QnaVO();
 	    qnaVO.setCommunityBoardNum(community_boardNum);
 	    qnaService.qnaPostView(qnaVO); // 조회수 증가 메소드 호출

 	    qnaVO = qnaService.qnaPostLooking(community_boardNum);
 	    model.addAttribute("qna", qnaVO);
 	    
 	    List<QnaVO> answers = qnaService.qnaAnswerPostList(community_boardNum);
 	    System.out.println("Answers: " + answers);
 	    model.addAttribute("qna", qnaVO);
 	    model.addAttribute("answers", answers);
 	    
 	   for(QnaVO answer : answers) {
 		    System.out.println("boardChoose: " + answer.getBoardChoose());
 		}

 	    return "page/qna/qna_post";
 	}
 	
 	// QnA 답글 작성 페이지
 	@GetMapping("/answerWrite/{communityBoardNum}")
 	public String answerWrite(@PathVariable("communityBoardNum") String communityBoardNum,
 	                          Model model,
 	                          HttpSession session) {
 	    String id = (String) session.getAttribute("id");
 	    QnaVO qnaVO = new QnaVO();
 	    qnaVO.setMemberId(id);

 	    model.addAttribute("qna", qnaVO);
 	    model.addAttribute("answerCommunityBoardNum", communityBoardNum);

 	    return "page/qna/answer_write";
 	}

 	// QnA 답글 등록
 	@PostMapping("/qnaAnswerAdd")
 	public String qnaAnswerAdd(@RequestParam("answerCommunityBoardNum") String answerCommunityBoardNum,
 	                           @RequestParam("communityBoardContent") String communityBoardContent,
 	                           HttpSession session) {
 		
 	    String id = (String) session.getAttribute("id");
 	    QnaVO qnaVO = new QnaVO();
 	    qnaVO.setAnswerCommunityBoardNum(answerCommunityBoardNum);
 	    qnaVO.setCommunityBoardContent(communityBoardContent);
 	    qnaVO.setMemberId(id);

 	    qnaService.qnaAnswerAdd(qnaVO);
 	    return "redirect:/qna/qnaPostLooking/" + answerCommunityBoardNum;
 	}
 
 	// QnA 답글 수정 (값 가져오기) 보류
 	@GetMapping("/qnaAnswerUpdate/{communityBoardNum}")
 	public String qnaAnswerUpdate(@PathVariable String communityBoardNum,
 	                              @RequestParam String answerCommunityBoardNum,
 	                              Model model) {
 	    System.out.println("answerCommunityBoardNum : " + answerCommunityBoardNum);
 	    QnaVO qnaVO = qnaService.qnaPostLooking(communityBoardNum);
 	    model.addAttribute("qna", qnaVO);
 	    model.addAttribute("answerCommunityBoardNum", answerCommunityBoardNum);
 	    return "page/qna/answer_update";
 	}

 	// QnA 답글 수정 (수정 값 뿌리기)
 	@PostMapping("/qnaAnswerUpdate")
 	public String qnaAnswerUpdate(QnaVO qnaVO, HttpSession session) {
 	    String memberId = (String) session.getAttribute("id");
 	    if (memberId == null) {
 	        return "redirect:/common/loginform";
 	    }
 	    
 	    qnaVO.setMemberId(memberId);
 	    qnaService.qnaAnswerUpdate(qnaVO);
 	    String answerUpdateRedirect = qnaService.answerUpdateRedirect(qnaVO.getAnswerCommunityBoardNum());
 	    return "redirect:/qna/qnaPostLooking/" + answerUpdateRedirect;
 	}

	
//  	// QnA 답글 채택
  	@GetMapping("/answerChoose/{communityBoardNum}")
  	public String answerChoose(@PathVariable("communityBoardNum") String communityBoardNum) {
  		
  	    qnaService.answerChoose(communityBoardNum);
  	    System.out.println("communitybN : " + communityBoardNum);
  	    String questionCommunityBoardNum = qnaService.answerRedirect(communityBoardNum);
  	    
  	    return "redirect:/qna/qnaPostLooking/" + questionCommunityBoardNum;
  	}
  	
//  	// QnA 답글 채택
//  	@GetMapping("/answerChoose/{communityBoardNum}")
//  	public String answerChoose(@PathVariable("communityBoardNum") String communityBoardNum) {
//  	    
//  	    QnaVO qnaVO = qnaService.getQuestionByAnswer(communityBoardNum);
//  	    if (qnaService.isAnswerChosen(qnaVO.getCommunityBoardNum())) {
//  	        // 이미 채택된 답변이 있는 경우
//  	        return "redirect:/qna/qnaPostLooking/" + qnaVO.getCommunityBoardNum();
//  	    }
//  	    
//  	    qnaService.answerChoose(communityBoardNum);
//  	    System.out.println("communitybN : " + communityBoardNum);
//  	    String questionCommunityBoardNum = qnaService.answerRedirect(communityBoardNum);
//  	    
//  	    return "redirect:/qna/qnaPostLooking/" + questionCommunityBoardNum;
//  	}
  	
  	// 답글 삭제
  	@GetMapping("/qnaAnswerDelete/{communityBoardNum}")
  	public String qnaAnswerDelete(@PathVariable String communityBoardNum) {
  	    qnaService.qnaAnswerDelete(communityBoardNum);
  	    return "redirect:/qna/main";
  	}
}

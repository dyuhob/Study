package com.yedam.app.sns.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.service.CommonService;
import com.yedam.app.sns.service.SnsService;
import com.yedam.app.sns.service.SnsVO;

import lombok.AllArgsConstructor;

// 이정용 : sns 메인 매핑

@Controller
@RequestMapping("/sns/*")
@AllArgsConstructor
public class SnsController {
	
	@Autowired
	SnsService snsService;
	@Autowired
	CommonService commonService;
//	로그인 세션
//	@GetMapping("/profile")
//	public String profile(Model model, HttpServletRequest request) {
//		HttpSession session = request.getSession();
//		String id = (String)session.getAttribute("id");
//		MemberVO vo = commonService.getMemberInfo(id);
//		
//		if (id == null) {
//			return "page/common/loginform";
//		}
//		
//		model.addAttribute("vo", vo);
//		System.out.println(vo);
//		return "page/sns/sns-home";		
//	}

//	메인페이지, 리스트 출력
	@GetMapping("/main/{memberId}")
	public String sns(@PathVariable String memberId, 
			          Model model, 
			          HttpSession session) {
	    String id = (String)session.getAttribute("id");
	    String fcnt = null;
	    
	    if (id == null) {
	        return "redirect:/common/loginform";
	    }
	    
	    // 팔로우 상태 확인.
	    if (id.equals(memberId)) {
	    	fcnt = "Me";
	    }
	    else {
	    	SnsVO sfollow = new SnsVO();
		    sfollow.setFollowId(id);
		    sfollow.setFollowerId(id);
		    int fResult = snsService.followCheck(sfollow);
		    
		    if (fResult > 0) {
		    		fcnt = "follow";
		    } else  {
		    		fcnt = "!follow"; 
		    }
	    }
	    
//	    System.out.println("memberId : " + memberId);
	    MemberVO vo = commonService.getMemberInfo(memberId);
	    model.addAttribute("vo", vo);
	    model.addAttribute("memberId", id);
	    model.addAttribute("followId", memberId);
	    model.addAttribute("fcnt", fcnt);

	    // 회원 page의 게시글 리스트 출력.
//	    List<SnsVO> postList = snsService.getPostListMember(memberId);
//	    model.addAttribute("postList", postList);
//	    List<SnsVO> followingPostList = snsService.getPostListFollowing(memberId);
//	    model.addAttribute("followingPostList", followingPostList);
	    List<SnsVO> combinedPostList = snsService.getPostListCombined(memberId);
	    model.addAttribute("combinedPostList", combinedPostList);
//	    System.out.println(postList);
	    
//	    boolean sessionMember = id.equals(memberId);
//	    boolean followchk = followService.followingList(id, memberId);
	    return "page/sns/sns-home";
	}
	
//	사용자 글모음
	@GetMapping("/myPost/{memberId}")
	public String getMyPosts(@PathVariable String memberId, Model model) {
	    List<SnsVO> myPost = snsService.getPostListMember(memberId);
	    model.addAttribute("myPost", myPost);
	    return "page/sns/sns-myPost";
	}
	
//	게시글 등록
	@PostMapping("/insertPost")
	public String createPost(SnsVO vo , 
							 HttpSession session ) {
		String id = (String)session.getAttribute("id");
		
//		if (!imageFile.isEmpty()) {
//			String uploadPath = "";
//			String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
//			
//			File target = new File(uploadPath, fileName);
//			imageFile.transferTo(target);
//		}
		vo.setMemberId(id);
		int result = snsService.insertPost(vo);
		
	    return "redirect:/sns/main/" + id;
	}
	
//	게시글 조회
	@GetMapping("/getPost/{postId}")
	@ResponseBody
	public SnsVO getPost(@PathVariable int postId) {
	
//		System.out.println("postId :" + postId);
	    SnsVO postVO = snsService.getPost(postId);
//	    System.out.println("postVO : " + postVO);
	    return postVO;	
	}
	
//	게시글 수정 
	@PostMapping("/updatePost")
	@ResponseBody
	public int updatePost(@RequestBody SnsVO UpostVO) {
		int updatePostVO = snsService.updatePost(UpostVO);
//		System.out.println("updatePostVO : " + updatePostVO);
		return updatePostVO;
	}
	
//	게시글 삭제
	@PostMapping("/deletePost")
	@ResponseBody
	public int deletePost(@RequestBody SnsVO dpostVO) {
//		System.out.println("dpostVO : " + dpostVO);
		int deletePostVO = snsService.deletePost(dpostVO);
//		System.out.println("deletePsotVO : " + deletePostVO);
		return deletePostVO;
	}
	
//	작성자 체크
	@PostMapping("/memberCheck")
	@ResponseBody
	public String memberCheck(@RequestParam("postId") int postId, HttpSession session) {
		String userId = (String)session.getAttribute("id");
//		System.out.println("userId : " + userId);
		return userId;
	}
	
//	댓글 리스트
	@GetMapping("/getCommentList")
	@ResponseBody
	public List<SnsVO> getCommentList(@RequestParam("postId") int postId ) {
		return snsService.getCommentList(postId);
	}
	
//	댓글 삭제
	@PostMapping("deleteComment")
	@ResponseBody
	public int deleteComment(SnsVO dcommentVO) {

		int deleteCommentVO = snsService.deleteComment(dcommentVO);
//		System.out.println("deleteCommentVO : " + deleteCommentVO);
		return deleteCommentVO;
	}
		
//	댓글 등록
	@PostMapping("/insertComment")
	@ResponseBody
	public String insertComment(SnsVO snsVO, HttpSession session) {
		String memberId = (String) session.getAttribute("id");
//		System.out.println("memberId : " + memberId);
		snsVO.setMemberId(memberId);

//		System.out.println("snsVO : " + snsVO);
		snsService.insertComment(snsVO);
	
		return "true";
	}
	
	//	팔로우 등록
	@PostMapping("/follow")
	@ResponseBody
	public Map<String, Object> changeFollow(@RequestBody HashMap<String, String> map, HttpServletRequest request) {
		
	    //로그인 여부 확인.
		HttpSession session = request.getSession();
	    String followerId = (String) session.getAttribute("id");
	    
	    if (followerId == null) {
	        return Collections.singletonMap("result", "fResult");
	    } 
	    
	    // 팔로우 상태 확인.
	    String memberId = map.get("memberId");
	    return snsService.followUpdate( memberId, followerId );

	}
	
	// 팔로우 여부 확인
	@GetMapping("/follow/check")
	@ResponseBody
	public HashMap<String, String> checkFollow(@RequestParam("memberId") String memberId, HttpServletRequest request) {
	    HttpSession session = request.getSession();
	    String followerId = (String) session.getAttribute("id");
	    HashMap<String, String> followCheckBtn = new HashMap<>();

	    if (followerId == null) {
	    	followCheckBtn.put("result", "fResult");
	    } else {
	        SnsVO snsVO = new SnsVO();
	        snsVO.setFollowId(memberId);
	        snsVO.setFollowerId(followerId);

	        int followCheck = snsService.followCheck(snsVO);
	        if (followCheck > 0) {
	        	followCheckBtn.put("result", "follow");
	        } else {
	        	followCheckBtn.put("result", "!follow");
	        }
	    }
	    return followCheckBtn;
	}

	// 좋아요 기능
	@PostMapping("/like")
	@ResponseBody
	public Map<String, Object> snsLikeCheck (@RequestBody HashMap<String, Object> data, HttpSession session) {
	    String memberId = (String) session.getAttribute("id");
	    int snsPostNum = Integer.parseInt(data.get("snsPostNum").toString());

	    // VO에 Id, snsPostNum 
	    SnsVO snsVO = new SnsVO();
	    snsVO.setMemberId(memberId);
	    snsVO.setSnsPostNum(snsPostNum);
	    System.out.println("snsPostNum : " + snsPostNum);
	    System.out.println("memberId : " + memberId);
	    // 좋아요 여부 확인
	    SnsVO likeCheck = snsService.likeCheck(snsVO);

	    if (likeCheck == null) {
	        // 좋아요 추가
	        snsService.likeInsert(snsVO);
	    }

	    // 좋아요 횟수 갱신
	    int likeCount = snsService.likeCountTotal(snsPostNum);

	    Map<String, Object> success = new HashMap<>();
	    success.put("likeCount", likeCount);
	    success.put("likeAdd", likeCheck == null);

	    return success;
	}
	
	// 좋아요 카운트
	@GetMapping("/getLikeCount")
	@ResponseBody
	public Map<String, Object> getLikeCount(@RequestParam("postId") int snsPostNum) {
	    int likeCount = snsService.likeCountTotal(snsPostNum);
	    Map<String, Object> response = new HashMap<>();
	    response.put("likeCount", likeCount);
	    return response;
	}
	
	// 검색바
//	@GetMapping("/main/{username}")
//	public String memberSearch(@PathVariable String memberId, Model model) {
//	    List<> member = commonService.(memberId);
//	    if (member == null) {
//	        model.addAttribute("errorMessage", "존재하지 않는 회원입니다.");
//	        return "error"; // 에러 페이지를 반환하거나 다른 적절한 페이지로 이동
//	    } else {
//	        model.addAttribute("member", member);
//	        return "memberSearch"; // 사용자 페이지 템플릿 반환
//	    }
//	}

}	

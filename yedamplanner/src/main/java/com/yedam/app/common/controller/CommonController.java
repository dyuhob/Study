package com.yedam.app.common.controller;

import java.security.SecureRandom;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yedam.app.common.domain.InfluencerVO;
import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.ReportVO;
import com.yedam.app.common.service.AES256;
import com.yedam.app.common.service.CommonService;
import com.yedam.app.planner.domain.CityVO;
import com.yedam.app.planner.domain.PlannerVO;

@Controller
@RequestMapping("/common/*")
public class CommonController {
   
   
   @Autowired
   CommonService commonService;
   
   @Autowired
   AES256 aes256;
   
   @Autowired
   PasswordEncoder passwordEncoder;
   
   // 회원 로그인 페이지
   @GetMapping("/loginform")
   public String loginform(HttpServletRequest request) {
	   	HttpSession session = request.getSession();
	   	/*String uri = request.getHeader("Referer");
	    if (uri != null && !uri.contains("/login")) {
	        request.getSession().setAttribute("prevPage", uri);
	    }*/
	   	if( session.getAttribute("id") != null ) {
	   		return "page/common/main"; 
	   	} else {
	   		return "page/common/login";
	   	}
	   	
   }
   
   // 로그인 처리
   @PostMapping("/login")
   @ResponseBody
   public HashMap<String, String> login(@RequestBody MemberVO vo, HttpServletRequest request) {
      HashMap<String, String> list = new HashMap<String, String>();
      HttpSession session = request.getSession();
      MemberVO member = commonService.getMemberInfo(vo.getMemberId());
         if(member == null) {
            list.put("result", "failid");
         } else if(vo.getMemberPw().equals(member.getMemberPw())) {
               session.setAttribute("id", member.getMemberId());
               session.setAttribute("grade", member.getMemberGrade());
               list.put("result", "success"); 
            } else {
               list.put("result", "failpw");
            }
         return list;
   }
   
   //메인 페이지
   @GetMapping("/main")
   public String main(Model model, HttpServletRequest request) {
	  HttpSession session = request.getSession();
      String id = (String) session.getAttribute("id");
      if(id != null) {
      MemberVO vo = commonService.getMemberInfo(id);
      model.addAttribute("vo", vo);
      }
      //인기 여행지
      List<CityVO> cityList = commonService.getTravelPlace();
      model.addAttribute("cityList", cityList);
      //인플루언서 리스트(랜덤)
      List<InfluencerVO> influList = commonService.getInfluencerList();
      System.out.println(influList.toString());
      model.addAttribute("influList", influList);
      //인기 플래너
      List<PlannerVO> planList = commonService.getPlanList();
      model.addAttribute("planList", planList);
      return "page/common/main";
   }
   
   // 일반 회원가입 페이지
   @GetMapping("/signupform")
   public String signupform() {
      return "page/common/signup";
   }
   
   // ID 중복 검사
   @GetMapping("/idtest")
   @ResponseBody
   public HashMap<String, String> idTest(@RequestParam String id, Model model) {
      
         HashMap<String, String> result = new HashMap<String, String>();
         MemberVO member = commonService.getMemberInfo(id);
         if(member != null) {
            result.put("result", "no");
         } else {
            result.put("result", "yes");
         }
         return result;
   }
   
   // 회원가입
   @PostMapping("/signup")
   public String signup(MemberVO vo, Model model) {
	  String EncPw = passwordEncoder.encode(vo.getMemberPw());
	  try {
		String EncSsh = aes256.encrypt(vo.getMemberSsh());
		vo.setMemberSsh(EncSsh);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  vo.setMemberPw(EncPw);
	  
      int result = commonService.signup(vo);
      if(result == 1) {
         return "page/common/login";
      }
      return "page/common/login";
   }
   
   // 카카오 로그인 & 회원가입
   @PostMapping("/kakaologin")
   @ResponseBody
   public HashMap<String, String> kakaologin(@RequestBody MemberVO vo, HttpServletRequest request) {
      HttpSession session = request.getSession();
      MemberVO member = commonService.getMemberInfo(vo.getMemberId());
      
      if(member == null) {
         commonService.kakaoSignup(vo);
      }
      
      member = commonService.getMemberInfo(vo.getMemberId());
      session.setAttribute("id", member.getMemberId());
      session.setAttribute("grade", member.getMemberGrade());
      session.setAttribute("login", member);
      
      HashMap<String, String> map = new HashMap<String, String>();
      map.put("result", "success");
      return map;
   }
   
   /* 주민등록증 진위확인용 암호화 기능 -> 사용하지 않음
   @GetMapping("/encrypt")
   @ResponseBody
   public HashMap<String, String> kakaologin(@RequestParam("ssh") String ssh, @RequestParam("name") String name, @RequestParam("date") String date) {
      
      HashMap<String, String> list = new HashMap<String, String>();
      try {
      String encSsh = aes256.encrypt(ssh);
      String encName = aes256.encrypt(name);
      String encDate = aes256.encrypt(date);
      list.put("encDate", encDate);
      list.put("encSsh", encSsh);
      list.put("encName", encName);
      } catch(Exception e) {
         e.printStackTrace();
      }
      return list;
   }*/
   
   // 판매자 회원가입 페이지
   @GetMapping("/sellersignupform")
   public String sellersignupform() {
      return "page/common/sellersignup";
   }
   
   // 판매자 회원가입
   @PostMapping("/sellersignup")
   public String sellersignup(MemberVO vo, Model model) {
      System.out.println(vo);
      int result = commonService.sellerSignup(vo);
      if(result == 1) {
         return "page/common/login";
      }
      return "page/common/login";
   
   }
   
   // 일반 회원, 판매자 회원가입 선택 페이지
   @GetMapping("/signupchoice")
   public String loginchoice() {
      return "page/common/signupchoice";
   }
   
   
   // 사업자등록증 중복 검사
   @GetMapping("/crntest")
   @ResponseBody
   public HashMap<String, String> crnTest(@RequestParam("crn") String crn) {
      
         HashMap<String, String> result = new HashMap<String, String>();
         MemberVO member = commonService.getMemberWithCrn(crn);
         if(member != null) {
            result.put("result", "no");
         } else {
            result.put("result", "yes");
         }
         return result;
   }
   
   // 주민등록증 중복 검사
      @GetMapping("/sshtest")
      @ResponseBody
      public HashMap<String, String> sshTest(@RequestParam("ssh") String ssh) {
    	  
            HashMap<String, String> result = new HashMap<String, String>();
            try {
				ssh = aes256.encrypt(ssh);
				String test = aes256.encrypt("9999991235555");
				System.out.println(test);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            MemberVO member = commonService.getMemberWithSsh(ssh);
            if(member != null) {
               result.put("result", "no");
            } else {
               result.put("result", "yes");
            }
            return result;
      }
      
   
   // 로그아웃
   @GetMapping("/logout")
   public String logout(HttpServletRequest request) {
      HttpSession session = request.getSession(); 
    		  session.invalidate();
      return "redirect:/common/loginform";
   }
   
   // 마이페이지
   @GetMapping("/mypage")
   public String myPage(HttpServletRequest request) {
     // if(session.getAttribute("id") == null) {
     //    return "/page/common/loginform";
     // } else {
         return "redirect:/user/profile";
   }
   
   // 신고 기능
   @PostMapping("/report")
   @ResponseBody
   public HashMap<String, String> report(@RequestBody ReportVO rvo, HttpServletRequest request){
	  HttpSession session = request.getSession();
	  String memberId = (String) session.getAttribute("id");
	  rvo.setReporterId(memberId);
	  return commonService.insertReport(rvo);
   }
   
   // 아이디 찾기
   @PostMapping("/forgot")
   @ResponseBody
   public List<MemberVO> forgot(@RequestBody MemberVO mvo){
	   String EncSsh;
	try {
		EncSsh = aes256.encrypt(mvo.getMemberSsh());
		mvo.setMemberSsh(EncSsh);
		
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	return commonService.forgot(mvo);
   }
   
   // 난수 생성
   public String getRamdomPassword(int size) {
		char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
				'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a',
				'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
				'w', 'x', 'y', 'z', '!', '@', '#', '$', '%', '^', '&' };

		StringBuffer sb = new StringBuffer();
		SecureRandom sr = new SecureRandom();
		sr.setSeed(new Date().getTime());

		int idx = 0;
		int len = charSet.length;
		for (int i = 0; i < size; i++) {
			// idx = (int) (len * Math.random());
			idx = sr.nextInt(len); // 강력한 난수를 발생시키기 위해 SecureRandom을 사용한다.
			sb.append(charSet[idx]);
		}

		return sb.toString();
	}
   
   // 임시 비밀번호 발급
   @PostMapping("/temporarypw")
   @ResponseBody
   public HashMap<String, String> temporaryPw(@RequestBody MemberVO mvo){
	   System.out.println(mvo);
	   HashMap<String, String> map = new HashMap<String, String>();
	   String EncSsh;
	   String EncTemporaryPw = "";
	   String temporaryPw = this.getRamdomPassword(8);
	try {
		EncTemporaryPw = passwordEncoder.encode(temporaryPw);
		EncSsh = aes256.encrypt(mvo.getMemberSsh());
		mvo.setMemberSsh(EncSsh);
		mvo.setMemberPw(EncTemporaryPw);
		map.put("tempPw", temporaryPw);
		commonService.temporaryPw(mvo);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		map.put("tempPw", "임시 비밀번호 발행 실패");
	}
	
	return map;
   }
   
// 복호화
   @PostMapping("/abc")
   @ResponseBody
   public HashMap<String, String> abc(@RequestBody HashMap<String, String> mvo){
	try {
		String abcd = aes256.encrypt(mvo.get("ssh"));
		System.out.println("주민번호 ================================================" + abcd);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	HashMap<String, String> map = new HashMap<String, String>();
	return map;
   }
}
   
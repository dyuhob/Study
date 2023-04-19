package com.yedam.app.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.service.CommonService;


public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {
	
	@Autowired
	private CommonService commonService;
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		HttpSession session = request.getSession();
		
		if(session != null) {
			MemberVO member = (MemberVO)session.getAttribute("login");
			if(member == null) {
				member = commonService.getMemberInfo(authentication.getName());
				session.setAttribute("id", member.getMemberId());
				session.setAttribute("grade", member.getMemberGrade());
				session.setAttribute("login", member);
			}
		}	
		response.sendRedirect("/common/main");
	}
}

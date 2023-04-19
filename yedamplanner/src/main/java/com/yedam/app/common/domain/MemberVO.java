package com.yedam.app.common.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;

@Data
public class MemberVO implements UserDetails {
	
	String memberId;
	String memberPw;
	String memberSsh;
	String memberName;
	String memberPhone;
	String memberAddr;
	String memberEmail;
	String memberAge;
	String memberGender;
	char memberGrade;
	String memberCrn;
	int memberPoint;
	String memberBank;
	String memberAccount;
	String memberIntroduce;
	Date blackEnd;
	String memberPhoto;
	
	// 인플루언서 랭킹 조회용 속성
	int rm;
	// 블랙리스트 회원 조회용 속성
	ReportVO rvo;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		List<GrantedAuthority> auth = new ArrayList<>();
		String role = "";
		if(memberGrade == 'A') {
			role = "ROLE_ADMIN";
		} else if (memberGrade == 'N' || memberGrade == 'I') {
			role = "ROLE_USER";
		} else if (memberGrade == 'S') {
			role = "ROLE_SELLER";
		}
		auth.add(new SimpleGrantedAuthority(role));
		return auth;
	}
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return memberPw;
	}
	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return memberId;
	}
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
}

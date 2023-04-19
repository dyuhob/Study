package com.yedam.app.user.mapper;

import com.yedam.app.common.domain.MemberVO;

public interface UserMapper {
	
	public int modifyMemberInfo(MemberVO vo);
	public int modifyPw(MemberVO vo);
}

package com.yedam.app.qna.service;

import java.util.Date;

import lombok.Data;

@Data
public class QnaVO {

	String communityBoardNum; // 게시글 번호 공통코드 QB001~
	  Date communityBoardDate; // 작성일자
	String communityBoardContent; // 본문 내용
	String communityBoardTitle; // 글 제목
	String communityBoardCategory; // 게시글 카테고리
	String communityBoardViews; // 조회수
	String memberId; // 회원 아이디
	String answerCommunityBoardNum; // 부모 게시글 번호
	
	  char communityBoardDelete; // 삭제 여부 'N' 디폴트 'Y'되면 숨김처리
	  char boardChoose; // 답변 채택 기능
}

package com.yedam.app.sns.service;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.Data;

@Data
public class SnsVO {
	int snsPostNum; // sns게시글 번호
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	Date snsPostDate; // 등록일자
	String snsPostContent; // sns 내용
	String memberId; // 회원 아이디
	String snsPostDelete; // sns 게시글 삭제여부
	
	int snsCommentNum; // sns 댓글번호
	String snsCommentContent; // sns댓글 내용
	Date snsCommentDate; // 등록 일자
	String snsCommentDelete; // 댓글 삭제여부
	
	String snsVisit; // 안씀
	Date snsVisitDate; // 안씀
	String snsVisitor; // 안씀
	String snsVisited; // 안씀
	
	String followId; // 내가 상대에게 팔로우
	String followerId; // 상대가 나에게 팔로우
	
	String likeId; // 좋아요 ID
	String likeCheck; // 좋아요 체크 여부
	int likeCount; // 좋아요 횟수
	
	//사진 조회용 속성
	String memberPhoto;

}
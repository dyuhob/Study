package com.yedam.app.qna.service;

import java.util.List;

public interface QnaService {
	// 게시글 리스트
	List<QnaVO> qnaList();
	// 게시글 추가
	void qnaQuestionAdd(QnaVO qnaVO);
	// 게시글 수정
	void qnaQuestionUpdate(QnaVO qnaVO);
	// 게시글 삭제
	void qnaQuestionDelete(String noticeBoardNUm);
	// 게시글 단건 조회
	QnaVO qnaPostLooking(String communityBoardNum);
	// 공지 조회수
	void qnaPostView(QnaVO QnaVO);
	// 답글 추가
	void qnaAnswerAdd(QnaVO qnaVO);
	// 답글 수정
	void qnaAnswerUpdate(QnaVO qnaVO);
	// 답글 리스트
	List<QnaVO> qnaAnswerPostList(String communityBoardNum);
	// 답글 채택
	void answerChoose(String communityBoardNum);
	// 채택후 리디렉션
	String answerRedirect(String answerBoardNum);
	// 답글 삭제
	void qnaAnswerDelete(String communityBoardNum);
	// 답글 수정후 리디렉션
	public String answerUpdateRedirect(String answerCommunityBoardNum);

}

package com.yedam.app.qna.mapper;

import java.util.List;

import com.yedam.app.qna.service.QnaVO;

public interface QnaMapper {
	// 게시글 리스트
	List<QnaVO> qnaList();
	// 게시글 추가
	void qnaQuestionAdd(QnaVO qnaVO);
	// 게시글 수정
	void qnaQuestionUpdate(QnaVO qnaVO);
	// 게시글 삭제
	void qnaQuestionDelete(String communityBoardNum);
	// 게시글 단건 조회
	QnaVO qnaPostLooking(String communityBoardNum);
	// 게시글 조회수
	void qnaPostView(QnaVO QnaVO);
	// 답글 추가
	void qnaAnswerAdd(QnaVO qnavO);
	// 답글 수정
	void qnaAnswerUpdate(QnaVO qnaVO);
	// 답글 리스트
	List<QnaVO> qnaAnswerPostList(String communityBoardNum);
	// 답글 채택
	void answerChoose(String communityBoardNum);
	// 답글 채택후 리디렉션
	String answerRedirect(String answerBoardNum);
	// 답글 삭제
	void qnaAnswerDelete(String communityBoardNum);
	// 답글 수정후 리디렉션
	public String answerUpdateRedirect(String answerCommunityBoardNum);
}

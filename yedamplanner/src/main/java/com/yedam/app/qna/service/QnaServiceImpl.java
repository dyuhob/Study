package com.yedam.app.qna.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.qna.mapper.QnaMapper;

@Service
public class QnaServiceImpl implements QnaService {

	@Autowired
	QnaMapper qnaMapper;
	
	@Override
	public List<QnaVO> qnaList() {
		return qnaMapper.qnaList();
	}

	@Override
	public void qnaQuestionAdd(QnaVO qnaVO) {
		qnaMapper.qnaQuestionAdd(qnaVO);
	}

	@Override
	public void qnaQuestionUpdate(QnaVO qnaVO) {
		qnaMapper.qnaQuestionUpdate(qnaVO);
	}

	@Override
	public QnaVO qnaPostLooking(String communityBoardNum) {
		return qnaMapper.qnaPostLooking(communityBoardNum);
	}

	@Override
	public void qnaPostView(QnaVO qnaVO) {
		qnaMapper.qnaPostView(qnaVO);
	}

	@Override
	public void qnaAnswerAdd(QnaVO qnaVO) {
		qnaMapper.qnaAnswerAdd(qnaVO);
	}

	@Override
	public void qnaAnswerUpdate(QnaVO qnaVO) {
		qnaMapper.qnaAnswerUpdate(qnaVO);
	}

	@Override
	public List<QnaVO> qnaAnswerPostList(String communityBoardNum) {
		return qnaMapper.qnaAnswerPostList(communityBoardNum);
	}

	@Override
	public void answerChoose(String communityBoardNum) {
		qnaMapper.answerChoose(communityBoardNum);
	}

	@Override
	public String answerRedirect(String answerBoardNum) {
	    return qnaMapper.answerRedirect(answerBoardNum);
	}

	@Override
	public void qnaQuestionDelete(String communityBoardNum) {
		qnaMapper.qnaQuestionDelete(communityBoardNum);
	}

	@Override
	public void qnaAnswerDelete(String communityBoardNum) {
		qnaMapper.qnaAnswerDelete(communityBoardNum);
	}	
	
	@Override
	public String answerUpdateRedirect(String answerCommunityBoardNum) {
	    return qnaMapper.answerUpdateRedirect(answerCommunityBoardNum);
	}

}

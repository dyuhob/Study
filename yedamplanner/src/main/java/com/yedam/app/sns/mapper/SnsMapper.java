package com.yedam.app.sns.mapper;

import java.util.List;

import com.yedam.app.sns.service.SnsVO;

public interface SnsMapper {
//	게시글 등록
	public int insertPost(SnsVO snsVO);
//	게시글 리스트
	public List<SnsVO> getPostListMember(String memberId);
//	게시글 조회
	public SnsVO getPost(int postId);
//	게시글 수정
	public int updatePost(SnsVO upostVO);
//	게시글 삭제
	public int deletePost(SnsVO dpostVO);
//	작성자 체크
	public String memberCheck(int postId);
//	댓글 리스트
	public List<SnsVO> getCommentList(int snsPostNum);
//	댓글 삭제
	public int deleteComment(SnsVO dcommentVO);
//	댓글 등록
	public int insertComment(SnsVO icommentVO);
//	팔로우 체크
	public int followCheck(SnsVO sfollowVO);
//	팔로우 추가
	public int followInsert(SnsVO ifollowVO);
//	팔로우 삭제
	public int followDelete(SnsVO dfollowVO);
// 팔로우한 사용자의 게시글 리스트
	public List<SnsVO> getPostListFollowing(String memberId);
// 팔로우한 사용자와 본인의 게시글 리스트
	public List<SnsVO> getPostListCombined(String memberId);

//	좋아요 중복 체크
	public SnsVO likeCheck(SnsVO likeCheckVO);
//	좋아요 추가
	public void likeInsert(SnsVO likeInsertVO);
//	좋아요 횟수 총합
	public int likeCountTotal(int snsPostNum);
}

package com.yedam.app.sns.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.common.mapper.MemberMapper;
import com.yedam.app.sns.mapper.SnsMapper;

@Service
public class SnsServiceImpl implements SnsService{

	@Autowired
	SnsMapper snsMapper;
	
	@Autowired
	MemberMapper memberMapper;
	
	@Override
	public int insertPost(SnsVO snsVO) {
		return snsMapper.insertPost(snsVO);
	}

	@Override
	public List<SnsVO> getPostListMember(String memberId) {
		return snsMapper.getPostListMember(memberId);
	}

	@Override
	public SnsVO getPost(int postId) {
		return snsMapper.getPost(postId);
	}

	@Override
	public int updatePost(SnsVO upostVO) {
		return snsMapper.updatePost(upostVO);
	}

	@Override
	public int deletePost(SnsVO dpostVO) {
		return snsMapper.deletePost(dpostVO);
	}

	@Override
	public String memberCheck(int postId) {
		return snsMapper.memberCheck(postId);
	}

	@Override
	public List<SnsVO> getCommentList(int snsPostNum) {
		return snsMapper.getCommentList(snsPostNum);
	}

	@Override
	public int deleteComment(SnsVO dcommentVO) {
		return snsMapper.deleteComment(dcommentVO);
	}

	@Override
	public int insertComment(SnsVO icommentVO) {
		return snsMapper.insertComment(icommentVO);
	}

	@Override
	public int followCheck(SnsVO sfollowVO) {
		return snsMapper.followCheck(sfollowVO);
	}

	@Override
	public HashMap<String, Object> followUpdate(String memberId, String followerId ) {
		HashMap<String, Object> changeFollow = new HashMap<String, Object>();
    	SnsVO snsVO = new SnsVO();
  	    snsVO.setFollowId(memberId);
  	    snsVO.setFollowerId(followerId);
  	    int followCheck = snsMapper.followCheck(snsVO);
  	    
  	    // 없으면 등록, 있으면 삭제.
  	    if (followCheck > 0) {
  	    	snsMapper.followDelete(snsVO);
  	        changeFollow.put("result", "dResult");
  	    } else {
  	    	changeFollow.put("result", "iResult");
  	    	snsMapper.followInsert(snsVO);
  	    }

  	    // 상태 확인.
	    String fcnt = null;
	    int fResult = snsMapper.followCheck(snsVO);
	    if (fResult > 0) {
	        fcnt = "follow";
	    } else {
	        fcnt = "!follow";
	    }
	    changeFollow.put("fcnt", fcnt);
		return changeFollow;
	}

	@Override
	public SnsVO likeCheck(SnsVO likeCheckVO) {
		return snsMapper.likeCheck(likeCheckVO);
	}
	
	@Override
	public void likeInsert(SnsVO likeInsertVO) {
		snsMapper.likeInsert(likeInsertVO);
	}

	@Override
	public int likeCountTotal(int snsPostNum) {
		return snsMapper.likeCountTotal(snsPostNum);
	}

	@Override
	public int followDelete(SnsVO dfollowVO) {
		return snsMapper.followDelete(dfollowVO);
	}

	@Override
	public List<SnsVO> getPostListFollowing(String memberId) {
		return snsMapper.getPostListFollowing(memberId);
	}

	@Override
	public List<SnsVO> getPostListCombined(String memberId) {
		
		List<SnsVO> list = snsMapper.getPostListCombined(memberId);
		for(SnsVO svo : list) {
			String photo = memberMapper.memberPhoto(svo.getMemberId());
			svo.setMemberPhoto(photo);
		}
		
		return list;
	}
}

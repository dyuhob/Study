package com.yedam.app.common.mapper;

import java.util.List;

import com.yedam.app.common.domain.MemberVO;
import com.yedam.app.common.domain.ReportVO;

public interface ReportMapper {
	
	public int insertReport(ReportVO vo);
	
	public ReportVO selectReport(ReportVO vo);
	
	public List<ReportVO> getReportList();
	
	int approveReport(ReportVO vo);
	
	int rejectReport(ReportVO vo);
	
	ReportVO getReportedInfo(MemberVO vo);
}

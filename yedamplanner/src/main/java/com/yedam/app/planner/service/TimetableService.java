package com.yedam.app.planner.service;

import java.util.List;

public interface TimetableService {
	// 타임테이블 전체 건수
	public List<TimetableVO> getTimetableList(String planId);
	public int insertTimetableInfo(TimetableVO timetableVO);
	public int deleteTimetableInfo(String programId);
	public List<TimetableVO> getTimetableList(String planId, String programDate);
}

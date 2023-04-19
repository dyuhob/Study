package com.yedam.app.planner.mapper;

import java.util.List;

import com.yedam.app.planner.service.TimetableVO;

public interface TimetableMapper {
	public List<TimetableVO> selectTimetableList(String planId);
	public int insertTimetableInfo(TimetableVO timetableVO);
	public int deleteTimetableInfo(String programId);
	public List<TimetableVO> getTimetableList(String planId, String programDate);
}

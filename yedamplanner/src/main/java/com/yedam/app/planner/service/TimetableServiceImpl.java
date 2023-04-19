package com.yedam.app.planner.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.planner.mapper.TimetableMapper;

@Service
public class TimetableServiceImpl implements TimetableService{
	
	@Autowired
	TimetableMapper timetableMapper;
	
	// 타임테이블 전체 건수 조회
	@Override
	public List<TimetableVO> getTimetableList(String planId) {
		return timetableMapper.selectTimetableList(planId);
	}

	// 타임테이블 추가
	@Override
	public int insertTimetableInfo(TimetableVO timetableVO) {
		int result = timetableMapper.insertTimetableInfo(timetableVO);
		
		return result;
	}

	// 타임테이블 삭제
	@Override
	public int deleteTimetableInfo(String programId) {
		int result = timetableMapper.deleteTimetableInfo(programId);
		
		return result;
	}

	@Override
	public List<TimetableVO> getTimetableList(String planId, String programDate) {
		return timetableMapper.getTimetableList(planId, programDate);
	}

}

package com.yedam.app.file.mapper;

import com.yedam.app.file.service.FileVO;

public interface FileMapper {

	void insertFile(FileVO fileVO);
	
	String getFile(String photoLocation);
}

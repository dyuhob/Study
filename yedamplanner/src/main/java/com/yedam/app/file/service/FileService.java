package com.yedam.app.file.service;

public interface FileService {
	void insertFile(FileVO fileVO);
	
	String getFile(String fileLocation);
	
}

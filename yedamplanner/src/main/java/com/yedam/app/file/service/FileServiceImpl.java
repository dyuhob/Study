package com.yedam.app.file.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.file.mapper.FileMapper;

@Service
public class FileServiceImpl implements FileService{
	
	@Autowired
	FileMapper fileMapper;

	@Override
	public void insertFile(FileVO fileVO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getFile(String fileLocation) {
		return fileMapper.getFile(fileLocation);
	}
}

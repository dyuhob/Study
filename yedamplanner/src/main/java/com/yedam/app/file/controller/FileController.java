package com.yedam.app.file.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.yedam.app.file.service.FileService;
import com.yedam.app.file.service.FileVO;

@Controller
public class FileController {

	@Autowired
	FileService fileService;
	
	@PostMapping("/uploadFile")
	public String uploadFile(@RequestParam("img") MultipartFile imgFile, @RequestParam("memberId") String memberId) {
		
		String fileName = imgFile.getOriginalFilename();
		String filePath = "/static/assets/img";
		String fileLocation = "";
		
		FileVO fileVO = new FileVO();
		fileVO.setPhotoId(fileName);
		fileVO.setPhotoRoute(filePath);
		fileVO.setPhotoLocation(fileLocation);
		fileVO.setMemberId(memberId);
		
//		fileService.
		return null;
	}
}

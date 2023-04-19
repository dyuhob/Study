package com.yedam.app.file.service;

import lombok.Data;

@Data
public class FileVO {

	String photoId; // 파일명
	String photoRoute; // 파일경로 (ex:static/assets/img/finalproject-img/...)
	String photoLocation; // 파일이 올라가는 게시판 및 SnS정보 (ex:localhost/sns/board1)
	String memberId;
}

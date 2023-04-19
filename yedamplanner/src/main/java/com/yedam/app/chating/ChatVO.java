package com.yedam.app.chating;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//생성자를 추가해야함
@AllArgsConstructor
//인수가 없는 생성자
@NoArgsConstructor
public class ChatVO {
	String userId;
	String content;
}

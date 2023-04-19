package com.yedam.app.common.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PageDTO {
	private int startPage;
	private int endPage;
	private boolean prev, next;
	
	private int total; // 전체건수.
	private Criteria cri; // 매개값으로 pageNum, amount
	
	public PageDTO(Criteria cri, int total) {
		this.cri = cri;
		this.total = total;
		
		// 현재페이지 : 11p(첫페이지) => 현재페이지(12p) => 20p(마지막페이지) 1.2 올림 => 2 * 10 => 20
		// 			=> 전체 255건이면 마지막은 26p
		this.endPage = (int) (Math.ceil(this.cri.getPageNum() / 10.0) * 10);
		this.startPage = this.endPage - 9; 
		
		int realEnd = (int) (Math.ceil((total*1.0) / cri.getAmount()));
		
		if(realEnd < this.endPage) {
			this.endPage = realEnd;
		}
		
		this.prev = this.startPage > 1; // 이전페이지 존재.
		this.next = this.endPage < realEnd; // 다음페이지 존재
		
	}
}

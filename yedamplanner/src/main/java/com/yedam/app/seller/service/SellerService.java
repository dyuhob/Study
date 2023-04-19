package com.yedam.app.seller.service;

import java.util.List;

import com.yedam.app.seller.domain.ProductVO;

public interface SellerService {
	
	//상품 목록 조회
	public List<ProductVO> getProductList(String sellerId);

	//상품 추가
	public void insertProduct(ProductVO vo);
}

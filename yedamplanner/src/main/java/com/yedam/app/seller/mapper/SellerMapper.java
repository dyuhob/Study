package com.yedam.app.seller.mapper;

import java.util.List;

import com.yedam.app.seller.domain.ProductVO;

public interface SellerMapper {
	// 상품 전체 목록 조회
	public List<ProductVO> selectProductList(String sellerId);

	public ProductVO selectProductInfo(String packageId);

	public int insertProduct(ProductVO vo);
}

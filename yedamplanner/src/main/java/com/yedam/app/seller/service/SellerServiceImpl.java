package com.yedam.app.seller.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yedam.app.seller.domain.ProductVO;
import com.yedam.app.seller.mapper.SellerMapper;

@Service
public class SellerServiceImpl implements SellerService{
	
	@Autowired
	SellerMapper sellerMapper;
	
	@Override
	public List<ProductVO> getProductList(String sellerId) {
		return sellerMapper.selectProductList(sellerId);
	}

	@Override
	public void insertProduct(ProductVO vo) {
		int result = sellerMapper.insertProduct(vo);
		
	}

}

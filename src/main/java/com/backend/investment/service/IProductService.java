package com.backend.investment.service;

import java.util.List;

import com.backend.investment.dto.ProductRequestDto;
import com.backend.investment.dto.ProductResponseDto;

public interface IProductService {

    String createProduct(ProductRequestDto request);

    ProductResponseDto getProduct(Long id);

    List<ProductResponseDto> getAllProducts();

}

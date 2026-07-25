package com.backend.investment.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.backend.investment.Exception.BadRequestException;
import com.backend.investment.Exception.ResourceNotFoundException;
import com.backend.investment.dto.ProductRequestDto;
import com.backend.investment.dto.ProductResponseDto;
import com.backend.investment.entity.Plan;
import com.backend.investment.entity.Product;
import com.backend.investment.repository.PlanRepository;
import com.backend.investment.repository.ProductRepository;
import com.backend.investment.service.IProductService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {

    private final ProductRepository productRepository;
    private final PlanRepository planRepository;

    @Override
    public String createProduct(ProductRequestDto request) {

        if (request.getPlanId() == null)
            throw new BadRequestException("Plan is mandatory.");

        if (request.getProductName() == null || request.getProductName().isBlank())
            throw new BadRequestException("Product Name is mandatory.");

        if (request.getInvestmentAmount() == null)
            throw new BadRequestException("Investment Amount is mandatory.");

        if (request.getDailyIncome() == null)
            throw new BadRequestException("Daily Income is mandatory.");

        if (request.getDurationDays() == null)
            throw new BadRequestException("Duration Days is mandatory.");

        Product exist = productRepository.findByProductName(request.getProductName())
                .orElse(null);

        if (exist != null)
            throw new BadRequestException("Product already exists.");

        Plan plan = planRepository.findById(request.getPlanId())
        .orElseThrow(() ->
              new ResourceNotFoundException("Product", "planId", request.getPlanId().toString())
        );
               
        Product product = new Product();

        product.setPlan(plan);
        product.setProductName(request.getProductName());
        product.setInvestmentAmount(request.getInvestmentAmount());
        product.setDailyIncome(request.getDailyIncome());
        product.setDurationDays(request.getDurationDays());
        product.setImageUrl(request.getImageUrl());
        product.setStatus(request.getStatus() == null ? "ACTIVE" : request.getStatus());
        product.setCreatedOn(LocalDateTime.now());
        productRepository.save(product);
        return "Product created successfully.";
    }

    

    @Override
    public ProductResponseDto getProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                new ResourceNotFoundException("Product", "productId", id.toString())
        );

        return mapProductResponseDto(product);
    }

    @Override
    public List<ProductResponseDto> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapProductResponseDto)
                .toList();
    }

    private ProductResponseDto mapProductResponseDto(Product product) {

        ProductResponseDto dto = new ProductResponseDto();

        dto.setId(product.getId());
        dto.setPlanId(product.getPlan().getId());
        dto.setPlanName(product.getPlan().getPlanName());
        dto.setProductName(product.getProductName());
        dto.setInvestmentAmount(product.getInvestmentAmount());
        dto.setDailyIncome(product.getDailyIncome());
        dto.setDurationDays(product.getDurationDays());
        dto.setImageUrl(product.getImageUrl());
        dto.setStatus(product.getStatus());
        dto.setCreatedOn(product.getCreatedOn());
        return dto;
    }

}

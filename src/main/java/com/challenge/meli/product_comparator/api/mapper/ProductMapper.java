package com.challenge.meli.product_comparator.api.mapper;

import com.challenge.meli.product_comparator.api.dto.ProductResponse;
import com.challenge.meli.product_comparator.domain.model.Product;

public class ProductMapper {
    private ProductMapper() {
    }

    public static ProductResponse toResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .rating(product.getRating())
                .imageUrl(product.getImageUrl())
                .specs(product.getSpecs())
                .build();
    }
}
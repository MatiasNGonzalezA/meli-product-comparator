package com.challenge.meli.product_comparator.api.dto;

import com.challenge.meli.product_comparator.domain.model.Product;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class CompareResponse {
    private List<Product> products;          // productos comparados
    private double minPrice;                 // precio más bajo
    private double maxPrice;                 // precio más alto
    private double avgPrice;                 // precio promedio
    private String bestPriceId;              // id del producto más barato
    private String bestRatingId;             // id del producto con mejor rating
    private Map<String, PriceDiff> priceDiffs; // diferencias respecto al más barato
    private String winnerId;
}
package com.challenge.meli.product_comparator.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ProductResponse {
    private String id;
    private String name;
    private String description;
    private double price;
    private double rating;
    private String imageUrl;
    private String specs;
}

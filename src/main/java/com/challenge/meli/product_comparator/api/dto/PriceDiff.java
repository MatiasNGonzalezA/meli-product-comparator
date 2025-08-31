package com.challenge.meli.product_comparator.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PriceDiff {
    private String productId;
    private double difference;
}

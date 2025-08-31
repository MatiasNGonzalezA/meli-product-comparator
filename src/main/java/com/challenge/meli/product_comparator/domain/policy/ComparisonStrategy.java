package com.challenge.meli.product_comparator.domain.policy;

import com.challenge.meli.product_comparator.domain.model.Product;

import java.util.List;

public interface ComparisonStrategy {
    String pickBest(List<Product> products);
}

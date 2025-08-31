package com.challenge.meli.product_comparator.domain.policy.impl;

import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.domain.policy.ComparisonStrategy;

import java.util.Comparator;
import java.util.List;

public class BestRatingStrategy implements ComparisonStrategy {
    public String pickBest(List<Product> ps) {
        return ps.stream().max(Comparator.comparingDouble(Product::getRating)).get().getId();
    }
}
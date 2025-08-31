package com.challenge.meli.product_comparator.application.impl;

import com.challenge.meli.product_comparator.api.dto.CompareResponse;
import com.challenge.meli.product_comparator.api.dto.PriceDiff;
import com.challenge.meli.product_comparator.application.port.in.ComparisonService;
import com.challenge.meli.product_comparator.application.port.in.ProductService;
import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.domain.policy.ComparisonStrategy;
import com.challenge.meli.product_comparator.domain.policy.StrategyType;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ComparisonServiceImpl implements ComparisonService {

    private final ProductService productService;
    private final Map<String, ComparisonStrategy> strategies; // beans por nombre "PRICE"/"RATING"

    public ComparisonServiceImpl(ProductService productService,
                                 Map<String, ComparisonStrategy> strategies) {
        this.productService = productService;
        this.strategies = strategies;
    }

    @Override
    public CompareResponse compare(List<String> ids) {
        return compare(ids, StrategyType.PRICE); // por defecto, PRICE
    }

    @Override
    public CompareResponse compare(List<String> ids, StrategyType strategy) {
        // 1) Traigo productos (el service ya valida lista vacía / normaliza ids)
        List<Product> products = productService.getByIds(ids);

        // 2) Métricas
        double minPrice = products.stream().mapToDouble(Product::getPrice).min().orElse(0);
        double maxPrice = products.stream().mapToDouble(Product::getPrice).max().orElse(0);
        double avgPrice = products.stream().mapToDouble(Product::getPrice).average().orElse(0);

        // 3) Bests
        String bestPriceId = products.stream()
                .min(Comparator.comparingDouble(Product::getPrice))
                .map(Product::getId)
                .orElse(null);

        String bestRatingId = products.stream()
                .max(Comparator.comparingDouble(Product::getRating))
                .map(Product::getId)
                .orElse(null);

        // 4) Diffs vs. mínimo
        Map<String, PriceDiff> diffs = products.stream()
                .collect(Collectors.toMap(
                        Product::getId,
                        p -> new PriceDiff(p.getId(), round2(p.getPrice() - minPrice))
                ));

        // 5) Orden para respuesta (opcional, más legible)
        List<Product> sortedByPrice = products.stream()
                .sorted(Comparator.comparingDouble(Product::getPrice))
                .toList();

        // 6) Strategy winner (PRICE/RATING u otra futura)
        ComparisonStrategy strategyBean = strategies.getOrDefault(
                (strategy == null ? StrategyType.PRICE : strategy).name(),
                strategies.get(StrategyType.PRICE.name())
        );
        String winnerId = strategyBean != null ? strategyBean.pickBest(products) : bestPriceId;

        return CompareResponse.builder()
                .products(sortedByPrice)
                .minPrice(round2(minPrice))
                .maxPrice(round2(maxPrice))
                .avgPrice(round2(avgPrice))
                .bestPriceId(bestPriceId)
                .bestRatingId(bestRatingId)
                .priceDiffs(diffs)
                .winnerId(winnerId)
                .build();
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }
}
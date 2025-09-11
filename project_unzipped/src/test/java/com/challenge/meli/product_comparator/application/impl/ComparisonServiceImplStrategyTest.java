package com.challenge.meli.product_comparator.application.impl;

import com.challenge.meli.product_comparator.api.dto.CompareResponse;
import com.challenge.meli.product_comparator.application.port.in.ProductService;
import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.domain.policy.ComparisonStrategy;
import com.challenge.meli.product_comparator.domain.policy.StrategyType;
import com.challenge.meli.product_comparator.domain.policy.impl.BestPriceStrategy;
import com.challenge.meli.product_comparator.domain.policy.impl.BestRatingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.*;

class ComparisonServiceImplStrategyTest {

    private ProductService productService;
    private ComparisonServiceImpl comparison;
    private Map<String, ComparisonStrategy> strategies;

    @BeforeEach
    void setUp() {
        productService = mock(ProductService.class);

        // usamos las estrategias reales para que el comportamiento sea determinista
        strategies = Map.of(
                StrategyType.PRICE.name(), new BestPriceStrategy(),
                StrategyType.RATING.name(), new BestRatingStrategy()
        );

        comparison = new ComparisonServiceImpl(productService, strategies);
    }

    @Test
    void compare_defaultUsesPRICE() {
        var p1 = new Product("A","A","",1000.0,4.0,"","");
        var p2 = new Product("B","B","",1200.0,4.9,"","");
        when(productService.getByIds(List.of("A","B"))).thenReturn(List.of(p1,p2));

        // Sin strategy → usa PRICE por defecto
        CompareResponse r = comparison.compare(List.of("A","B"));

        assertThat(r.getMinPrice()).isEqualTo(1000.0);
        assertThat(r.getMaxPrice()).isEqualTo(1200.0);
        assertThat(r.getAvgPrice()).isEqualTo(1100.0, within(0.0001));
        assertThat(r.getBestPriceId()).isEqualTo("A");
        assertThat(r.getBestRatingId()).isEqualTo("B");
        assertThat(r.getWinnerId()).isEqualTo("A"); // PRICE → el más barato
        verify(productService, times(1)).getByIds(List.of("A","B"));
        verifyNoMoreInteractions(productService);
    }

    @Test
    void compare_withRATING_usesRatingStrategy() {
        var p1 = new Product("A","A","",1000.0,4.0,"","");
        var p2 = new Product("B","B","",1200.0,4.9,"","");
        when(productService.getByIds(List.of("A","B"))).thenReturn(List.of(p1,p2));

        CompareResponse r = comparison.compare(List.of("A","B"), StrategyType.RATING);

        assertThat(r.getBestPriceId()).isEqualTo("A");
        assertThat(r.getBestRatingId()).isEqualTo("B");
        assertThat(r.getWinnerId()).isEqualTo("B"); // RATING → mejor rating
        verify(productService, times(1)).getByIds(List.of("A","B"));
        verifyNoMoreInteractions(productService);
    }

    @Test
    void compare_withNullStrategy_fallsBackToPRICE() {
        var p1 = new Product("A","A","",1000.0,4.0,"","");
        var p2 = new Product("B","B","",1200.0,4.9,"","");
        when(productService.getByIds(List.of("A","B"))).thenReturn(List.of(p1,p2));

        // Simulamos que llega null (controller haría StrategyType.from(null))
        CompareResponse r = comparison.compare(List.of("A","B"), null);

        assertThat(r.getWinnerId()).isEqualTo("A"); // fallback a PRICE
    }
}
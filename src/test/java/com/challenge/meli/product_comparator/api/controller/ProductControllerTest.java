package com.challenge.meli.product_comparator.api.controller;

import com.challenge.meli.product_comparator.api.dto.CompareResponse;
import com.challenge.meli.product_comparator.api.dto.PriceDiff;
import com.challenge.meli.product_comparator.application.port.in.ComparisonService;
import com.challenge.meli.product_comparator.application.port.in.ProductService;
import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.domain.policy.StrategyType;
import com.challenge.meli.product_comparator.exception.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

    @InjectMocks
    private ProductController controller;

    @Mock
    private ProductService productService;

    @Mock
    private ComparisonService comparisonService;

    @Test
    void getProducts_returnsList() {
        when(productService.getAllProducts())
                .thenReturn(List.of(new Product("1","A","desc",100,4.5,"http://img","specs")));

        var result = controller.getAllProducts();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo("1");
    }

    @Test
    void compare_withDefault_PRICE_returnsCompareResponse() {
        var p1 = new Product("1","A","d",1000,4.0,"u","s");
        var p2 = new Product("2","B","d",1200,4.8,"u","s");

        var dto = CompareResponse.builder()
                .products(List.of(p1, p2))
                .minPrice(1000).maxPrice(1200).avgPrice(1100)
                .bestPriceId("1").bestRatingId("2")
                .priceDiffs(Map.of("1", new PriceDiff("1",0),
                        "2", new PriceDiff("2",200)))
                .winnerId("1")
                .build();

        when(comparisonService.compare(List.of("1","2"), StrategyType.PRICE))
                .thenReturn(dto);

        var resp = controller.compareProducts(List.of("1","2"), "PRICE");

        assertThat(resp.getWinnerId()).isEqualTo("1");
        assertThat(resp.getMinPrice()).isEqualTo(1000);
        assertThat(resp.getProducts()).hasSize(2);
    }

    @Test
    void compare_withUnknownId_throwsNotFound() {
        when(comparisonService.compare(List.of("1","999"), StrategyType.PRICE))
                .thenThrow(new NotFoundException("missing"));

        assertThatThrownBy(() -> controller.compareProducts(List.of("1","999"), "PRICE"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("missing");
    }
}
package com.challenge.meli.product_comparator.api.controller;

import com.challenge.meli.product_comparator.api.dto.CompareResponse;
import com.challenge.meli.product_comparator.api.mapper.ProductMapper;
import com.challenge.meli.product_comparator.api.dto.ProductResponse;
import com.challenge.meli.product_comparator.application.port.in.ComparisonService;
import com.challenge.meli.product_comparator.application.port.in.ProductService;
import com.challenge.meli.product_comparator.domain.policy.StrategyType;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final ComparisonService comparisonService;

    public ProductController(ProductService productService, ComparisonService comparisonService) {
        this.productService = productService;
        this.comparisonService = comparisonService;
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts().stream()
                .map(ProductMapper::toResponse)
                .toList();
    }

    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable String id) {
        return ProductMapper.toResponse(productService.getById(id));
    }

    /**
     * Compara múltiples productos por IDs
     * Ejemplo: /products/compare?ids=1,2,3
     */
    @GetMapping(value = "/compare", produces = MediaType.APPLICATION_JSON_VALUE)
    public CompareResponse compareProducts(@RequestParam List<String> ids,
                                           @RequestParam(required = false, name = "strategy") String strategyRaw) {
        var strategy = StrategyType.from(strategyRaw);
        return comparisonService.compare(ids, strategy);
    }


}

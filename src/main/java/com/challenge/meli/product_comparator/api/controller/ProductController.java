package com.challenge.meli.product_comparator.api.controller;

import com.challenge.meli.product_comparator.application.implementation.ComparisonServiceImpl;
import com.challenge.meli.product_comparator.application.implementation.ProductServiceImpl;
import com.challenge.meli.product_comparator.domain.model.Product;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductServiceImpl productService;
    private final ComparisonServiceImpl comparisonService;

    public ProductController(ProductServiceImpl productService, ComparisonServiceImpl comparisonService) {
        this.productService = productService;
        this.comparisonService = comparisonService;
    }

    @GetMapping
    public List<Product> getAll(){
        return null;
    }


}

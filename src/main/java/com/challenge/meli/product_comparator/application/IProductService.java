package com.challenge.meli.product_comparator.application;

import com.challenge.meli.product_comparator.domain.model.Product;

import java.util.List;

public interface IProductService {
    List<Product> getAllProducts();
    Product getById(String id);
    List<Product> getByIds(List<String> ids);
}

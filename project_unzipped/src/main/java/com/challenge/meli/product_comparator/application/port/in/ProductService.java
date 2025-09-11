package com.challenge.meli.product_comparator.application.port.in;

import com.challenge.meli.product_comparator.domain.model.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProducts();
    Product getById(String id);
    List<Product> getByIds(List<String> ids);
}

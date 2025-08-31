package com.challenge.meli.product_comparator.application.port.out;

import com.challenge.meli.product_comparator.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository
{
    List<Product> findAll();
    Optional<Product> findById(String id);
    List<Product> findByIds(List<String> ids);
}

package com.challenge.meli.product_comparator.infraestructure.repository;

import com.challenge.meli.product_comparator.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface IProductRepository
{
    List<Product> findAll();
    Optional<Product> findById(String id);
    List<Product> findByIds(List<String> ids);
}

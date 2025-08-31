package com.challenge.meli.product_comparator.application.impl;

import com.challenge.meli.product_comparator.application.port.in.ProductService;
import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.exception.NotFoundException;
import com.challenge.meli.product_comparator.application.port.out.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository){
        this.repository = repository;
    }

    @Override
    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Product getById(String id) {
        return repository.findById(id).orElseThrow(()-> new NotFoundException("Product not found: " + id));
    }

    @Override
    public List<Product> getByIds(List<String> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'ids' must not be empty");
        }

        // normalizo: trim + saco vacíos
        //ej: List<String> ids = List.of("  123 ", null, "   ", "456");

        List<String> normalized = ids.stream()
                .map(s -> s == null ? "" : s.trim())
                .filter(s -> !s.isEmpty())
                .toList();
        //salida del normalized = ["123", "456"]
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("Parameter 'ids' must not be empty");
        }

        var found = repository.findByIds(normalized);

        // índice para detectar faltantes y mantener orden
        var byId = found.stream()
                .collect(java.util.stream.Collectors.toMap(Product::getId, java.util.function.Function.identity()));

        var missing = normalized.stream().filter(id -> !byId.containsKey(id)).toList();
        if (!missing.isEmpty()) {
            throw new NotFoundException("Products not found: " + String.join(",", missing));
        }

        return normalized.stream().map(byId::get).toList(); // respeta el orden pedido
    }
}

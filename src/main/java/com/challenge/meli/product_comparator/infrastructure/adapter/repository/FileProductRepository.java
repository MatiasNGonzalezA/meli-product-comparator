package com.challenge.meli.product_comparator.infrastructure.adapter.repository;

import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.application.port.out.ProductRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.*;

public class FileProductRepository implements ProductRepository {

    private final String sourcePath;
    private final ResourceLoader resourceLoader;
    private final ObjectMapper mapper = new ObjectMapper();

    public FileProductRepository(String sourcePath, ResourceLoader resourceLoader) {
        this.sourcePath = sourcePath;
        this.resourceLoader = resourceLoader;
    }

    private List<Product> loadAll() {
        try (var in = resourceLoader.getResource(sourcePath).getInputStream()) {
            return Arrays.asList(mapper.readValue(in, Product[].class));
        } catch (IOException e) {
            throw new RuntimeException("Error loading products from " + sourcePath, e);
        }
    }
    @Override
    public List<Product> findAll() {
        return loadAll();
    }

    @Override
    public Optional<Product> findById(String id) {
        return loadAll().stream().filter(p -> p.getId().equals(id)).findFirst();
    }

    @Override
    public List<Product> findByIds(List<String> ids) {
        var all = loadAll();
        return all.stream().filter(p -> ids.contains(p.getId())).toList();
    }
}
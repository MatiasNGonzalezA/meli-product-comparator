package com.challenge.meli.product_comparator.infrastructure.adapter.repository.decorators;

import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.application.port.out.ProductRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class CachedProductRepository implements ProductRepository {
    private final ProductRepository delegate;
    private final Map<String, Product> cache = new ConcurrentHashMap<>();

    public CachedProductRepository(ProductRepository delegate) {
        this.delegate = delegate;
    }

    @Override
    public List<Product> findAll() {
        // (opcional) podrías cachear la lista completa si te sirve
        return delegate.findAll();
    }

    @Override
    public Optional<Product> findById(String id) {
        var p = cache.get(id);
        if (p != null) return Optional.of(p);
        var fromRepo = delegate.findById(id);
        fromRepo.ifPresent(prod -> cache.put(prod.getId(), prod));
        return fromRepo;
    }

    @Override
    public List<Product> findByIds(List<String> ids) {
        List<Product> result = new ArrayList<>();
        List<String> misses = new ArrayList<>();

        for (String id : ids) {
            var p = cache.get(id);
            if (p != null) result.add(p); else misses.add(id);
        }

        if (!misses.isEmpty()) {
            var fetched = delegate.findByIds(misses);
            for (Product p : fetched) {
                cache.put(p.getId(), p);
                result.add(p);
            }
        }

        // conservar orden solicitado
        Map<String, Product> byId = new HashMap<>();
        for (Product p : result) byId.put(p.getId(), p);

        List<Product> ordered = new ArrayList<>();
        for (String id : ids) {
            if (byId.containsKey(id)) ordered.add(byId.get(id));
        }
        return ordered;
    }
}
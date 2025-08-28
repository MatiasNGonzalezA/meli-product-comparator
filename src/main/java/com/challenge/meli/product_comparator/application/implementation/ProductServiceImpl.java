package com.challenge.meli.product_comparator.application.implementation;

import com.challenge.meli.product_comparator.application.IProductService;
import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.exception.NotFoundException;
import com.challenge.meli.product_comparator.infraestructure.repository.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements IProductService {

    private final IProductRepository repository;

    public ProductServiceImpl(IProductRepository repository){
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
        var requested = ids.stream().map(String::trim).filter(s->!s.isEmpty()).toList();
        var found = repository.findByIds(requested);

        Set<String> foundIds = found.stream().map(Product::getId).collect(Collectors.toSet());
        var missing = requested.stream().filter(id -> !foundIds.contains(id)).toList();
        if(!missing.isEmpty()){
            throw new NotFoundException("Products not found: " + String.join(", ", missing));
        }
        return requested.stream().map(id -> found.stream().filter(p->p.getId().equals(id)).findFirst().get()).toList();
    }
}

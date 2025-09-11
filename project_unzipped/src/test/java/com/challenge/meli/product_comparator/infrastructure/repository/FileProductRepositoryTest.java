package com.challenge.meli.product_comparator.infrastructure.repository;

import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.infrastructure.adapter.repository.FileProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FileProductRepositoryTest {

    private FileProductRepository newRepo() {
        var resourceLoader = new DefaultResourceLoader();
        return new FileProductRepository("classpath:data/products.json", resourceLoader);
    }

    @Test
    void loadsJson_and_findMethodsWork() {
        var repo = newRepo();

        var all = repo.findAll();
        assertThat(all).isNotEmpty();

        Product first = all.get(0);
        assertThat(first.getId()).isNotBlank();
        assertThat(first.getName()).isNotBlank();

        String existingId = first.getId();

        assertThat(repo.findById(existingId)).isPresent();

        var some = repo.findByIds(List.of(existingId, "NO_EXISTE"));
        assertThat(some).extracting(Product::getId).contains(existingId);
    }

    @Test
    void findById_whenMissing_returnsEmpty() {
        var repo = newRepo();
        assertThat(repo.findById("__nope__")).isEmpty();
    }
}
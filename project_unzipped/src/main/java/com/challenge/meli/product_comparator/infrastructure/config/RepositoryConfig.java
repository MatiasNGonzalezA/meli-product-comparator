package com.challenge.meli.product_comparator.infrastructure.config;

import com.challenge.meli.product_comparator.application.port.out.ProductRepository;
import com.challenge.meli.product_comparator.infrastructure.adapter.repository.FileProductRepository;
import com.challenge.meli.product_comparator.infrastructure.adapter.repository.decorators.CachedProductRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ResourceLoader;

@Configuration
@Profile("file")
public class RepositoryConfig {

    @Bean
    public ProductRepository fileProductRepository(
            @Value("${app.data.source:classpath:data/products.json}") String sourcePath,
            ResourceLoader resourceLoader
    ) {
        return new FileProductRepository(sourcePath, resourceLoader);
    }

    @Bean
    @Primary
    public ProductRepository cached(ProductRepository fileProductRepository) {
        return new CachedProductRepository(fileProductRepository);
    }
}
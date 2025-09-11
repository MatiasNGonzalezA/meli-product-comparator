package com.challenge.meli.product_comparator.api.controller.mapper;

import com.challenge.meli.product_comparator.api.dto.ProductResponse;
import com.challenge.meli.product_comparator.api.mapper.ProductMapper;
import com.challenge.meli.product_comparator.domain.model.Product;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProductMapperTest {

    @Test
    void toResponse_mapsAllFields() {
        var p = new Product("P1", "Laptop", "Desc", 1234.56, 4.7, "https://img", "8GB;256GB");
        ProductResponse dto = ProductMapper.toResponse(p);

        assertThat(dto.getId()).isEqualTo("P1");
        assertThat(dto.getName()).isEqualTo("Laptop");
        assertThat(dto.getDescription()).isEqualTo("Desc");
        assertThat(dto.getPrice()).isEqualTo(1234.56);
        assertThat(dto.getRating()).isEqualTo(4.7);
        assertThat(dto.getImageUrl()).isEqualTo("https://img");
        assertThat(dto.getSpecs()).isEqualTo("8GB;256GB");
    }

}
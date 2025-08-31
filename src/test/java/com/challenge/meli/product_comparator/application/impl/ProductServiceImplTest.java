package com.challenge.meli.product_comparator.application.impl;

import com.challenge.meli.product_comparator.application.port.in.ProductService;
import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.exception.NotFoundException;
import com.challenge.meli.product_comparator.application.port.out.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceImplTest {

    private ProductRepository repository;
    private ProductService service;

    @BeforeEach
    void setUp() {
        repository = mock(ProductRepository.class);
        service = new ProductServiceImpl(repository);
    }

    @Test
    void getAllProducts_returnsAll() {
        var products = List.of(
                new Product("1","A","d",100,4.0,"u","s"),
                new Product("2","B","d",200,4.5,"u","s")
        );
        when(repository.findAll()).thenReturn(products);

        var result = service.getAllProducts();

        assertThat(result).hasSize(2);
        verify(repository, times(1)).findAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getById_found_ok() {
        var p = new Product("1","A","d",100,4.0,"u","s");
        when(repository.findById("1")).thenReturn(Optional.of(p));

        var result = service.getById("1");

        assertThat(result.getId()).isEqualTo("1");
        verify(repository, times(1)).findById("1");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getById_notFound_throws() {
        when(repository.findById("9")).thenReturn(Optional.empty());

        assertThatThrownBy(() -> service.getById("9"))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("9");

        verify(repository, times(1)).findById("9");
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getByIds_keepsRequestedOrder_and_throwsOnMissing() {
        var p1 = new Product("1","A","d",100,4.0,"u","s");
        var p2 = new Product("2","B","d",200,4.5,"u","s");

        // 1) Caso OK: el repo puede devolver en otro orden, el service debe mantener el orden solicitado
        when(repository.findByIds(List.of("1","2"))).thenReturn(List.of(p2, p1));
        var ordered = service.getByIds(List.of("1","2"));
        assertThat(ordered).extracting(Product::getId).containsExactly("1","2");

        // 2) Caso faltante: el repo no trae "3" → NotFoundException
        when(repository.findByIds(List.of("1","3"))).thenReturn(List.of(p1));
        assertThatThrownBy(() -> service.getByIds(List.of("1","3")))
                .isInstanceOf(NotFoundException.class)
                .hasMessageContaining("3");

        verify(repository, times(1)).findByIds(List.of("1","2"));
        verify(repository, times(1)).findByIds(List.of("1","3"));
        verifyNoMoreInteractions(repository);
    }

    @Test
    void getByIds_normalizesInput_trimsAndRemovesEmpty_andKeepsOrder() {
        // ids con espacios, vacíos y nulos -> usar Arrays.asList (acepta nulls)
        java.util.List<String> input = new java.util.ArrayList<>(
                java.util.Arrays.asList(" 1 ", null, "   ", "2", " 3  ")
        );

        var p1 = new Product("1","A","d",100,4.0,"u","s");
        var p2 = new Product("2","B","d",200,4.5,"u","s");
        var p3 = new Product("3","C","d",300,4.6,"u","s");

        // el repo devolverá sin garantizar orden
        when(repository.findByIds(java.util.Arrays.asList("1","2","3")))
                .thenReturn(java.util.Arrays.asList(p3, p1, p2));

        var out = service.getByIds(input);

        // assert: el service debe pedir al repo ["1","2","3"] (normalizado)
        var captor = ArgumentCaptor.forClass(java.util.List.class);
        verify(repository, times(1)).findByIds((List<String>) captor.capture());
        @SuppressWarnings("unchecked")
        var passed = (java.util.List<String>) captor.getValue();
        assertThat(passed).containsExactly("1","2","3");

        // y debe devolverlos manteniendo el orden pedido tras normalizar: 1,2,3
        org.assertj.core.api.Assertions.assertThat(out).extracting(Product::getId)
                .containsExactly("1","2","3");

        verifyNoMoreInteractions(repository);
    }
    @Test
    void getByIds_allEmptyAfterNormalize_throwsIllegalArgument() {
        var onlySpaces = java.util.Arrays.asList("   ", "   ");
        var withNulls  = new java.util.ArrayList<>(java.util.Arrays.asList(null, "   ", null));

        assertThatThrownBy(() -> service.getByIds(java.util.List.of()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ids");

        assertThatThrownBy(() -> service.getByIds(onlySpaces))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ids");

        assertThatThrownBy(() -> service.getByIds(withNulls))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("ids");

        verifyNoInteractions(repository);
    }
    @Test
    void getByIds_emptyList_illegalArgument() {
        assertThatThrownBy(() -> service.getByIds(List.of()))
                .isInstanceOf(IllegalArgumentException.class);
    }

}
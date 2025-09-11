package com.challenge.meli.product_comparator.infrastructure.repository.decorators;

import com.challenge.meli.product_comparator.application.port.out.ProductRepository;
import com.challenge.meli.product_comparator.domain.model.Product;
import com.challenge.meli.product_comparator.infrastructure.adapter.repository.decorators.CachedProductRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

class CachedProductRepositoryTest {

    private final ProductRepository delegate = mock(ProductRepository.class);
    private final CachedProductRepository repo = new CachedProductRepository(delegate);

    @Test
    void findById_miss_then_hit() {
        var p = new Product("A","A","",100,4,"","");
        when(delegate.findById("A")).thenReturn(Optional.of(p));

        // 1ª vez: miss -> delega
        assertThat(repo.findById("A")).contains(p);
        // 2ª vez: hit -> NO delega
        assertThat(repo.findById("A")).contains(p);

        verify(delegate, times(1)).findById("A");
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void findByIds_usa_cache_entre_llamadas() {
        var p1 = new Product("1","1","",10,4,"","");
        var p2 = new Product("2","2","",20,4,"","");

        when(delegate.findByIds(List.of("1","2"))).thenReturn(List.of(p1,p2));

        // 1ª llamada: delega una vez
        assertThat(repo.findByIds(List.of("1","2"))).containsExactlyInAnyOrder(p1,p2);
        verify(delegate, times(1)).findByIds(List.of("1","2"));

        // 2ª llamada con los mismos ids: debería salir de cache → no delega
        assertThat(repo.findByIds(List.of("1","2"))).containsExactlyInAnyOrder(p1,p2);
        verifyNoMoreInteractions(delegate);
    }

    @Test
    void findByIds_hit_parcial_solo_busca_misses() {
        var p1 = new Product("1","1","",10,4,"","");
        var p2 = new Product("2","2","",20,4,"","");

        // Precaliento cache para "1"
        when(delegate.findById("1")).thenReturn(Optional.of(p1));
        repo.findById("1");

        // Ahora solo debe delegar por "2"
        when(delegate.findByIds(List.of("2"))).thenReturn(List.of(p2));

        assertThat(repo.findByIds(List.of("1","2"))).containsExactlyInAnyOrder(p1,p2);

        verify(delegate, times(1)).findById("1");
        verify(delegate, times(1)).findByIds(List.of("2"));
        verifyNoMoreInteractions(delegate);
    }
}
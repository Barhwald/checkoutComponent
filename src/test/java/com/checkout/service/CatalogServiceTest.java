package com.checkout.service;

import com.checkout.domain.Product;
import com.checkout.exception.ItemNotFoundException;
import com.checkout.repository.CatalogRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CatalogServiceTest {

    @Test
    void findById_existingProduct_returnsProduct() {
        CatalogRepository repo = Mockito.mock(CatalogRepository.class);
        Product product = new Product("A", BigDecimal.valueOf(40), 3, BigDecimal.valueOf(30));
        Mockito.when(repo.findById("A")).thenReturn(Optional.of(product));

        CatalogService service = new CatalogService(repo);

        Product result = service.findById("A");
        assertThat(result).isEqualTo(product);
    }

    @Test
    void findById_nonExistentProduct_throwsException() {
        CatalogRepository repo = Mockito.mock(CatalogRepository.class);
        Mockito.when(repo.findById("Z")).thenReturn(Optional.empty());

        CatalogService service = new CatalogService(repo);

        assertThrows(ItemNotFoundException.class, () -> service.findById("Z"));
    }
}
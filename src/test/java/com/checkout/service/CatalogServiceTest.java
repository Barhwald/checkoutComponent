package com.checkout.service;

import com.checkout.domain.Product;
import com.checkout.exception.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CatalogServiceTest {

    @Autowired
    private CatalogService catalogService;

    @Test
    void findById_existingProduct_returnsProduct() {

        //given
        Product product = new Product("A", BigDecimal.valueOf(40), 3, BigDecimal.valueOf(30));

        //when
        Product result = catalogService.findById("A");

        //then
        assertThat(result.getId()).isEqualTo(product.getId());
        assertThat(result.getNormalPrice()).isEqualTo(product.getNormalPrice());
        assertThat(result.getRequiredQuantity()).isEqualTo(product.getRequiredQuantity());
        assertThat(result.getSpecialPrice()).isEqualTo(product.getSpecialPrice());
    }

    @Test
    void findById_nonExistentProduct_throwsException() {
        assertThrows(ItemNotFoundException.class, () -> catalogService.findById("Z"));
    }
}
package com.checkout.repository;

import com.checkout.domain.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CatalogRepositoryIntegrationTest {

    @Autowired
    CatalogRepository catalogRepository;

    @Test
    void catalogLoadsFromJson_andFindById() {

        //when
        Product a = catalogRepository.findById("A").orElseThrow();

        //then
        assertEquals("A", a.getId());
        assertEquals(0, BigDecimal.valueOf(40).compareTo(a.getNormalPrice()));
        assertEquals(3, a.getRequiredQuantity());
        assertEquals(0, BigDecimal.valueOf(30).compareTo(a.getSpecialPrice()));
    }
}
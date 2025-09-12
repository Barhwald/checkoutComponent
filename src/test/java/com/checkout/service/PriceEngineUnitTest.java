package com.checkout.service;

import com.checkout.domain.BundleOffer;
import com.checkout.domain.Checkout;
import com.checkout.domain.Product;
import com.checkout.repository.CatalogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PriceEngineUnitTest {

    @Autowired
    private PriceEngine priceEngine;

    private Map<String, Product> catalog;
    private List<BundleOffer> bundleOffers;

    @BeforeEach
    void setUp() {
        ObjectMapper mapper = new ObjectMapper();
        CatalogRepository catalogRepository = new CatalogRepository(mapper);

        catalog = catalogRepository.findAll().stream()
                .collect(Collectors.toMap(Product::getId, p -> p));
        bundleOffers = List.of(
                new BundleOffer("A", "B", BigDecimal.valueOf(5))
        );
    }

    @Test
    void calculateTotalPrice_specialPerItem_groupAndRemainder() {

        //given
        Checkout checkout = new Checkout();
        checkout.getItems().put("A", 4);

        //when
        BigDecimal total = priceEngine.calculateTotalPrice(checkout, catalog, bundleOffers);

        //then
        assertEquals(0, BigDecimal.valueOf(130).compareTo(total));
    }

    @Test
    void calculateTotalPrice_withBundleDiscount() {

        //given
        Checkout checkout = new Checkout();
        checkout.getItems().put("A", 3);
        checkout.getItems().put("B", 1);

        //when
        BigDecimal total = priceEngine.calculateTotalPrice(checkout, catalog, bundleOffers);

        //then
        assertEquals(0, BigDecimal.valueOf(95).compareTo(total));
    }
}

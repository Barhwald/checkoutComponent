package com.checkout.service;

import com.checkout.domain.BundleOffer;
import com.checkout.domain.Checkout;
import com.checkout.domain.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class PriceEngineUnitTest {

    private CatalogService catalogService;
    private BundleService bundleService;
    private PriceEngine priceEngine;

    @BeforeEach
    void setUp() {
        catalogService = Mockito.mock(CatalogService.class);
        bundleService = Mockito.mock(BundleService.class);
        priceEngine = new PriceEngine(bundleService, catalogService);
    }

    @Test
    void calculateTotalPrice_specialPerItem_groupAndRemainder() {
        Product a = new Product();
        a.setId("A");
        a.setNormalPrice(BigDecimal.valueOf(40));
        a.setRequiredQuantity(3);
        a.setSpecialPrice(BigDecimal.valueOf(30));

        when(catalogService.findById("A")).thenReturn(a);
        when(bundleService.getBundleOffers()).thenReturn(Collections.emptyList());

        Checkout checkout = new Checkout();
        Map<String, Integer> items = new HashMap<>();
        items.put("A", 4);
        checkout.getItems().putAll(items);

        BigDecimal total = priceEngine.calculateTotalPrice(checkout);

        assertEquals(0, BigDecimal.valueOf(130).compareTo(total));
    }

    @Test
    void calculateTotalPrice_withBundleDiscount() {
        Product a = new Product();
        a.setId("A");
        a.setNormalPrice(BigDecimal.valueOf(40));
        a.setRequiredQuantity(3);
        a.setSpecialPrice(BigDecimal.valueOf(30));

        Product b = new Product();
        b.setId("B");
        b.setNormalPrice(BigDecimal.valueOf(10));
        b.setRequiredQuantity(2);
        b.setSpecialPrice(BigDecimal.valueOf(7.5));

        when(catalogService.findById("A")).thenReturn(a);
        when(catalogService.findById("B")).thenReturn(b);

        BundleOffer offer = new BundleOffer("A", "B", BigDecimal.valueOf(5));
        when(bundleService.getBundleOffers()).thenReturn(Collections.singletonList(offer));

        Checkout checkout = new Checkout();
        checkout.getItems().put("A", 3);
        checkout.getItems().put("B", 1);

        BigDecimal total = priceEngine.calculateTotalPrice(checkout);

        assertEquals(0, BigDecimal.valueOf(95).compareTo(total));
    }
}

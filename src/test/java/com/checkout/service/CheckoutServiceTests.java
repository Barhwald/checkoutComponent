package com.checkout.service;

import com.checkout.domain.Receipt;
import com.checkout.exception.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckoutServiceTests {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CatalogService catalogService;

    @Test
    void testScanCreatesSessionAutomatically() {
        //given
        String itemName = "A";

        //when
        Receipt receipt = checkoutService.scanItem(itemName);

        //then
        assertNotNull(checkoutService.getSession());
        assertTrue(checkoutService.getSession().isActive());
        assertEquals(1, receipt.getItems().size());
        assertEquals(itemName, receipt.getItems().getFirst().getName());
    }

    @Test
    void testScanUnknownItemThrowsException() {
        assertThrows(ItemNotFoundException.class, () ->
                checkoutService.scanItem("UNKNOWN")
        );
    }

    @Test
    void testCheckoutFinalizesSession() {
        //given
        String itemName = "A";

        //when & then
        checkoutService.scanItem(itemName);
        Receipt receipt = checkoutService.checkout();
        assertFalse(checkoutService.getSession().isActive());

        //when & then
        checkoutService.scanItem(itemName);
        assertTrue(checkoutService.getSession().isActive());
    }

}


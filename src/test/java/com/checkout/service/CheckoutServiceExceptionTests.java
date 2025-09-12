package com.checkout.service;

import com.checkout.domain.ItemDto;
import com.checkout.exception.CheckoutNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CheckoutServiceExceptionTests {

    @Autowired
    private CheckoutService checkoutService;

    @Test
    void scanItem_invalidCheckoutId_throwsCheckoutNotFoundException() {

        //given
        String invalidId = "doesNotExist";
        ItemDto dto = new ItemDto();
        dto.setId("A");
        dto.setQuantity(1);

        //when & then
        assertThrows(CheckoutNotFoundException.class, () ->
                checkoutService.scanItem(invalidId, dto)
        );
    }

    @Test
    void finalizeCheckout_invalidCheckoutId_throwsCheckoutNotFoundException() {

        //given
        String invalidId = "doesNotExist";

        //when & then
        assertThrows(CheckoutNotFoundException.class, () ->
                checkoutService.finalizeCheckout(invalidId)
        );
    }

}

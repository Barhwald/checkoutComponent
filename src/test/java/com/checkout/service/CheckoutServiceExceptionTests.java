package com.checkout.service;

import com.checkout.domain.ItemDto;
import com.checkout.exception.CheckoutNotFoundException;
import com.checkout.repository.CheckoutRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class CheckoutServiceExceptionTests {

    private CheckoutRepository checkoutRepository;
    private CheckoutService checkoutService;

    @BeforeEach
    void setup() {
        checkoutRepository = mock(CheckoutRepository.class);
        PriceEngine priceEngine = mock(PriceEngine.class);
        checkoutService = new CheckoutService(priceEngine, checkoutRepository);
    }

    @Test
    void scanItem_invalidCheckoutId_throwsCheckoutNotFoundException() {
        String invalidId = "doesNotExist";
        ItemDto dto = new ItemDto();
        dto.setId("A");
        dto.setQuantity(1);

        when(checkoutRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(CheckoutNotFoundException.class, () ->
                checkoutService.scanItem(invalidId, dto)
        );
    }

    @Test
    void finalizeCheckout_invalidCheckoutId_throwsCheckoutNotFoundException() {
        String invalidId = "doesNotExist";

        when(checkoutRepository.findById(invalidId)).thenReturn(Optional.empty());

        assertThrows(CheckoutNotFoundException.class, () ->
                checkoutService.finalizeCheckout(invalidId)
        );
    }

}

package com.checkout.controller;

import com.checkout.service.CheckoutService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CheckoutControllerTests {

    @Autowired
    private CheckoutService checkoutService;

    @Autowired
    private CheckoutController controller;

    @BeforeEach
    void setUp() {
        checkoutService = Mockito.mock(CheckoutService.class);
        controller = new CheckoutController(checkoutService);
    }

    @Test
    void finalizeTransaction_noSession_throwsException() {
        Mockito.when(checkoutService.getSession()).thenReturn(null);

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> controller.finalizeTransaction());

        assertThat(ex.getMessage()).isEqualTo("No active session to finalize");
    }

}
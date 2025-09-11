package com.checkout.exception;

import com.checkout.controller.CheckoutController;
import com.checkout.service.CheckoutService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CheckoutController.class)
class CheckoutNotFoundExceptionTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CheckoutService checkoutService;

    @Test
    void finalizeCheckout_checkoutNotFound_returns400() throws Exception {
        String invalidId = "DoesNotExist";

        when(checkoutService.finalizeCheckout(invalidId))
                .thenThrow(new CheckoutNotFoundException("Checkout not found: " + invalidId));

        mvc.perform(get("/checkouts/" + invalidId + "/finalize")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}

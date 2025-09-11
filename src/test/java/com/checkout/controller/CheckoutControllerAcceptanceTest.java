package com.checkout.controller;

import com.checkout.domain.Checkout;
import com.checkout.domain.ItemDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class CheckoutControllerAcceptanceTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void flow_create_scan_finalize_returnsExpectedTotal() throws Exception {
        // --- Create checkout ---
        String createResponse = mvc.perform(post("/checkouts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Checkout created = mapper.readValue(createResponse, Checkout.class);
        assertThat(created.getId()).isNotNull();
        assertThat(created.isActive()).isTrue();
        assertThat(created.getItems()).isEmpty();

        String checkoutId = created.getId();

        // --- Scan items (4x A) ---
        ItemDto dto = new ItemDto();
        dto.setId("A");
        dto.setQuantity(4);
        String payload = mapper.writeValueAsString(dto);

        String scanResponse = mvc.perform(post("/checkouts/" + checkoutId + "/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(payload))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Checkout updated = mapper.readValue(scanResponse, Checkout.class);
        assertThat(updated.getItems()).containsEntry("A", 4);
        // catalog.json says: A normal 40, special 30 for group of 3 → 30*3 + 40 = 130
        assertThat(updated.getFinalPrice()).isEqualByComparingTo(BigDecimal.valueOf(130));

        // --- Finalize checkout ---
        String finalizeResponse = mvc.perform(get("/checkouts/" + checkoutId + "/finalize"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();

        Checkout finalized = mapper.readValue(finalizeResponse, Checkout.class);
        assertThat(finalized.isActive()).isFalse();
        assertThat(finalized.getFinalPrice()).isEqualByComparingTo(BigDecimal.valueOf(130));
    }
}
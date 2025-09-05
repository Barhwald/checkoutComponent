package com.checkout;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CheckoutAcceptanceTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testScanItemsAndReceiptCalculation() throws Exception {
        mockMvc.perform(post("/v1/items/scan")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"A\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("A"))
                .andExpect(jsonPath("$.items[0].quantity").value(1))
                .andExpect(jsonPath("$.items[0].totalPrice").value(40));

        mockMvc.perform(post("/v1/items/scan")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"A\"}"));
        mockMvc.perform(post("/v1/items/scan")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"A\"}"));

        // 3. Check receipt JSON
        mockMvc.perform(get("/v1/items/finalize"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items[0].name").value("A"))
                .andExpect(jsonPath("$.items[0].quantity").value(3))
                .andExpect(jsonPath("$.totalPrice").value(90));
    }


}
package com.checkout.service;

import com.checkout.domain.Item;
import com.checkout.exception.ItemNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CatalogServiceTests {

    @Autowired
    private CatalogService catalogService;

    @Test
    void findByName_existingItem_returnsItem() {
        Item item = catalogService.findByName("A");

        assertThat(item).isNotNull();
        assertThat(item.getName()).isEqualTo("A");
        assertThat(item.getNormalPrice()).isEqualByComparingTo(BigDecimal.valueOf(40));
    }

    @Test
    void findByName_unknownItem_throwsException() {
        assertThrows(ItemNotFoundException.class, () ->
                catalogService.findByName("UNKNOWN"));
    }
}

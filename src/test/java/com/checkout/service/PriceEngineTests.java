package com.checkout.service;

import com.checkout.domain.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PriceEngineTests {

    @Test
    void testSingleItemNoSpecialPricing() {
        Item item = new Item();
        item.setName("A");
        item.setNormalPrice(BigDecimal.valueOf(40));
        item.setQuantity(1);

        BigDecimal total = PriceEngine.calculateTotalPrice(List.of(item));

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(40));
        assertThat(item.getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(40));
    }

    @Test
    void testMultiPricingApplied() {
        Item item = new Item();
        item.setName("A");
        item.setNormalPrice(BigDecimal.valueOf(40));
        item.setRequiredQuantity(3);
        item.setSpecialPrice(BigDecimal.valueOf(30));
        item.setQuantity(4);

        BigDecimal total = PriceEngine.calculateTotalPrice(List.of(item));

        assertThat(total).isEqualByComparingTo(BigDecimal.valueOf(130));
        assertThat(item.getTotalPrice()).isEqualByComparingTo(BigDecimal.valueOf(130));
    }

    @Test
    void testEmptyItemList() {
        BigDecimal total = PriceEngine.calculateTotalPrice(List.of());
        assertThat(total).isEqualByComparingTo(BigDecimal.ZERO);
    }

}

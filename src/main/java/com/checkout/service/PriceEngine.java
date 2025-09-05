package com.checkout.service;

import com.checkout.domain.Item;

import java.math.BigDecimal;
import java.util.List;

public class PriceEngine {

    public static BigDecimal calculateTotalPrice(List<Item> items) {
        BigDecimal total = BigDecimal.ZERO;

        for (Item item : items) {
            int quantity = item.getQuantity();
            BigDecimal linePrice;

            if (item.getRequiredQuantity() != null
                    && item.getRequiredQuantity() > 0
                    && item.getSpecialPrice() != null
                    && quantity >= item.getRequiredQuantity()) {

                int fullGroups = quantity / item.getRequiredQuantity();
                int remainder = quantity % item.getRequiredQuantity();

                BigDecimal specialTotal = item.getSpecialPrice()
                        .multiply(BigDecimal.valueOf(item.getRequiredQuantity()))
                        .multiply(BigDecimal.valueOf(fullGroups));

                BigDecimal normalTotal = item.getNormalPrice()
                        .multiply(BigDecimal.valueOf(remainder));

                linePrice = specialTotal.add(normalTotal);

            } else {
                linePrice = item.getNormalPrice().multiply(BigDecimal.valueOf(quantity));
            }

            item.setTotalPrice(linePrice);
            total = total.add(linePrice);
        }

        return total;
    }

}

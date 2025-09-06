package com.checkout.service;

import com.checkout.domain.BundleOffer;
import com.checkout.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
@RequiredArgsConstructor
public class PriceEngine {

    private final BundleService bundleService;

    public BigDecimal calculateTotalPrice(List<Item> items) {
        BigDecimal total = BigDecimal.ZERO;
        total = calculatePriceWithSpecials(total, items);

        BigDecimal discount = calculateBundleDiscount(items);
        return total.subtract(discount);
    }

    private BigDecimal calculatePriceWithSpecials(BigDecimal total, List<Item> items) {
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

    public BigDecimal calculateBundleDiscount(List<Item> items) {
        BigDecimal discount = BigDecimal.ZERO;

        for (BundleOffer offer : bundleService.getBundleOffers()) {
            Item firstItem = findItemByName(items, offer.getFirstItem());
            Item secondItem = findItemByName(items, offer.getSecondItem());

            if (firstItem != null && secondItem != null) {
                int bundles = Math.min(firstItem.getQuantity(), secondItem.getQuantity());
                discount = discount.add(
                        offer.getDiscount().multiply(BigDecimal.valueOf(bundles))
                );
            }
        }
        return discount;
    }

    private static Item findItemByName(List<Item> items, String name) {
        return items.stream()
                .filter(i -> i.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}

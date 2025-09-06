package com.checkout.service;

import com.checkout.domain.BundleOffer;
import com.checkout.domain.Item;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class PriceEngine {

    private final BundleService bundleService;

    public PriceEngine(BundleService bundleService) {
        this.bundleService = bundleService;
    }

    public BigDecimal calculateTotalPrice(List<Item> items) {
        BigDecimal total = BigDecimal.ZERO;
        total = calculatePriceWithSpecials(total, items);
        total = applyBundleDiscount(total, items);

        return total;
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

    private BigDecimal applyBundleDiscount(BigDecimal total, List<Item> items) {
        for (BundleOffer offer : bundleService.getBundleOffers()) {
            Item firstItem = findItemByName(items, offer.getFirstItem());
            Item secondItem = findItemByName(items, offer.getSecondItem());

            if (firstItem != null && secondItem != null) {
                int bundles = Math.min(firstItem.getQuantity(), secondItem.getQuantity());
                BigDecimal discount = offer.getDiscount().multiply(BigDecimal.valueOf(bundles));
                total = total.subtract(discount);
            }
        }
        return total;
    }

    private static Item findItemByName(List<Item> items, String name) {
        return items.stream()
                .filter(i -> i.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

}

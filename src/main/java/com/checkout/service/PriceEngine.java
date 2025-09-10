package com.checkout.service;

import com.checkout.domain.BundleOffer;
import com.checkout.domain.Checkout;
import com.checkout.domain.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PriceEngine {

    private final BundleService bundleService;
    private final CatalogService catalogService;

    public BigDecimal calculateTotalPrice(Checkout checkout) {
        BigDecimal total = BigDecimal.ZERO;

        for (Map.Entry<String, Integer> entry : checkout.getItems().entrySet()) {
            String productId = entry.getKey();
            int quantity = entry.getValue();

            Product product = catalogService.findById(productId);
            BigDecimal linePrice = calculateLinePrice(product, quantity);

            total = total.add(linePrice);
        }

        BigDecimal discount = calculateBundleDiscount(checkout);
        return total.subtract(discount);
    }

    public BigDecimal calculateBundleDiscount(Checkout checkout) {
        BigDecimal discount = BigDecimal.ZERO;
        Map<String, Integer> items = checkout.getItems();

        for (Map.Entry<String, Integer> entry : items.entrySet()) {
            String firstItem = entry.getKey();
            int firstQty = entry.getValue();

            for (BundleOffer offer : bundleService.getBundleOffers()) {
                if (!offer.getFirstItem().equals(firstItem)) continue;

                String secondItem = offer.getSecondItem();
                Integer secondQty = items.get(secondItem);

                if (secondQty != null) {
                    int bundles = Math.min(firstQty, secondQty);
                    discount = discount.add(offer.getDiscount().multiply(BigDecimal.valueOf(bundles)));
                }
            }
        }
        return discount;
    }

    private BigDecimal calculateLinePrice(Product product, int quantity) {
        Integer requiredQty = product.getRequiredQuantity();
        BigDecimal specialPrice = product.getSpecialPrice();
        BigDecimal normalPrice = product.getNormalPrice();

        if (requiredQty != null && requiredQty > 0
                && specialPrice != null
                && quantity >= requiredQty) {

            int fullGroups = quantity / requiredQty;
            int remainder = quantity % requiredQty;

            BigDecimal specialTotal = specialPrice.multiply(BigDecimal.valueOf((long) fullGroups * requiredQty));
            BigDecimal normalTotal = normalPrice.multiply(BigDecimal.valueOf(remainder));

            return specialTotal.add(normalTotal);
        } else {
            return normalPrice.multiply(BigDecimal.valueOf(quantity));
        }
    }

}

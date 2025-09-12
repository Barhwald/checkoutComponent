package com.checkout.service;

import com.checkout.domain.BundleOffer;
import com.checkout.domain.Checkout;
import com.checkout.domain.ItemDto;
import com.checkout.domain.Product;
import com.checkout.exception.CheckoutNotFoundException;
import com.checkout.repository.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CatalogService catalogService;
    private final BundleService bundleService;
    private final CheckoutRepository checkoutRepository;
    private final PriceEngine priceEngine;

    public Checkout initializeCheckout() {
        Checkout checkout = new Checkout();
        checkoutRepository.addCheckout(checkout);
        return checkout;
    }

    public Checkout scanItem(String checkoutId, ItemDto itemDto) {
        Checkout checkout = getActiveCheckout(checkoutId);

        checkout.getItems().merge(itemDto.getId(), itemDto.getQuantity(), Integer::sum);

        calculatePrice(checkout);
        return checkout;
    }

    public Checkout finalizeCheckout(String checkoutId) {
        Checkout checkout = getActiveCheckout(checkoutId);
        checkout.setActive(false);
        return checkout;
    }

    private Checkout getActiveCheckout(String checkoutId) {
        Checkout checkout = checkoutRepository.findById(checkoutId)
                .orElseThrow(() -> new CheckoutNotFoundException("Checkout not found: " + checkoutId));

        if (!checkout.isActive()) {
            throw new IllegalStateException("Checkout has already been finalized: " + checkoutId);
        }

        return checkout;
    }

    private void calculatePrice(Checkout checkout) {
        Map<String, Product> catalogMap = catalogService.getAllProducts().stream()
                .collect(Collectors.toMap(Product::getId, p -> p));

        List<BundleOffer> bundleOffers = bundleService.getBundleOffers();

        checkout.setFinalPrice(priceEngine.calculateTotalPrice(checkout, catalogMap, bundleOffers));
        checkout.setBundleDiscount(priceEngine.calculateBundleDiscount(checkout, bundleOffers));
    }
}
package com.checkout.service;

import com.checkout.domain.Checkout;
import com.checkout.domain.ItemDto;
import com.checkout.exception.CheckoutNotFoundException;
import com.checkout.repository.CheckoutRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final PriceEngine priceEngine;
    private final CheckoutRepository checkoutRepository;

    public Checkout initializeCheckout() {
        Checkout checkout = new Checkout();
        checkoutRepository.getCheckouts().put(checkout.getId(), checkout);
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
        checkout.setFinalPrice(priceEngine.calculateTotalPrice(checkout));
        checkout.setBundleDiscount(priceEngine.calculateBundleDiscount(checkout));
    }

}
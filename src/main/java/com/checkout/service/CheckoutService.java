package com.checkout.service;

import com.checkout.domain.Item;
import com.checkout.domain.Receipt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final PriceEngine priceEngine;
    private final CatalogService catalogService;

    @Getter
    private CheckoutSession session;

    public Receipt scanItem(String name) {
        if (session == null || !session.isActive()) {
            session = new CheckoutSession();
        }

        Item item = catalogService.findByName(name);

        session.initializeReceipt();
        settleReceipt(session.getReceipt(), item);
        return session.getReceipt();
    }

    public Receipt checkout() {
        if (session == null) throw new RuntimeException("Session not found");
        if (!session.isActive()) throw new RuntimeException("Session has already been finalized");
        Receipt receiptToReturn = session.getReceipt();
        session.finalizeSession();
        return receiptToReturn;
    }

    private void settleReceipt(Receipt receipt, Item item) {
        receipt.setItemQuantity(item);
        BigDecimal totalPrice = priceEngine.calculateTotalPrice(receipt.getItems());
        receipt.setTotalPrice(totalPrice);
    }

}
package com.checkout.service;

import com.checkout.domain.Item;
import com.checkout.domain.Receipt;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

import static com.checkout.service.PriceEngine.calculateTotalPrice;

@Component
@Getter
@Setter
public class CheckoutSession {

    private String sessionId;
    private Receipt receipt;
    private boolean active = true;

    public CheckoutSession() {
        this.sessionId = UUID.randomUUID().toString();
    }

    public void scan(Item item) {
        if (!active) {
            throw new IllegalStateException("Cannot scan items: session is finalized");
        }

        if (receipt == null) {
            receipt = new Receipt();
        }

        receipt.setItemQuantity(item);
        BigDecimal totalPrice = calculateTotalPrice(receipt.getItems());
        receipt.setTotalPrice(totalPrice);
    }

    public void finalizeSession() {
        this.active = false;
        this.receipt = null;
    }
}
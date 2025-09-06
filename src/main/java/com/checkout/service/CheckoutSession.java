package com.checkout.service;

import com.checkout.domain.Receipt;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.UUID;

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

    public void initializeReceipt() {
        if (receipt == null) {
            receipt = new Receipt();
        }
    }

    public void finalizeSession() {
        this.active = false;
        this.receipt = null;
    }
}
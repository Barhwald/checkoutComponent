package com.checkout.service;

import com.checkout.domain.Item;
import com.checkout.domain.Receipt;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckoutService {

    private final CatalogService catalogService;

    @Getter
    private CheckoutSession session;

    public Receipt scanItem(String name) {
        if (session == null || !session.isActive()) {
            session = new CheckoutSession();
        }

        Item item = catalogService.findByName(name);
        session.scan(item);
        return session.getReceipt();
    }

    public Receipt checkout() {
        if (session == null) throw new RuntimeException("Session not found");
        if (!session.isActive()) throw new RuntimeException("Session has already been finalized");
        Receipt receiptToReturn = session.getReceipt();
        session.finalizeSession();
        return receiptToReturn;
    }

}
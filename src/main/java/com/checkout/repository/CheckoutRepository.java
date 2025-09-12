package com.checkout.repository;

import com.checkout.domain.Checkout;
import lombok.Getter;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Getter
@Repository
public class CheckoutRepository {

    private final Map<String, Checkout> checkouts;

    public CheckoutRepository(Map<String, Checkout> checkouts) {
        this.checkouts = new HashMap<>();
    }

    public Optional<Checkout> findById(String id) {
        return Optional.ofNullable(checkouts.get(id));
    }

    public void addCheckout(Checkout checkout) {
        checkouts.put(checkout.getId(), checkout);
    }

}

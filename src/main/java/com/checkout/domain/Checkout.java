package com.checkout.domain;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Getter
public class Checkout {

    private final String id;
    private final Map<String, Integer> items;
    @Setter
    private BigDecimal finalPrice;
    @Setter
    private BigDecimal bundleDiscount;
    @Setter
    private boolean isActive;

    public Checkout() {
        this.id = UUID.randomUUID().toString();
        this.items = new HashMap<>();
        this.finalPrice = BigDecimal.ZERO;
        this.bundleDiscount = BigDecimal.ZERO;
        this.isActive = true;
    }

}

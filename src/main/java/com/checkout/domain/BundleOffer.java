package com.checkout.domain;

import lombok.*;

import java.math.BigDecimal;

@EqualsAndHashCode
@Getter
@Setter
@AllArgsConstructor
public class BundleOffer {
    private String firstItem;
    private String secondItem;
    private BigDecimal discount;
}
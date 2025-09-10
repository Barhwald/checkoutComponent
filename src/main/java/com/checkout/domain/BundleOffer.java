package com.checkout.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class BundleOffer {
    private String firstItem;
    private String secondItem;
    private BigDecimal discount;
}
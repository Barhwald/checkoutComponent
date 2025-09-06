package com.checkout.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@ToString
@Getter
@Setter
@AllArgsConstructor
public class BundleOffer {
    private String firstItem;
    private String secondItem;
    private BigDecimal discount;
}
package com.checkout.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
public class Product {

    private String id;
    private BigDecimal normalPrice;
    private Integer requiredQuantity;
    private BigDecimal specialPrice;

}

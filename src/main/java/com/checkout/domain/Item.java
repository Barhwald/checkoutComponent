package com.checkout.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@NoArgsConstructor
@Setter
@Getter
public class Item {

    private String name;
    @JsonIgnore
    private BigDecimal normalPrice;
    private BigDecimal totalPrice;
    @JsonIgnore
    private Integer requiredQuantity;
    private int quantity;
    @JsonIgnore
    private BigDecimal specialPrice;

}

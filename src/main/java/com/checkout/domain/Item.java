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

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final Item item = new Item();

        public Builder name(String name) {
            item.name = name;
            return this;
        }

        public Builder normalPrice(BigDecimal normalPrice) {
            item.normalPrice = normalPrice;
            return this;
        }

        public Builder totalPrice(BigDecimal totalPrice) {
            item.totalPrice = totalPrice;
            return this;
        }

        public Item build() {
            return item;
        }
    }

}

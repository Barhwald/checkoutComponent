package com.checkout.domain;

import lombok.Getter;

@Getter
public class ItemDto {

    private String id;
    private Integer quantity;

    @Override
    public String toString() {
        return "ItemDto{" +
                "id='" + id + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}

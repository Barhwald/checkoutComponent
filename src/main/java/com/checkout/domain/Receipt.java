package com.checkout.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class Receipt {

    private List<Item> items = new ArrayList<>();
    private BigDecimal totalPrice;

    public void setItemQuantity(Item item) {
        for (Item it : items) {
            if (it.getName().equals(item.getName())) {
                it.setQuantity(item.getQuantity() + 1);
                return;
            }
        }
        item.setQuantity(1);
        items.add(item);
    }

}

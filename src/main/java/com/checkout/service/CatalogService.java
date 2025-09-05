package com.checkout.service;

import com.checkout.config.OfferConfig;
import com.checkout.domain.Item;
import com.checkout.exception.ItemNotFoundException;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Getter
public class CatalogService {
     final List<Item> items;

    public CatalogService(OfferConfig config) {
        this.items = new ArrayList<>(config.getCatalogItems());
    }

    public Item findByName(String name) {
        Optional<Item> optionalItem = items.stream()
                .filter(item -> item.getName().equals(name))
                .findFirst();

        return optionalItem.orElseThrow(() ->
                new ItemNotFoundException("Item with name " + name + " not found in the catalogue"));

    }
}
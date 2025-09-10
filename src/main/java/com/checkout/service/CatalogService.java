package com.checkout.service;

import com.checkout.domain.Product;
import com.checkout.exception.ItemNotFoundException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
public class CatalogService {

    private final List<Product> items;

    public CatalogService(ObjectMapper objectMapper) {
        this.items = loadCatalogFromJson(objectMapper);
    }

    private List<Product> loadCatalogFromJson(ObjectMapper objectMapper) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("catalog.json")) {
            if (is == null) {
                throw new IllegalStateException("Catalog file not found: " + "catalog.json");
            }
            return objectMapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load catalog file: " + "catalog.json", e);
        }
    }

    public Product findById(String id) {
        return items.stream()
                .filter(item -> item.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found in the catalogue"));
    }

}

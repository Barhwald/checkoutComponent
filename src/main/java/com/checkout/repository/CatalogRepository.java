package com.checkout.repository;

import com.checkout.domain.Product;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Repository
public class CatalogRepository {

    private final List<Product> items;

    public CatalogRepository(ObjectMapper objectMapper) {
        this.items = loadCatalogFromJson(objectMapper);
    }

    private List<Product> loadCatalogFromJson(ObjectMapper objectMapper) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("catalog.json")) {
            if (is == null) {
                throw new IllegalStateException("Catalog file not found: catalog.json");
            }
            return objectMapper.readValue(is, new TypeReference<>() {});
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load catalog file: catalog.json", e);
        }
    }

    public Optional<Product> findById(String id) {
        return items.stream()
                .filter(item -> item.getId().equalsIgnoreCase(id))
                .findFirst();
    }

    public List<Product> findAll() {
        return Collections.unmodifiableList(items);
    }
}

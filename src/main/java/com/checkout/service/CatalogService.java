package com.checkout.service;

import com.checkout.domain.Product;
import com.checkout.exception.ItemNotFoundException;
import com.checkout.repository.CatalogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public Product findById(String id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found in the catalogue"));
    }

    public List<Product> getAllProducts() {
        return new ArrayList<>(catalogRepository.findAll());
    }

}

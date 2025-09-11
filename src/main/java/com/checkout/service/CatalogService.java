package com.checkout.service;

import com.checkout.domain.Product;
import com.checkout.exception.ItemNotFoundException;
import com.checkout.repository.CatalogRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CatalogService {

    private final CatalogRepository catalogRepository;

    public Product findById(String id) {
        return catalogRepository.findById(id)
                .orElseThrow(() -> new ItemNotFoundException("Item with ID " + id + " not found in the catalogue"));
    }

}

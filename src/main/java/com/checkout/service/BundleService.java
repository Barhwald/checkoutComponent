package com.checkout.service;

import com.checkout.domain.BundleOffer;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@Getter
public class BundleService {

    private final List<BundleOffer> bundleOffers;

    public BundleService(ObjectMapper objectMapper) {
        this.bundleOffers = loadBundlesFromJson(objectMapper);
    }

    private List<BundleOffer> loadBundlesFromJson(ObjectMapper objectMapper) {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("bundles.json")) {
            if (is == null) {
                throw new IllegalStateException("Bundle file not found: " + "bundles.json");
            }
            return objectMapper.readValue(is, new TypeReference<>() {
            });
        } catch (IOException e) {
            throw new IllegalStateException("Failed to load bundle file: " + "bundles.json", e);
        }
    }

}
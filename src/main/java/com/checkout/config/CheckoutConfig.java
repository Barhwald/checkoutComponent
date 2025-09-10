package com.checkout.config;

import com.checkout.domain.BundleOffer;
import com.checkout.domain.Product;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "checkout")
@Getter
@Setter

public class CheckoutConfig {
    private List<Product> catalogItems = new ArrayList<>();
    private List<BundleOffer> bundleOffers = new ArrayList<>();
}

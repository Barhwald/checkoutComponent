package com.checkout.service;

import com.checkout.config.CheckoutConfig;
import com.checkout.domain.BundleOffer;
import lombok.Getter;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Getter
public class BundleService {

    final List<BundleOffer> bundleOffers;

    public BundleService(CheckoutConfig checkoutConfig) {
        this.bundleOffers = new ArrayList<>(checkoutConfig.getBundleOffers());
    }

}

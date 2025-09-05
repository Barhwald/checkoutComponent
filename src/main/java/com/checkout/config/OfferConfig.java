package com.checkout.config;

import com.checkout.domain.Item;
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

public class OfferConfig {
    private List<Item> catalogItems = new ArrayList<>();
}

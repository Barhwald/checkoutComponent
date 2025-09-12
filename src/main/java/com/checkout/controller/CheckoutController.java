package com.checkout.controller;

import com.checkout.domain.Checkout;
import com.checkout.domain.ItemDto;
import com.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkouts")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping
    public ResponseEntity<Checkout> initializeCheckout() {
        Checkout checkout = checkoutService.initializeCheckout();
        return ResponseEntity.status(HttpStatus.CREATED).body(checkout);
    }

    @PutMapping(value = "/{checkoutId}/items", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Checkout> scanItem(@PathVariable String checkoutId, @RequestBody ItemDto itemDto) {
        Checkout checkout = checkoutService.scanItem(checkoutId, itemDto);
        return ResponseEntity.ok(checkout);
    }

    @PutMapping("/{checkoutId}/finalize")
    public ResponseEntity<Checkout> finalizeCheckout(@PathVariable String checkoutId) {
            return ResponseEntity.ok(checkoutService.finalizeCheckout(checkoutId));
    }
}
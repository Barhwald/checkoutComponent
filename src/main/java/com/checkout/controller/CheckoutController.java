package com.checkout.controller;

import com.checkout.domain.ItemDto;
import com.checkout.domain.Receipt;
import com.checkout.service.CheckoutService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/items")
@RequiredArgsConstructor
public class CheckoutController {

    private final CheckoutService checkoutService;

    @PostMapping(value = "/scan", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Receipt> scanItem(@RequestBody ItemDto itemDto) {
        Receipt receipt = checkoutService.scanItem(itemDto.getName());
        return ResponseEntity.ok(receipt);
    }

    @GetMapping("/finalize")
    public ResponseEntity<Receipt> finalizeTransaction() {
        if (checkoutService.getSession() != null) {
            return ResponseEntity.ok(checkoutService.checkout());
        } else {
            throw new RuntimeException("No active session to finalize");
        }
    }
}
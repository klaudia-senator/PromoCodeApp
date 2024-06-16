package com.example.promocode_app.controller;

import com.example.promocode_app.model.Purchase;
import com.example.promocode_app.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    @PostMapping("/simulate")
    public ResponseEntity<ResponseEntity<Purchase>> simulatePurchase(@RequestParam Long productId, @RequestParam(required = false) String promoCode) {
        return ResponseEntity.ok(purchaseService.simulatePurchase(productId, promoCode));
    }
}


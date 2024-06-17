package com.example.promocode_app.controller;

import com.example.promocode_app.model.Purchase;
import com.example.promocode_app.service.PurchaseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/purchases")
public class PurchaseController {
    private final PurchaseService purchaseService;
    public PurchaseController(PurchaseService purchaseService) {
        this.purchaseService = purchaseService;
    }
    @PostMapping
    public ResponseEntity<Purchase> savePurchase(@RequestBody Purchase purchase) {
        Purchase savedPurchase = purchaseService.save(purchase);
        return ResponseEntity.ok(savedPurchase);
    }
    @GetMapping
    public ResponseEntity<List<Purchase>> getAllPurchases() {
        List<Purchase> purchases = purchaseService.findAll();
        return ResponseEntity.ok(purchases);
    }
    @PostMapping("/simulate")
    public ResponseEntity<ResponseEntity<Purchase>> simulatePurchase(@RequestParam Long productId, @RequestParam(required = false) String promoCode) {
        return ResponseEntity.ok(purchaseService.simulatePurchase(productId, promoCode));
    }
}


package com.example.promocode_app.service;

import com.example.promocode_app.model.Purchase;
import com.example.promocode_app.repository.PurchaseRepository;

import java.util.List;

public class PurchaseService {
    private final PurchaseRepository purchaseRepository;

    public PurchaseService(PurchaseRepository purchaseRepository) {
        this.purchaseRepository = purchaseRepository;
    }
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }
}

package com.example.promocode_app.service;

import com.example.promocode_app.model.Product;
import com.example.promocode_app.model.PromoCode;
import com.example.promocode_app.model.Purchase;
import com.example.promocode_app.repository.ProductRepository;
import com.example.promocode_app.repository.PurchaseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PurchaseService {
    private final PurchaseRepository purchaseRepository;
    private final ProductService productService;
    private final PromoCodeService promoCodeService;

    public PurchaseService(PurchaseRepository purchaseRepository, ProductService productService, PromoCodeService promoCodeService) {
        this.purchaseRepository = purchaseRepository;
        this.productService = productService;
        this.promoCodeService = promoCodeService;
    }
    public Purchase save(Purchase purchase) {
        return purchaseRepository.save(purchase);
    }
    public List<Purchase> findAll() {
        return purchaseRepository.findAll();
    }
    public Purchase findById(Long id) {
        Optional<Purchase> purchase = purchaseRepository.findById(id);
        return purchase.orElse(null); }

    public ResponseEntity<Purchase> simulatePurchase(Long productId, String promoCodeId) {
        Optional<Product> productOpt = productService.findById(productId);
        Optional<PromoCode> promoCodeOpt = promoCodeService.findById(promoCodeId);

        if (!productOpt.isPresent() || !promoCodeOpt.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOpt.get();
        PromoCode promoCode = promoCodeOpt.get();

        if (promoCode.getExpirationDate().isBefore(LocalDate.now()) ||
                !promoCode.getCurrency().equals(product.getCurrency()) ||
                promoCode.getUsage() >= promoCode.getMaxUsage()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        BigDecimal regularPrice = product.getPrice();
        BigDecimal discountApplied = promoCode.getDiscount();
        BigDecimal finalPrice = regularPrice.subtract(discountApplied);

        promoCode.setUsage(promoCode.getUsage() + 1);
        promoCodeService.save(promoCode);

        Purchase purchase = new Purchase();
        purchase.setProduct(product);
        purchase.setPromoCode(promoCode);
        purchase.setDateOfPurchase(LocalDate.now());
        purchase.setRegularPrice(regularPrice);
        purchase.setDiscount(discountApplied);
        purchase.setFinalPrice(finalPrice);

        Purchase savedPurchase = purchaseRepository.save(purchase);

        return ResponseEntity.ok(savedPurchase);
    }
}

package com.example.promocode_app.service;

import com.example.promocode_app.model.Product;
import com.example.promocode_app.model.PromoCode;
import com.example.promocode_app.repository.PromoCodeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;

    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }
    public PromoCode save(PromoCode promoCode) {
        validatePromoCode(promoCode);
        return promoCodeRepository.save(promoCode); }

    private void validatePromoCode(PromoCode promoCode) {
        String code = promoCode.getCode();
        if (code.length() < 3 || code.length() > 24) {
            throw new IllegalArgumentException("Promo code must be between 3 and 24 characters long.");
        }
        if (!code.matches("^[a-zA-Z0-9]+$")) {
            throw new IllegalArgumentException("Promo code must be alphanumeric and cannot contain spaces or special characters.");
        }
    }
    public List<PromoCode> findAll() {
        return promoCodeRepository.findAll();
    }
    public Optional<PromoCode> findById(String code) {
        return promoCodeRepository.findById(code);
    }
    public void deleteById(String code) {
        promoCodeRepository.deleteById(code);
    }

    public BigDecimal calculateDiscount(BigDecimal regularPrice, PromoCode promoCode) {
        BigDecimal discountPrice = regularPrice.subtract(promoCode.getDiscount());
        return discountPrice.max(BigDecimal.ZERO);
    }

    public ResponseEntity<BigDecimal> getDiscountPrice(Product product, PromoCode promoCode) {
        if (promoCode.getExpirationDate().isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(product.getPrice());
        }
        if (!promoCode.getCurrency().equals(product.getCurrency())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(product.getPrice());
        }
        if (promoCode.getUsage() >= promoCode.getMaxUsage()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(product.getPrice());
        }
        BigDecimal discountPrice = calculateDiscount(product.getPrice(), promoCode);
        return ResponseEntity.ok(discountPrice);
    }
}


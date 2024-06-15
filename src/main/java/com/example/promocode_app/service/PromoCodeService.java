package com.example.promocode_app.service;

import com.example.promocode_app.model.PromoCode;
import com.example.promocode_app.repository.PromoCodeRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class PromoCodeService {
    private final PromoCodeRepository promoCodeRepository;

    public PromoCodeService(PromoCodeRepository promoCodeRepository) {
        this.promoCodeRepository = promoCodeRepository;
    }
    public PromoCode save(PromoCode promoCode) {
        return promoCodeRepository.save(promoCode);
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
        if (promoCode.getDiscount().compareTo(BigDecimal.ZERO) < 0) {
            return BigDecimal.ZERO;
        }
        BigDecimal discountPrice = regularPrice.subtract(promoCode.getDiscount());
        return discountPrice.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : discountPrice;
    }
}

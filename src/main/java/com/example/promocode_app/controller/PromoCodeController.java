package com.example.promocode_app.controller;

import com.example.promocode_app.model.Product;
import com.example.promocode_app.model.PromoCode;
import com.example.promocode_app.service.PromoCodeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/promocodes")
public class PromoCodeController {
    private final PromoCodeService promoCodeService;

    public PromoCodeController(PromoCodeService promoCodeService) {
        this.promoCodeService = promoCodeService;
    }
    @PostMapping
    public ResponseEntity<PromoCode> createPromoCode(@RequestBody PromoCode promoCode) {
        PromoCode createdPromoCode = promoCodeService.save(promoCode);
        return ResponseEntity.ok(createdPromoCode);
    }
    @GetMapping
    public ResponseEntity<List<PromoCode>> getAllPromoCodes() {
        List<PromoCode> promoCodes = promoCodeService.findAll();
        return ResponseEntity.ok(promoCodes);
    }
    @GetMapping("/{code}")
    public ResponseEntity<PromoCode> getPromoCodeByCode(@PathVariable String code) {
        return promoCodeService.findById(code)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping("/discount")
    public ResponseEntity<BigDecimal> calculateDiscount(@RequestParam("price") BigDecimal price,
                                                        @RequestParam("code") String code) {
        try {
            Product product = new Product(price);

            Optional<PromoCode> promoCodeOptional = promoCodeService.findById(code);
            if (!promoCodeOptional.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(BigDecimal.ZERO);
            }
            PromoCode promoCode = promoCodeOptional.get();
            return promoCodeService.getDiscountPrice(product, promoCode);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BigDecimal.ZERO);
        }
    }
}

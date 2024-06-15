package com.example.promocode_app.controller;

import com.example.promocode_app.model.Product;
import com.example.promocode_app.model.PromoCode;
import com.example.promocode_app.service.PromoCodeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}

package com.example.promocode_app.controller;

import com.example.promocode_app.model.PromoCode;
import com.example.promocode_app.service.PromoCodeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PromoCodeControllerTest {
    @InjectMocks
    private PromoCodeController promoCodeController;
    @Mock
    private PromoCodeService promoCodeService;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testCreatePromoCode() {
        PromoCode promoCode = new PromoCode();
        promoCode.setCode("PROMO10");
        promoCode.setDiscount(BigDecimal.valueOf(10));
        promoCode.setExpirationDate(LocalDate.now().plusDays(1));
        promoCode.setCurrency("USD");
        promoCode.setMaxUsage(20);

        when(promoCodeService.save(any(PromoCode.class))).thenReturn(promoCode);

        ResponseEntity<PromoCode> response = promoCodeController.createPromoCode(promoCode);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(promoCode, response.getBody());
        verify(promoCodeService, times(1)).save(promoCode);
    }
    @Test
    void testGetAllPromoCode() {
        PromoCode promoCode1 = new PromoCode();
        promoCode1.setCode("PROMO10");
        promoCode1.setDiscount(BigDecimal.valueOf(10));
        promoCode1.setExpirationDate(LocalDate.now().plusDays(1));
        promoCode1.setCurrency("USD");
        promoCode1.setMaxUsage(20);

        PromoCode promoCode2 = new PromoCode();
        promoCode2.setCode("SUMMER20");
        promoCode2.setDiscount(BigDecimal.valueOf(20));
        promoCode2.setExpirationDate(LocalDate.now().plusDays(30));
        promoCode2.setCurrency("PLN");
        promoCode2.setMaxUsage(5);

        List<PromoCode> promoCodeList = Arrays.asList(promoCode1, promoCode2);

        when(promoCodeService.findAll()).thenReturn(promoCodeList);

        ResponseEntity<List<PromoCode>> response = promoCodeController.getAllPromoCodes();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(promoCodeList, response.getBody());
        verify(promoCodeService, times(1)).findAll();
    }
    @Test
    void testGetPromoCodeByCode() {
        PromoCode promoCode = new PromoCode();
        promoCode.setCode("PROMO10");
        promoCode.setDiscount(BigDecimal.valueOf(10));
        promoCode.setExpirationDate(LocalDate.now().plusDays(1));
        promoCode.setCurrency("USD");
        promoCode.setMaxUsage(20);

        when(promoCodeService.findById("PROMO10")).thenReturn(Optional.of(promoCode));

        ResponseEntity<PromoCode> response = promoCodeController.getPromoCodeByCode("PROMO10");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(promoCode, response.getBody());
        verify(promoCodeService, times(1)).findById("PROMO10");
    }
    @Test
    void testCalculatedDiscount() {
        PromoCode promoCode = new PromoCode();
        promoCode.setCode("PROMO10");
        promoCode.setDiscount(BigDecimal.valueOf(10));
        promoCode.setExpirationDate(LocalDate.now().plusDays(1));
        promoCode.setCurrency("USD");
        promoCode.setMaxUsage(20);

        when(promoCodeService.findById("PROMO10")).thenReturn(Optional.of(promoCode));
        when(promoCodeService.calculateDiscount(BigDecimal.valueOf(100), promoCode)).thenReturn(BigDecimal.valueOf(90));

        ResponseEntity<BigDecimal> response = promoCodeController.calculateDiscount(BigDecimal.valueOf(100), "PROMO10");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(BigDecimal.valueOf(90), response.getBody());
        verify(promoCodeService, times(1)).findById("PROMO10");
        verify(promoCodeService, times(1)).calculateDiscount(BigDecimal.valueOf(100), promoCode);
    }
}
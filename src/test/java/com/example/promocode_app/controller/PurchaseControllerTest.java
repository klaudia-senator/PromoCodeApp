package com.example.promocode_app.controller;

import com.example.promocode_app.model.Product;
import com.example.promocode_app.model.PromoCode;
import com.example.promocode_app.model.Purchase;
import com.example.promocode_app.service.PromoCodeService;
import com.example.promocode_app.service.PurchaseService;
import com.example.promocode_app.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PurchaseControllerTest {
    private MockMvc mockMvc;
    @InjectMocks
    private PurchaseController purchaseController;
    private ObjectMapper objectMapper;

    @Mock
    private PurchaseService purchaseService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(purchaseController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void testSavePurchase() {
        Purchase purchaseToSave = new Purchase();
        purchaseToSave.setId(1L);
        purchaseToSave.setDateOfPurchase(LocalDate.now());
        purchaseToSave.setRegularPrice(BigDecimal.valueOf(1500));
        purchaseToSave.setDiscount(BigDecimal.valueOf(10));
        purchaseToSave.setFinalPrice(BigDecimal.valueOf(1490));

        when(purchaseService.save(any())).thenReturn(purchaseToSave);

        ResponseEntity<Purchase> response = purchaseController.savePurchase(purchaseToSave);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(purchaseToSave, response.getBody());
        verify(purchaseService, times(1)).save(any());
    }

    @Test
    void testGetAllPurchases() {

        Product product1 = new Product();
        product1.setId(1L);
        product1.setName("Product 1");
        product1.setCurrency("USD");
        product1.setPrice(BigDecimal.valueOf(1500));

        PromoCode promoCode1 = new PromoCode();
        promoCode1.setCode("PROMO10");
        promoCode1.setExpirationDate(LocalDate.now().plusMonths(1));
        promoCode1.setDiscount(BigDecimal.valueOf(10));
        promoCode1.setMaxUsage(100);
        promoCode1.setUsage(0);
        promoCode1.setCurrency("USD");

        Purchase purchase1 = new Purchase();
        purchase1.setId(1L);
        purchase1.setProduct(product1);
        purchase1.setPromoCode(promoCode1);
        purchase1.setDateOfPurchase(LocalDate.now());
        purchase1.setRegularPrice(product1.getPrice());
        purchase1.setDiscount(promoCode1.getDiscount());
        purchase1.setFinalPrice(product1.getPrice().subtract(promoCode1.getDiscount()));

        Product product2 = new Product();
        product2.setId(2L);
        product2.setName("Product 2");
        product2.setCurrency("USD");
        product2.setPrice(BigDecimal.valueOf(500));

        PromoCode promoCode2 = new PromoCode();
        promoCode2.setCode("SUMMER123");
        promoCode2.setExpirationDate(LocalDate.now().plusMonths(1));
        promoCode2.setDiscount(BigDecimal.valueOf(20));
        promoCode2.setMaxUsage(50);
        promoCode2.setUsage(0);
        promoCode2.setCurrency("USD");

        Purchase purchase2 = new Purchase();
        purchase2.setId(2L);
        purchase2.setProduct(product2);
        purchase2.setPromoCode(promoCode2);
        purchase2.setDateOfPurchase(LocalDate.now());
        purchase2.setRegularPrice(product2.getPrice());
        purchase2.setDiscount(promoCode2.getDiscount());
        purchase2.setFinalPrice(product2.getPrice().subtract(promoCode2.getDiscount()));

        List<Purchase> purchasesList = Arrays.asList(purchase1, purchase2);

        when(purchaseService.findAll()).thenReturn(purchasesList);

        ResponseEntity<List<Purchase>> response = purchaseController.getAllPurchases();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(purchasesList, response.getBody());
        verify(purchaseService, times(1)).findAll();
    }

    @Test
    void testSimulatePurchase() {
        Product product = new Product();
        product.setId(1L);
        product.setPrice(BigDecimal.valueOf(100));

        PromoCode promoCode = new PromoCode();
        promoCode.setCode("PROMO10");
        promoCode.setExpirationDate(LocalDate.now().plusDays(1));
        promoCode.setCurrency("USD");
        promoCode.setMaxUsage(10);
        promoCode.setUsage(0);
        promoCode.setDiscount(BigDecimal.TEN);

        Purchase simulatedPurchase = new Purchase();
        simulatedPurchase.setId(1L);
        simulatedPurchase.setProduct(product);
        simulatedPurchase.setPromoCode(promoCode);
        simulatedPurchase.setDateOfPurchase(LocalDate.now());
        simulatedPurchase.setRegularPrice(product.getPrice());
        simulatedPurchase.setDiscount(promoCode.getDiscount());
        simulatedPurchase.setFinalPrice(product.getPrice().subtract(promoCode.getDiscount()));

        when(purchaseService.simulatePurchase(anyLong(), anyString())).thenReturn(ResponseEntity.ok(simulatedPurchase));

        ResponseEntity<Purchase> response = purchaseController.simulatePurchase(1L, "PROMO10");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(simulatedPurchase, response.getBody());
    }
}
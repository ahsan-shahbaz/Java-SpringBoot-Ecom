package com.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProductFetchTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void testGetProduct() {
        System.out.println("TEST GET PRODUCT 1 VIA HTTP...");
        ResponseEntity<String> response = restTemplate.getForEntity("/api/products/1", String.class);
        System.out.println("HTTP STATUS: " + response.getStatusCode());
        System.out.println("RESPONSE BODY: " + response.getBody());
    }
}

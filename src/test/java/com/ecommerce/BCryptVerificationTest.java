package com.ecommerce;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BCryptVerificationTest {

    @Test
    public void testPasswordMatch() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String generatedHash = encoder.encode("password");
        System.out.println("VALID_HASH:" + generatedHash + ":END_HASH");
        assertTrue(encoder.matches("password", generatedHash), "Password should match the generated hash");
    }
}

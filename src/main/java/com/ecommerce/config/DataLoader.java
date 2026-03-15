package com.ecommerce.config;

import com.ecommerce.entity.Product;
import com.ecommerce.entity.User;
import com.ecommerce.repository.ProductRepository;
import com.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoader implements CommandLineRunner {

    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // loadUsers();
        // loadProducts();
        log.info("ℹ️ Automatic data seeding is disabled. Using MSSQL database.");
    }

    private void loadUsers() {
        // Disabled to prevent interference with manual database management
    }

    private void loadProducts() {
        // Disabled to prevent interference with manual database management
    }
}

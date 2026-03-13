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
        log.info("ℹ️ SQL script should be used for initial data seeding.");
    }

    private void loadUsers() {
        if (userRepository.count() == 0) {
            User demoUser = User.builder()
                    .email("test@test.com")
                    .password(passwordEncoder.encode("password"))
                    .firstName("John")
                    .lastName("Doe")
                    .role(User.Role.USER)
                    .build();
            userRepository.save(demoUser);

            User adminUser = User.builder()
                    .email("admin@test.com")
                    .password(passwordEncoder.encode("admin123"))
                    .firstName("Admin")
                    .lastName("User")
                    .role(User.Role.ADMIN)
                    .build();
            userRepository.save(adminUser);

            log.info("👤 Demo users created (test@test.com / password)");
        }
    }

    private void loadProducts() {
        if (productRepository.count() == 0) {
            List<Product> products = Arrays.asList(
                Product.builder()
                    .title("Apple MacBook Pro M3 Max 16-inch")
                    .price(3499.00)
                    .originalPrice(3999.00)
                    .discountPercentage(12.5)
                    .description("The most advanced Mac for pros. Features the groundbreaking M3 Max chip with 16-core CPU, 40-core GPU, and up to 128GB unified memory.")
                    .brand("Apple")
                    .category("electronics")
                    .image("https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&q=80&w=1000")
                    .images(Arrays.asList(
                        "https://images.unsplash.com/photo-1517336714731-489689fd1ca8?auto=format&fit=crop&q=80&w=1000",
                        "https://images.unsplash.com/photo-1611186871348-b1ce696e52c9?auto=format&fit=crop&q=80&w=1000"
                    ))
                    .features(Arrays.asList("M3 Max Chip", "Liquid Retina XDR Display", "Up to 22 hours battery life", "1080p FaceTime HD camera"))
                    .stock(12)
                    .ratingRate(4.9)
                    .ratingCount(120)
                    .tags(Arrays.asList("laptop", "pro", "apple"))
                    .build(),

                Product.builder()
                    .title("Sony Alpha a7 IV Full-Frame Mirrorless")
                    .price(2498.00)
                    .description("Next-generation full-frame mirrorless interchangeable lens camera with 33MP sensor and 4K 60p video capabilities.")
                    .brand("Sony")
                    .category("photography")
                    .image("https://images.unsplash.com/photo-1516035069371-29a1b244cc32?auto=format&fit=crop&q=80&w=1000")
                    .images(Arrays.asList(
                        "https://images.unsplash.com/photo-1516035069371-29a1b244cc32?auto=format&fit=crop&q=80&w=1000"
                    ))
                    .features(Arrays.asList("33MP Full-Frame Exmor R CMOS Sensor", "Up to 10 fps Shooting", "Real-time Eye AF for Humans/Animals", "4K 60p Video Recording"))
                    .stock(8)
                    .ratingRate(4.8)
                    .ratingCount(245)
                    .tags(Arrays.asList("camera", "mirrorless", "sony"))
                    .build(),

                Product.builder()
                    .title("Sony WH-1000XM5 Wireless Headphones")
                    .price(348.00)
                    .originalPrice(399.00)
                    .discountPercentage(12.7)
                    .description("Industry-leading noise canceling headphones with Auto Noise Canceling Optimizer, crystal clear hands-free calling, and Alexa voice control.")
                    .brand("Sony")
                    .category("audio")
                    .image("https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?auto=format&fit=crop&q=80&w=1000")
                    .images(Arrays.asList(
                        "https://images.unsplash.com/photo-1618366712010-f4ae9c647dcb?auto=format&fit=crop&q=80&w=1000"
                    ))
                    .features(Arrays.asList("Industry Leading Noise Cancellation", "30-Hour Battery Life", "Multipoint Connection", "Touch Sensor Controls"))
                    .stock(45)
                    .ratingRate(4.7)
                    .ratingCount(1890)
                    .tags(Arrays.asList("headphones", "wireless", "audio", "noise-canceling"))
                    .build(),

                Product.builder()
                    .title("Samsung 49-Inch Odyssey G9 Gaming Monitor")
                    .price(1199.99)
                    .originalPrice(1499.99)
                    .discountPercentage(20.0)
                    .description("Unmatched gaming performance with 240Hz refresh rate, 1ms response time, and extremely curved 1000R panel for ultimate immersion.")
                    .brand("Samsung")
                    .category("electronics")
                    .image("https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?auto=format&fit=crop&q=80&w=1000")
                    .images(Arrays.asList(
                        "https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?auto=format&fit=crop&q=80&w=1000"
                    ))
                    .features(Arrays.asList("49-inch Super Ultrawide", "1000R Curvature", "240Hz Refresh Rate", "QLED Technology"))
                    .stock(5)
                    .ratingRate(4.6)
                    .ratingCount(532)
                    .tags(Arrays.asList("monitor", "gaming", "ultrawide", "samsung"))
                    .build(),

                Product.builder()
                    .title("Minimalist Leather Oxford Shoes")
                    .price(185.00)
                    .description("Crafted from premium full-grain Italian leather. Features a timeless minimalist design with a durable rubber sole for all-day comfort.")
                    .brand("Everlane")
                    .category("men clothing")
                    .image("https://images.unsplash.com/photo-1614252339474-ce3a480a4f3e?auto=format&fit=crop&q=80&w=1000")
                    .images(Arrays.asList(
                        "https://images.unsplash.com/photo-1614252339474-ce3a480a4f3e?auto=format&fit=crop&q=80&w=1000"
                    ))
                    .features(Arrays.asList("100% Full-grain leather", "Hand-stitched detailing", "Breathable leather lining", "Anti-slip sole"))
                    .stock(24)
                    .ratingRate(4.5)
                    .ratingCount(89)
                    .tags(Arrays.asList("shoes", "leather", "fashion", "men"))
                    .build(),

                Product.builder()
                    .title("Nike Air Zoom Pegasus 40")
                    .price(130.00)
                    .description("A springy ride for every run. The Peg 40 brings back the tailored fit and feel you love, right down to the responsive React foam.")
                    .brand("Nike")
                    .category("men clothing")
                    .image("https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&q=80&w=1000")
                    .images(Arrays.asList(
                        "https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&q=80&w=1000"
                    ))
                    .features(Arrays.asList("Nike React foam", "Zoom Air units", "Engineered mesh upper", "Waffle-inspired rubber outsole"))
                    .stock(120)
                    .ratingRate(4.8)
                    .ratingCount(420)
                    .tags(Arrays.asList("shoes", "running", "sport", "nike"))
                    .build(),

                Product.builder()
                    .title("Keychron Q1 Pro Mechanical Keyboard")
                    .price(199.00)
                    .description("A fully customizable 75% layout wireless custom mechanical keyboard with QMK/VIA support, designed with a premium aluminum CNC machined body.")
                    .brand("Keychron")
                    .category("electronics")
                    .image("https://images.unsplash.com/photo-1595225476474-87563907a212?auto=format&fit=crop&q=80&w=1000")
                    .images(Arrays.asList(
                        "https://images.unsplash.com/photo-1595225476474-87563907a212?auto=format&fit=crop&q=80&w=1000"
                    ))
                    .features(Arrays.asList("Wireless / Wired", "Hot-Swappable", "QMK/VIA support", "CNC Aluminum Body", "Double-gasket design"))
                    .stock(30)
                    .ratingRate(4.9)
                    .ratingCount(215)
                    .tags(Arrays.asList("keyboard", "mechanical", "wireless", "accessories"))
                    .build(),

                Product.builder()
                    .title("Bose SoundLink Revolve+ II")
                    .price(329.00)
                    .description("Deep, loud, and immersive sound, with True 360° coverage. Built-in handle, water-resistant, up to 17 hours of battery life.")
                    .brand("Bose")
                    .category("audio")
                    .image("https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?auto=format&fit=crop&q=80&w=1000")
                    .images(Arrays.asList(
                        "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?auto=format&fit=crop&q=80&w=1000"
                    ))
                    .features(Arrays.asList("True 360° Sound", "Water and dust-resistant", "Up to 17 hours per charge", "Built-in microphone"))
                    .stock(50)
                    .ratingRate(4.7)
                    .ratingCount(681)
                    .tags(Arrays.asList("speaker", "audio", "bluetooth", "bose"))
                    .build()
            );

            productRepository.saveAll(products);
            log.info("📦 {} products seeded", products.size());
        }
    }
}

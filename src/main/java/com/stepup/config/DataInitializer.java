package com.stepup.config;

import com.stepup.model.Product;
import com.stepup.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(ProductRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                // Kids products
                createProduct(repository, "Whitby Girls White Multi Marble EVA Clog", 2799.0, "https://i.imgur.com/5N8YQ0U.png", "Kids", 4);
                // Womens products (New & Trending from index.html)
                createProduct(repository, "Ophelia Womens Silver Diamante Heel", 2299.0, "/14833_2_B02.20250530000126.jpg", "Womens", 0);
            }
        };
    }

    private void createProduct(ProductRepository repo, String name, Double price, String image, String category, int rating) {
        Product p = new Product();
        p.setName(name);
        p.setPrice(price);
        p.setImage(image);
        p.setCategory(category);
        p.setRating(rating);
        p.setDescription("Description for " + name);
        
        // Add default sizes for seed data
        java.util.Map<String, Integer> sizes = new java.util.HashMap<>();
        sizes.put("UK 7", 25);
        sizes.put("UK 8", 25);
        sizes.put("UK 9", 25);
        sizes.put("UK 10", 25);
        p.setSizes(sizes);
        
        repo.save(p);
    }

    @Bean
    CommandLineRunner initUser(com.stepup.repository.UserRepository userRepository, org.springframework.security.crypto.password.PasswordEncoder passwordEncoder) {
        return args -> {
            if (userRepository.findByEmail("user@stepup.com").isEmpty()) {
                com.stepup.model.User user = new com.stepup.model.User();
                user.setEmail("user@stepup.com");
                user.setMobileNumber("9999988888");
                user.setPassword(passwordEncoder.encode("user"));
                user.setName("Test User");
                user.setRole(com.stepup.model.User.Role.USER);
                userRepository.save(user);
            }
            if (userRepository.findByEmail("admin@stepup.com").isEmpty()) {
                com.stepup.model.User admin = new com.stepup.model.User();
                admin.setEmail("admin@stepup.com");
                admin.setMobileNumber("0000000000");
                admin.setPassword(passwordEncoder.encode("admin"));
                admin.setName("Admin User");
                admin.setRole(com.stepup.model.User.Role.ADMIN);
                userRepository.save(admin);
            }
        };
    };
    @Bean
    CommandLineRunner initExtras(com.stepup.repository.CouponRepository couponRepo, 
                                 com.stepup.repository.OfferRepository offerRepo) {
        return args -> {
            if (couponRepo.count() == 0) {
                com.stepup.model.Coupon c1 = new com.stepup.model.Coupon();
                c1.setCode("STEPUP50");
                c1.setDiscountPercentage(50);
                c1.setExpiryDate(java.time.LocalDate.now().plusMonths(3));
                c1.setActive(true);
                couponRepo.save(c1);
            }
            if (offerRepo.count() == 0) {
                com.stepup.model.Offer o1 = new com.stepup.model.Offer();
                o1.setTitle("Grand Opening Sale!");
                o1.setDescription("Celebrate with us! Use code STEPUP50 for a massive 50% discount on all seasonal items.");
                o1.setImage("https://images.unsplash.com/photo-1542291026-7eec264c27ff");
                o1.setExpiryDate(java.time.LocalDate.now().plusMonths(1));
                o1.setActive(true);
                offerRepo.save(o1);

                com.stepup.model.Offer o2 = new com.stepup.model.Offer();
                o2.setTitle("Buy 2 Get 1 Free");
                o2.setDescription("Exclusive for our premium members. Applicable on all products in the Kids store.");
                o2.setImage("https://images.unsplash.com/photo-1595950653106-6c9ebd614d3a");
                o2.setExpiryDate(java.time.LocalDate.now().plusMonths(2));
                o2.setActive(true);
                offerRepo.save(o2);
            }
        };
    };
}

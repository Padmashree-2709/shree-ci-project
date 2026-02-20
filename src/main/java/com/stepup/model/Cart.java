package com.stepup.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @ManyToOne
    private Coupon appliedCoupon;

    public Double getTotalAmount() {
        double subtotal = items.stream()
                .mapToDouble(CartItem::getSubtotal)
                .sum();
        
        if (appliedCoupon != null && appliedCoupon.isActive()) {
            double discount = subtotal * (appliedCoupon.getDiscountPercentage() / 100.0);
            return subtotal - discount;
        }
        return subtotal;
    }

    public Integer getTotalQuantity() {
        return items.stream()
                .mapToInt(CartItem::getQuantity)
                .sum();
    }
}

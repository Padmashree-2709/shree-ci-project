package com.stepup.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.HashMap;
import java.util.Map;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private Double price; // MRP
    private String image; // URL
    private String category; // e.g., Kids, Womens, Mens
    private String color;
    private Integer rating; // 1-5

    @Column(columnDefinition = "boolean default false")
    private boolean isFeatured;

    @Column(columnDefinition = "boolean default true")
    private boolean inStock;

    @ElementCollection
    @CollectionTable(name = "product_sizes", joinColumns = @JoinColumn(name = "product_id"))
    @MapKeyColumn(name = "size_name")
    @Column(name = "quantity")
    private Map<String, Integer> sizes = new HashMap<>();

    public Integer getTotalStock() {
        return sizes.values().stream().mapToInt(Integer::intValue).sum();
    }
}

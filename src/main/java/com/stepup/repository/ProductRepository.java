package com.stepup.repository;

import com.stepup.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);

    @Query("SELECT p FROM Product p WHERE " +
           "(:keyword IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:category IS NULL OR p.category = :category) AND " +
           "(:minPrice IS NULL OR p.price >= :minPrice) AND " +
           "(:maxPrice IS NULL OR p.price <= :maxPrice)")
    List<Product> searchProducts(@Param("keyword") String keyword, 
                                 @Param("category") String category, 
                                 @Param("minPrice") Double minPrice, 
                                 @Param("maxPrice") Double maxPrice,
                                 org.springframework.data.domain.Sort sort);
    List<Product> findByIsFeaturedTrue();
}

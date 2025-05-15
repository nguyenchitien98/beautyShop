package com.tien.repository;

import com.tien.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByProductId(Long productId);

    @Query("SELECT AVG(r.rating) FROM Rating r WHERE r.product.id = :productId")
    Double findAverageRatingByProductId(Long productId);
}

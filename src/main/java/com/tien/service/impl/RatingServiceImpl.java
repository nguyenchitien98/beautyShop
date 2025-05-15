package com.tien.service.impl;

import com.tien.dto.response.RatingResponse;
import com.tien.entity.Product;
import com.tien.entity.Rating;
import com.tien.entity.User;
import com.tien.map.MapToResponse;
import com.tien.repository.ProductRepository;
import com.tien.repository.RatingRepository;
import com.tien.service.RatingService;
import com.tien.util.AuthUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final RatingRepository ratingRepository;
    private final ProductRepository productRepository;
    private final AuthUtil authUtil;
    private final MapToResponse mapToResponse;

    @Override
    public RatingResponse addRating(Long productId, Integer ratingValue, String comment) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        User currentUser = authUtil.getCurrentUser();

        Rating rating = Rating.builder()
                .product(product)
                .user(currentUser)
                .rating(ratingValue)
                .comment(comment)
//                .createdAt(LocalDateTime.now())
                .build();

        Rating saved = ratingRepository.save(rating);

        return mapToResponse.convertToRatingResponse(saved);
    }

    @Override
    public List<RatingResponse> getRatingsForProduct(Long productId) {
        return ratingRepository.findByProductId(productId).stream()
                .map(mapToResponse::convertToRatingResponse)
                .toList();
    }

    @Override
    public Double getAverageRating(Long productId) {
        // Cách 1
//        List<Rating> ratings = ratingRepository.findByProductId(productId);
//        return ratings.stream()
//                .mapToInt(Rating::getRating)
//                .average()
//                .orElse(0.0);

        // Cách 2
        Double avg = ratingRepository.findAverageRatingByProductId(productId);
        return avg != null ? Math.round(avg * 10.0) / 10.0 : 0.0;
    }


}

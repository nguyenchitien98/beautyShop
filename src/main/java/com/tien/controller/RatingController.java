package com.tien.controller;

import com.tien.dto.response.RatingResponse;
import com.tien.entity.Rating;
import com.tien.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
@RequiredArgsConstructor
public class RatingController {
    private final RatingService ratingService;

    // Thêm đánh giá
    @PostMapping("/product/{productId}")
    public RatingResponse addRating(
            @PathVariable Long productId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String comment
    ) {
        return ratingService.addRating(productId, rating, comment);
    }

    // Lấy danh sách đánh giá
    @GetMapping("/product/{productId}")
    public List<RatingResponse> getRatings(@PathVariable Long productId) {
        return ratingService.getRatingsForProduct(productId);
    }

    // Lấy điểm trung bình đánh giá
    @GetMapping("/product/{productId}/average")
    public Double getAverageRating(@PathVariable Long productId) {
        return ratingService.getAverageRating(productId);
    }
}

package com.tien.map;

import com.tien.dto.response.RatingResponse;
import com.tien.entity.Rating;

import java.util.List;
import java.util.stream.Collectors;

//Tạo DTO rõ ràng và tối ưu cho Product và Rating:
//ProductDTO: không trả về full Category, chỉ tên danh mục hoặc ID.
//RatingDTO: không trả về full User, chỉ tên người dùng hoặc ID.
//Tránh vòng lặp Product → Rating → Product.

public class RatingMapper {
    public static RatingResponse toResponse(Rating r) {
        return RatingResponse.builder()
                .id(r.getId())
                .rating(r.getRating())
                .comment(r.getComment())
                .userFullName(r.getUser().getFullName())
                .avatar(r.getUser().getAvatar())
                .createdAt(r.getCreatedAt())
                .build();
    }

    public static List<RatingResponse> toResponseList(List<Rating> ratings) {
        return ratings.stream()
                .map(RatingMapper::toResponse)
                .collect(Collectors.toList());
    }
}

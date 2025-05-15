package com.tien.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false) //  không muốn tính id từ BaseEntity vào equals/hashCode
public class Product extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private Double price;

    private Double discountPrice; // giá sau giảm

    private Integer stockQuantity; // Kho

    private String imageUrl; // Ảnh

    private String brand; // Thương hiệu

    private String origin; // Xuất xứ

    private String skinType; // Loại da phù hợp (VD: dầu, khô, nhạy cảm...)

    private String weightOrVolume; // Trọng lượng/thể tích: 150ml, 30g,...
    private Boolean isFeatured; // sản phẩm nổi bật

    // Đoạn bên dưới là phần nâng cao, có thể nâng cấp sau
//    @Column(length = 1000)
//    private String usageInstructions; // cách sử dụng, hướng dẫn
//
//    @Column(length = 1000)
//    private String ingredients; // thành phần
//
//    private Boolean isAvailable; // Có sẵn
//
//    private String videoUrl; // link video review (nếu có)

    @ElementCollection
    private List<String> tags = new ArrayList<>(); // từ khóa tìm kiếm

    // Quan hệ đến danh mục sản phẩm
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // Danh sách đánh giá sản phẩm
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference // Dùng để loại bỏ vòng lặp vô hạn (@JsonManagedReference: Phía cha (forward reference) — sẽ được serialize.)
    private List<Rating> ratings = new ArrayList<>();

    // Field không được ánh xạ vào database
    //@Transient: annotation này dùng để đánh dấu field hoặc method không được ánh xạ vào bảng trong cơ sở dữ liệu.
    //getAverageRating(): tính toán trung bình của tất cả Rating trong danh sách ratings.
    @Transient
    public Double getAverageRating() {
        if (ratings == null || ratings.isEmpty()) {
            return 0.0;
        }

        double sum = ratings.stream()
                .mapToInt(Rating::getRating)
                .sum();
        return Math.round((sum / (double) ratings.size()) * 10.0) / 10.0; // làm tròn 1 chữ số thập phân
    }

}

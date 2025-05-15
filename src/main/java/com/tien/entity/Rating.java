package com.tien.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "ratings")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(callSuper = false) //  không muốn tính id từ BaseEntity vào equals/hashCode
public class Rating extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer rating; // từ 1 đến 5

    private String comment;

//    private LocalDateTime createdAt;

    // Nếu có User, liên kết với người dùng đánh giá
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Dùng để oại bỏ vòng lặp vô hạn (@JsonBackReference: Phía con — bị bỏ qua khi serialize.)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    @JsonBackReference // Dùng để oại bỏ vòng lặp vô hạn (@JsonBackReference: Phía con — bị bỏ qua khi serialize.)
    private Product product;
}

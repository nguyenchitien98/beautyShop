package com.tien.service;

import com.tien.dto.request.ProductRequest;
import com.tien.entity.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product createProduct(ProductRequest product);
    Product getProductById(Long id);
    List<Product> getAllProducts();
    List<Product> getProductsByCategoryId(Long categoryId);
    String uploadImage(MultipartFile file);
    void deleteFile(String imageUrl);
    void deleteProduct(Long id);
}

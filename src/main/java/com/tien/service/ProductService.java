package com.tien.service;

import com.tien.dto.request.ProductRequest;
import com.tien.dto.response.ProductResponse;
import com.tien.entity.Product;
import org.springframework.data.domain.Page;
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

    Page<ProductResponse> getAllProductsForPage(int page, int size);
    ProductResponse updateProduct(Long id, ProductRequest dto);
    List<ProductResponse> searchProducts(String keyword);
    List<ProductResponse> getFeaturedProducts();
    List<ProductResponse> getTopRatedProducts();
    List<ProductResponse> getLatestProducts();
    List<ProductResponse> filterProducts(String brand, String skinType, Double minPrice, Double maxPrice, Long categoryId);
}

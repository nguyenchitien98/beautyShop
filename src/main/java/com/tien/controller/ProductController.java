package com.tien.controller;

import com.tien.dto.request.ProductRequest;
import com.tien.dto.response.ProductResponse;
import com.tien.entity.Product;
import com.tien.map.ProductMapper;
import com.tien.payload.ApiResponseBuilder;
import com.tien.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    private final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @PostMapping
    public ResponseEntity<?> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        Product product = productService.createProduct(productRequest);
        ProductResponse productResponse = ProductMapper.toResponse(product);
        return ResponseEntity.ok(ApiResponseBuilder.success(productResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) {
        Product product = productService.getProductById(id);
        ProductResponse productResponse = ProductMapper.toResponse(product);
        return ResponseEntity.ok(ApiResponseBuilder.success(productResponse));
    }

    @GetMapping
    public ResponseEntity<?> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductResponse> productResponses = ProductMapper.toResponseList(products);
        return ResponseEntity.ok(ApiResponseBuilder.success(productResponses));
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable Long categoryId) {
        List<Product> productList = productService.getProductsByCategoryId(categoryId);
        List<ProductResponse> productResponseList = ProductMapper.toResponseList(productList);
        return ResponseEntity.ok(ApiResponseBuilder.success(productResponseList));
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadImage(@RequestParam("image") MultipartFile file) {
        LOGGER.info(file.getOriginalFilename());

        String imageUrl = productService.uploadImage(file);
        return ResponseEntity.ok(Map.of("imageUrl", imageUrl));

    }
}

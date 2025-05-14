package com.tien.service.impl;

import com.tien.dto.request.ProductRequest;
import com.tien.entity.Category;
import com.tien.entity.Product;
import com.tien.repository.CategoryRepository;
import com.tien.repository.ProductRepository;
import com.tien.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    private final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    @Value("${file.upload-dir}")
    private String fileUploadDir;

    @Override
    public Product createProduct(ProductRequest productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .stockQuantity(productRequest.getStockQuantity())
                .imageUrl(productRequest.getImageUrl())
                .category(category)
                .build();
        return productRepository.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategoryId(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public String uploadImage(MultipartFile file) {
        try {
            String uploadDir =  System.getProperty("user.dir") + fileUploadDir;
            File folder = new File(uploadDir);
            if (!folder.exists()) folder.mkdirs();
            String filename =file.getOriginalFilename();
            File dest = new File(uploadDir + filename);
            file.transferTo(dest);
            String baseUrl = ServletUriComponentsBuilder.fromCurrentContextPath().build().toUriString();
            System.out.println(baseUrl);
            LOGGER.info(file.getOriginalFilename());
            return "/uploads/" + filename;
        }catch (IOException e) {
            e.printStackTrace(); // 👈 rất quan trọng
            return "Upload failed: " + e.getMessage();
        }
    }

    @Override
    public void deleteFile(String imageUrl) {
        String uploadDir =  System.getProperty("user.dir") + fileUploadDir;
        String filename = imageUrl.replace("/uploads/", "");
        File file = new File(uploadDir + filename);
        if (file.exists()) file.delete();
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id).orElseThrow();
        if (product.getImageUrl() != null) {
            deleteFile(product.getImageUrl());
        }
        productRepository.delete(product);
    }
}

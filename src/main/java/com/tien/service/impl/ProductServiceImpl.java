package com.tien.service.impl;

import com.tien.dto.request.ProductRequest;
import com.tien.dto.response.ProductResponse;
import com.tien.entity.Category;
import com.tien.entity.Product;
import com.tien.map.ProductMapper;
import com.tien.repository.CategoryRepository;
import com.tien.repository.ProductRepository;
import com.tien.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

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
        Product product = ProductMapper.toEntity(productRequest);
        product.setCategory(category);
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

    // Lấy tất cả sản phẩm có phân trang
    @Override
    public Page<ProductResponse> getAllProductsForPage(int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Product> products = productRepository.findAll(pageable);
        return products.map(ProductMapper::toResponse);
    }

    // Cập nhật sản phẩm
    @Override
    public ProductResponse updateProduct(Long id, ProductRequest dto) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));
        existing.setName(dto.getName());
        existing.setDescription(dto.getDescription());
        existing.setPrice(dto.getPrice());
        existing.setDiscountPrice(dto.getDiscountPrice());
        existing.setStockQuantity(dto.getStockQuantity());
        existing.setImageUrl(dto.getImageUrl());
        existing.setBrand(dto.getBrand());
        existing.setOrigin(dto.getOrigin());
        existing.setSkinType(dto.getSkinType());
        existing.setWeightOrVolume(dto.getWeightOrVolume());
        existing.setIsFeatured(dto.getIsFeatured());
        existing.setTags(dto.getTags());
        if (dto.getCategoryId() != null) {
            Category category = categoryRepository.findById(dto.getCategoryId())
                    .orElseThrow(() -> new EntityNotFoundException("Category not found"));
            existing.setCategory(category);
        }

        return ProductMapper.toResponse(productRepository.save(existing));
    }

    // Tìm kiếm theo từ khoá
    @Override
    public List<ProductResponse> searchProducts(String keyword) {
        return productRepository.search(keyword).stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Sản phẩm nổi bật
    @Override
    public List<ProductResponse> getFeaturedProducts() {
        return productRepository.findByIsFeaturedTrue().stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Sản phẩm có đánh giá cao nhất
    @Override
    public List<ProductResponse> getTopRatedProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        return productRepository.findTopRated(pageable).stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Sản phẩm mới nhất
    @Override
    public List<ProductResponse> getLatestProducts() {
        return productRepository.findAllByOrderByCreatedAtDesc().stream()
                .limit(10)
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }

    // Lọc nâng cao
    @Override
    public List<ProductResponse> filterProducts(String brand, String skinType, Double minPrice, Double maxPrice, Long categoryId) {
        return productRepository.filter(brand, skinType, minPrice, maxPrice, categoryId).stream()
                .map(ProductMapper::toResponse)
                .collect(Collectors.toList());
    }
}

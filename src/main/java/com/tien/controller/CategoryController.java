package com.tien.controller;

import com.tien.dto.request.CategoryRequest;
import com.tien.dto.response.CategoryResponse;
import com.tien.entity.Category;
import com.tien.map.CategoryMapper;
import com.tien.payload.ApiCode;
import com.tien.payload.ApiResponse;
import com.tien.payload.ApiResponseBuilder;
import com.tien.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<?> createCategory(@RequestBody CategoryRequest category) {
        Category newCategory = categoryService.createCategory(category);
        CategoryResponse categoryResponse = CategoryMapper.toResponse(newCategory);
        return ResponseEntity.ok(ApiResponseBuilder.success(ApiCode.REGISTER_SUCCESS_CATEGORY,categoryResponse));
//        return ResponseEntity.ok(
//                ApiResponse.builder()
//                        .success(true)
//                        .message("Created category successfully")
//                        .data(newCategory)
//                        .build());
    }

    @GetMapping
    public ResponseEntity<ApiResponse<?>>  getAllCategories() {
        List<Category> listCategory = categoryService.getAllCategories();
        List<CategoryResponse> categoryResponseList = CategoryMapper.toResponseList(listCategory);
        return ResponseEntity.ok(ApiResponseBuilder.success(ApiCode.GET_ALL_CATEGORIES,categoryResponseList));
    }
}

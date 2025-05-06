package com.tien.exception;

import com.tien.payload.ApiCode;
import com.tien.payload.ApiResponseBuilder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Bắt các Exception thông thường
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseBuilder.error(ApiCode.INTERNAL_SERVER_ERROR));
    }

    // Bắt lỗi không tìm thấy tài nguyên
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponseBuilder.error(ApiCode.NOT_FOUND));
    }

    // Bắt lỗi validation @Valid thất bại
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult().getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("Validation error");
        return ResponseEntity.badRequest()
                .body(ApiResponseBuilder.error(ApiCode.BAD_REQUEST, errorMessage));
    }

    // Bắt lỗi kiểu dữ liệu request sai
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<?> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponseBuilder.error(ApiCode.BAD_REQUEST, "Invalid parameter type"));
    }

    // Bắt lỗi vi phạm constraint (ví dụ @NotNull)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolation(ConstraintViolationException ex) {
        return ResponseEntity.badRequest()
                .body(ApiResponseBuilder.error(ApiCode.BAD_REQUEST, ex.getMessage()));
    }

    // Bắt mọi lỗi còn lại
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseBuilder.error(ApiCode.INTERNAL_SERVER_ERROR));
    }

    // Bắt lỗi BusinessException
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(BusinessException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseBuilder.error(ex.getApiCode(), ex.getMessage()));
    }

//    // 2. Xử lý Resource Not Found
//    @ExceptionHandler(ResourceNotFoundException.class)
//    public ResponseEntity<?> handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest request) {
//        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND, request.getRequestURI());
//    }
//
//    // 3. Xử lý Bad Request
//    @ExceptionHandler(BadRequestException.class)
//    public ResponseEntity<?> handleBadRequest(BadRequestException ex, HttpServletRequest request) {
//        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST, request.getRequestURI());
//    }
//
//    // 4. Xử lý tất cả exception còn lại
//    @ExceptionHandler(Exception.class)
//    public ResponseEntity<?> handleGlobalException(Exception ex, HttpServletRequest request) {
//        return buildErrorResponse("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR, request.getRequestURI());
//    }
//
//    // 1. Xử lý lỗi @Valid (Validation)
//    @ExceptionHandler(MethodArgumentNotValidException.class)
//    public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex, HttpServletRequest request) {
//        Map<String, String> errors = new HashMap<>();
//
//        ex.getBindingResult().getAllErrors().forEach(error -> {
//            String fieldName = ((FieldError) error).getField();
//            String errorMessage = error.getDefaultMessage();
//            errors.put(fieldName, errorMessage);
//        });
//
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", HttpStatus.BAD_REQUEST.value());
//        response.put("error", "Validation Error");
//        response.put("message", errors);
//        response.put("path", request.getRequestURI());
//
//        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//
//
//    // Helper method để build response
//    private ResponseEntity<?> buildErrorResponse(String message, HttpStatus status, String path) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("timestamp", LocalDateTime.now());
//        response.put("status", status.value());
//        response.put("error", status.getReasonPhrase());
//        response.put("message", message);
//        response.put("path", path);
//
//        return new ResponseEntity<>(response, status);
//    }
}

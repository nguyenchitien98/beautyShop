package com.tien.payload;

public class ApiResponseBuilder {
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(ApiCode.SUCCESS.getCode())
                .message(ApiCode.SUCCESS.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> success(ApiCode apiCode, T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .code(apiCode.getCode())
                .message(apiCode.getMessage())
                .data(data)
                .build();
    }

    public static <T> ApiResponse<T> error(ApiCode apiCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(apiCode.getCode())
                .message(apiCode.getMessage())
                .build();
    }

    public static <T> ApiResponse<T> error(ApiCode apiCode, String customMessage) {
        return ApiResponse.<T>builder()
                .success(false)
                .code(apiCode.getCode())
                .message(customMessage)
                .data(null)
                .build();
    }
}

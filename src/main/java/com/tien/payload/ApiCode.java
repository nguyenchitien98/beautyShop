package com.tien.payload;


import lombok.Getter;

@Getter
public enum ApiCode {

    SUCCESS(0, "Success"),
    BAD_REQUEST(100, "Bad request"),
    UNAUTHORIZED(101, "Unauthorized"),
    FORBIDDEN(102, "Forbidden"),
    NOT_FOUND(103, "Resource not found"),
    INTERNAL_SERVER_ERROR(104, "Internal server error"),
    INVALID_TOKEN(105, "Invalid token"),
    EMAIL_ALREADY_EXISTS(106, "Email already exists"),
    USER_NOT_FOUND(107, "User not found"),
    ORDER_NOT_FOUND(108, "Order not found"),
    PRODUCT_NOT_FOUND(109, "Product not found"),
    OUT_OF_STOCK(110, "Out of stock"),
    PAYMENT_FAILED(111, "Payment failed"),
    CHANGE_PASSWORD_FAILED(112, "Change password failed"),
    CHANGE_PASSWORD_SUCCESS(113, "Change password success"),
    DELETE_USER_SUCCESS(114, "Delete user success"),
    REGISTER_SUCCESS(115, "User registered successfully"),
    REGISTER_SUCCESS_CATEGORY(116, "Created category successfully"),
    GET_ALL_CATEGORIES(117, "Get All Categories"),
    LOGIN_SUCCESS(118, "Login successful"),
    GET_CURRENT_USER(118, "Get current user profile successfully"),
    ;

    private final int code;
    private final String message;

    ApiCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

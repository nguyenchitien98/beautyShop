package com.tien.exception;

import com.tien.payload.ApiCode;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {

    private final ApiCode apiCode;

    public BusinessException(ApiCode apiCode) {
        super(apiCode.getMessage());
        this.apiCode = apiCode;
    }

    public BusinessException(ApiCode apiCode, String customMessage) {
        super(customMessage);
        this.apiCode = apiCode;
    }
}

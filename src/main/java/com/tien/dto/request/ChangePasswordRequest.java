package com.tien.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @NotBlank(message = "Old password must not be blank")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String oldPassword;

    @Size(min = 6, message = "Password must be at least 6 characters")
    @NotBlank(message = "New password must not be blank")
    private String newPassword;
}
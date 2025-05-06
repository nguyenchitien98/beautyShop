package com.tien.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class CreateUserRequest {
    @NotBlank(message = "Name must not be blank")
    private String username;

    @NotBlank(message = "Password must not be blank")
    private String password;

    @NotBlank(message = "FullName must not be blank")
    private String fullName;

    private String avatar;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email format is invalid")
    private String email;
    private String phoneNumber;
    private String address;
}

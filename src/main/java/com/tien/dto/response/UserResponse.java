package com.tien.dto.response;

import com.tien.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String username;
    private String fullName;
    private String avatar;
    private String email;
    private String phoneNumber;
    private String address;
    private Role role;
}

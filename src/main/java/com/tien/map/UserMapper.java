package com.tien.map;

import com.tien.dto.response.UserResponse;
import com.tien.entity.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {
    public static UserResponse toResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .address(user.getAddress())
                .role(user.getRole())
                .build();
    }

    public static List<UserResponse> toResponseList(List<User> users) {
        return users.stream()
                .map(UserMapper::toResponse)
                .collect(Collectors.toList());
    }
}

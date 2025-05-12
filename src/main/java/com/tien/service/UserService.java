package com.tien.service;

import com.tien.dto.request.ChangePasswordRequest;
import com.tien.dto.request.CreateUserRequest;
import com.tien.dto.request.UpdateProfileRequest;
import com.tien.dto.response.UserProfileResponse;
import com.tien.entity.User;

import java.util.List;

public interface UserService {
    User createUser(CreateUserRequest user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
    UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request);
    void changePassword(Long userId, ChangePasswordRequest request);
    UserProfileResponse getUserProfile(Long userId);
}

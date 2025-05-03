package com.tien.service;

import com.tien.dto.request.ChangePasswordRequest;
import com.tien.dto.request.UpdateProfileRequest;
import com.tien.dto.response.UserResponse;
import com.tien.entity.User;

import java.util.List;

public interface UserService {
    User createUser(User user);
    User getUserById(Long id);
    List<User> getAllUsers();
    void deleteUser(Long id);
    UserResponse updateProfile(Long userId, UpdateProfileRequest request);
    void changePassword(Long userId, ChangePasswordRequest request);
}

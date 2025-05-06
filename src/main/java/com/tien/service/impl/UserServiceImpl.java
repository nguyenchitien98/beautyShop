package com.tien.service.impl;

import com.tien.dto.request.ChangePasswordRequest;
import com.tien.dto.request.CreateUserRequest;
import com.tien.dto.request.UpdateProfileRequest;
import com.tien.dto.response.UserProfileResponse;
import com.tien.entity.Role;
import com.tien.entity.User;
import com.tien.exception.BusinessException;
import com.tien.exception.ResourceNotFoundException;
import com.tien.payload.ApiCode;
import com.tien.repository.UserRepository;
import com.tien.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(CreateUserRequest request) {
        User user = User.builder()
                .username(request.getUsername())
                .fullName(request.getFullName())
                .email(request.getEmail())
                .phoneNumber(request.getPhoneNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .address(request.getAddress())
                .role(Role.USER)
                .build();

      return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ApiCode.USER_NOT_FOUND));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserProfileResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setAvatar(request.getAvatar());

        userRepository.save(user);

        return UserProfileResponse.builder()
                .id(user.getId())
                .name(user.getFullName())
                .email(user.getEmail())
                .address(user.getAddress())
                .avatar(user.getAvatar())
                .build();
    }

    @Override
    public void changePassword(Long userId, ChangePasswordRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(ApiCode.USER_NOT_FOUND));

        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu cũ không chính xác");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}

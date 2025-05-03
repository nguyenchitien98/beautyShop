package com.tien.service.impl;

import com.tien.dto.request.ChangePasswordRequest;
import com.tien.dto.request.UpdateProfileRequest;
import com.tien.dto.response.UserResponse;
import com.tien.entity.Role;
import com.tien.entity.User;
import com.tien.repository.UserRepository;
import com.tien.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
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
    public UserResponse updateProfile(Long userId, UpdateProfileRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getName());
        user.setEmail(request.getEmail());
        user.setAddress(request.getAddress());
        user.setAvatar(request.getAvatar());

        userRepository.save(user);

        return UserResponse.builder()
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
                .orElseThrow(() -> new RuntimeException("User not found"));

//        if (!passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
//            throw new RuntimeException("Mật khẩu cũ không chính xác");
//        }
//
//        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userRepository.save(user);
    }
}

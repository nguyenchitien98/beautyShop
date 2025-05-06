package com.tien.controller;

import com.tien.dto.request.ChangePasswordRequest;
import com.tien.dto.request.CreateUserRequest;
import com.tien.dto.request.UpdateProfileRequest;
import com.tien.dto.response.UserProfileResponse;
import com.tien.payload.ApiCode;
import com.tien.payload.ApiResponseBuilder;
import com.tien.security.model.CustomUserDetails;
import com.tien.entity.User;
import com.tien.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest userRequest) {
        User user = userService.createUser(userRequest);
        return ResponseEntity.ok(ApiResponseBuilder.success(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User user = userService.getUserById(id);

        // Đoạn này không cần thiết, làm ở phía layer service rồi,Để lại để tham khảo thôi
//        if (user == null) {
//            return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                    .body(ApiResponseBuilder.error(ApiCode.USER_NOT_FOUND));
//        }
        return ResponseEntity.ok(ApiResponseBuilder.success(user));
    }

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        List<User> userList = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponseBuilder.success(userList));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
         userService.deleteUser(id);
         return ResponseEntity.ok(ApiResponseBuilder.success(ApiCode.DELETE_USER_SUCCESS));
//         return ResponseEntity.noContent().build();
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@Valid @RequestBody UpdateProfileRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        UserProfileResponse updatedUser = userService.updateProfile(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponseBuilder.success(updatedUser));
    }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest request, @AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.changePassword(userDetails.getId(), request);
        return ResponseEntity.ok(ApiResponseBuilder.success(ApiCode.CHANGE_PASSWORD_SUCCESS));
//        return ResponseEntity.ok("Đổi mật khẩu thành công!");
    }
}

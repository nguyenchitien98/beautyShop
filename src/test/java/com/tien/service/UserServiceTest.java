package com.tien.service;

import com.tien.dto.request.CreateUserRequest;
import com.tien.entity.User;
import com.tien.exception.BusinessException;
import com.tien.repository.UserRepository;
import com.tien.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
//import static org.mockito.Mockito.when;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*; // import đầy đủ các static methods

import java.util.Optional;

public class UserServiceTest {

//    @Mock ➔ giả lập UserRepository
//    @InjectMocks ➔ inject các mock vào UserService
//    MockitoAnnotations.openMocks(this) ➔ mở mock tự động trước mỗi test
//    when(...).thenReturn(...) ➔ giả lập dữ liệu trả về từ repository
//    assertThrows ➔ kiểm tra exception có được ném ra hay không
//    verify ➔ kiểm tra có đúng số lần method được gọi

    @InjectMocks
    private UserServiceImpl userService; // Class cần test

    @Mock
    private UserRepository userRepository; // Phụ thuộc cần mock

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Khởi tạo @Mock, @InjectMocks
    }

    @Test
    void testFindUserById_Success() {
        // Arrange (Chuẩn bị dữ liệu)
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setFullName("Tien");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act (Gọi hàm cần test)
        User result = userService.getUserById(userId);

        // Assert (Kiểm tra kết quả)
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(userId);
        assertThat(result.getFullName()).isEqualTo("Tien");

        verify(userRepository, times(1)).findById(userId); // Verify hành vi
    }

    @Test
    void testFindUserById_NotFound() {
        // Arrange
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act + Assert
        assertThrows(BusinessException.class, () -> userService.getUserById(userId));

        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void testCreateUser_Success() {
        // Arrange
        User user = new User();
        user.setFullName("New User");
        user.setEmail("newuser@example.com");
        user.setPassword("plainPassword");

        when(userRepository.save(any(User.class))).thenReturn(user);

        CreateUserRequest request = new CreateUserRequest();
        request.setFullName(user.getFullName());
        request.setEmail(user.getEmail());
        request.setPassword(user.getPassword());
        // Act
        User createdUser = userService.createUser(request);

        // Assert
        assertThat(createdUser).isNotNull();
        assertThat(createdUser.getFullName()).isEqualTo("New User");

        verify(userRepository, times(1)).save(any(User.class));
    }
}

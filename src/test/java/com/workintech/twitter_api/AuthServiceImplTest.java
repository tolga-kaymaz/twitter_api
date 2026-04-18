package com.workintech.twitter_api;



import com.workintech.twitter_api.dto.request.LoginRequestDto;
import com.workintech.twitter_api.dto.request.RegisterRequestDto;
import com.workintech.twitter_api.dto.response.AuthResponseDto;
import com.workintech.twitter_api.entity.Role;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.exceptions.TwitterApiException;
import com.workintech.twitter_api.repository.RoleRepository;
import com.workintech.twitter_api.repository.UserRepository;
import com.workintech.twitter_api.service.AuthServiceImpl;
import com.workintech.twitter_api.util.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private AuthServiceImpl authService;

    private User user;
    private Role role;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        user.setPassword("encodedPassword");

        role = new Role();
        role.setId(1L);
        role.setAuthority("ROLE_USER");
    }

    @Test
    void register_ShouldRegisterUser_WhenValidData() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(false);
        when(roleRepository.getByAuthority("ROLE_USER")).thenReturn(role);
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.toResponseDto(any(User.class))).thenReturn(null);

        RegisterRequestDto dto = new RegisterRequestDto("testuser", "test@test.com", "123456");
        AuthResponseDto result = authService.register(dto);

        assertNotNull(result);
        assertEquals("Kayıt başarılı.", result.message());
        verify(userRepository).save(any(User.class));
    }

    @Test
    void register_ShouldThrowException_WhenUsernameExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        RegisterRequestDto dto = new RegisterRequestDto("testuser", "test@test.com", "123456");

        assertThrows(TwitterApiException.class, () -> authService.register(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_ShouldThrowException_WhenEmailExists() {
        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@test.com")).thenReturn(true);

        RegisterRequestDto dto = new RegisterRequestDto("testuser", "test@test.com", "123456");

        assertThrows(TwitterApiException.class, () -> authService.register(dto));
        verify(userRepository, never()).save(any());
    }

    @Test
    void login_ShouldReturnAuth_WhenValidCredentials() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "encodedPassword")).thenReturn(true);
        when(userRepository.findById(1L)).thenReturn(Optional.of(user)); // bunu ekle
        when(userMapper.toResponseDto(user)).thenReturn(null);

        LoginRequestDto dto = new LoginRequestDto("test@test.com", "123456");
        AuthResponseDto result = authService.login(dto);

        assertNotNull(result);
        assertEquals("Giriş başarılı.", result.message());
    }


    @Test
    void login_ShouldThrowException_WhenEmailNotFound() {
        when(userRepository.findByEmail("wrong@test.com")).thenReturn(Optional.empty());

        LoginRequestDto dto = new LoginRequestDto("wrong@test.com", "123456");

        assertThrows(TwitterApiException.class, () -> authService.login(dto));
    }

    @Test
    void login_ShouldThrowException_WhenPasswordWrong() {
        when(userRepository.findByEmail("test@test.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("wrongpassword", "encodedPassword")).thenReturn(false);

        LoginRequestDto dto = new LoginRequestDto("test@test.com", "wrongpassword");

        assertThrows(TwitterApiException.class, () -> authService.login(dto));
    }
}

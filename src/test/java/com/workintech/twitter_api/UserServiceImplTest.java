package com.workintech.twitter_api;



import com.workintech.twitter_api.dto.request.UserPatchRequestDto;
import com.workintech.twitter_api.dto.request.UserRequestDto;
import com.workintech.twitter_api.dto.response.UserResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.exceptions.TwitterApiException;
import com.workintech.twitter_api.repository.UserRepository;
import com.workintech.twitter_api.service.UserServiceImpl;
import com.workintech.twitter_api.util.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserResponseDto userResponseDto;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("test@test.com");
        user.setPassword("123456");

        userResponseDto = new UserResponseDto(1L, "testuser", "test@test.com", null, null);
    }

    @Test
    void getAll_ShouldReturnAllUsers() {
        when(userRepository.findAll()).thenReturn(List.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(userResponseDto);

        List<UserResponseDto> result = userService.getAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(userRepository).findAll();
    }

    @Test
    void findById_ShouldReturnUser_WhenUserExists() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toResponseDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userService.findById(1L);

        assertNotNull(result);
        assertEquals("testuser", result.username());
    }

    @Test
    void findById_ShouldThrowException_WhenUserNotFound() {
        when(userRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(TwitterApiException.class, () -> userService.findById(99L));
    }

    @Test
    void delete_ShouldDeleteUser_WhenUserIsOwner() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.delete(1L, user);

        verify(userRepository).delete(user);
    }

    @Test
    void delete_ShouldThrowException_WhenUserIsNotOwner() {
        User anotherUser = new User();
        anotherUser.setId(2L);

        assertThrows(TwitterApiException.class, () -> userService.delete(1L, anotherUser));
    }

    @Test
    void update_ShouldThrowException_WhenUserIsNotOwner() {
        User anotherUser = new User();
        anotherUser.setId(2L);

        UserPatchRequestDto dto = new UserPatchRequestDto("newuser", null, null);

        assertThrows(TwitterApiException.class, () -> userService.update(1L, dto, anotherUser));
    }

    @Test
    void replace_ShouldUpdateAllFields_WhenUserIsOwner() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.existsByUsername("newuser")).thenReturn(false);
        when(userRepository.existsByEmail("new@test.com")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toResponseDto(user)).thenReturn(userResponseDto);

        UserRequestDto dto = new UserRequestDto("newuser", "new@test.com", "bio");

        UserResponseDto result = userService.replace(1L, dto, user);

        assertNotNull(result);
        verify(userRepository).save(user);
    }
}

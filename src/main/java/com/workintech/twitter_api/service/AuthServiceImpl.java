package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.LoginRequestDto;
import com.workintech.twitter_api.dto.request.RegisterRequestDto;
import com.workintech.twitter_api.dto.response.AuthResponseDto;
import com.workintech.twitter_api.entity.Role;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.exceptions.TwitterApiException;
import com.workintech.twitter_api.repository.RoleRepository;
import com.workintech.twitter_api.repository.UserRepository;
import com.workintech.twitter_api.util.UserMapper;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;
    @Autowired
    private final PasswordEncoder passwordEncoder;
    @Autowired
    private final UserMapper userMapper;

    @Override
    public AuthResponseDto register(RegisterRequestDto registerRequestDto) {

        if (userRepository.existsByUsername(registerRequestDto.username())) {
            throw new TwitterApiException(
                    "Bu kullanıcı adı zaten kullanılıyor: " + registerRequestDto.username(),
                    HttpStatus.CONFLICT);
        }

        if (userRepository.existsByEmail(registerRequestDto.email())) {
            throw new TwitterApiException(
                    "Bu email zaten kayıtlı: " + registerRequestDto.email(),
                    HttpStatus.CONFLICT);
        }

        User user = new User();
        user.setUsername(registerRequestDto.username());
        user.setEmail(registerRequestDto.email());
        user.setPassword(passwordEncoder.encode(registerRequestDto.password()));

        Role role = roleRepository.getByAuthority("ROLE_USER");
        user.getRoles().add(role);

        userRepository.save(user);

        return new AuthResponseDto("Kayıt başarılı.", userMapper.toResponseDto(user));
    }

    @Override
    public AuthResponseDto login(LoginRequestDto loginRequestDto) {
        User user = userRepository.findByEmail(loginRequestDto.email())
                .orElseThrow(() -> new TwitterApiException(
                        "Email veya şifre hatalı.",
                        HttpStatus.UNAUTHORIZED));

        if (!passwordEncoder.matches(loginRequestDto.password(), user.getPassword())) {
            throw new TwitterApiException(
                    "Email veya şifre hatalı.",
                    HttpStatus.UNAUTHORIZED);
        }


        User freshUser = userRepository.findById(user.getId())
                .orElseThrow(() -> new TwitterApiException(
                        "Kullanıcı bulunamadı.",
                        HttpStatus.NOT_FOUND));
        System.out.println("createdAt: " + user.getCreatedAt());

        return new AuthResponseDto("Giriş başarılı.", userMapper.toResponseDto(freshUser));
    }
}
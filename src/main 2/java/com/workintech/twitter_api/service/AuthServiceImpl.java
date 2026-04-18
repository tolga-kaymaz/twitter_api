package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.LoginRequestDto;
import com.workintech.twitter_api.dto.request.RegisterRequestDto;
import com.workintech.twitter_api.dto.response.AuthResponseDto;
import com.workintech.twitter_api.entity.Role;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


public class AuthServiceImpl implements AuthService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
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

        return new AuthResponseDto("Giriş başarılı.", userMapper.toResponseDto(user));
    }
}

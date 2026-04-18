package com.workintech.twitter_api.service;

import com.workintech.twitter_api.dto.request.UserPatchRequestDto;
import com.workintech.twitter_api.dto.request.UserRequestDto;
import com.workintech.twitter_api.dto.response.UserResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.exceptions.TwitterApiException;
import com.workintech.twitter_api.exceptions.UserNotFoundException;
import com.workintech.twitter_api.repository.UserRepository;
import com.workintech.twitter_api.util.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final UserMapper userMapper;

    @Override
    public List<UserResponseDto> getAll() {
        return userRepository
                .findAll()
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public List<UserResponseDto> searchByUsername(String username) {
        return userRepository.findByUsernameContainingIgnoreCase(username)
                .stream()
                .map(userMapper::toResponseDto)
                .toList();
    }

    @Override
    public UserResponseDto findById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            return userMapper.toResponseDto(user);
        }
        throw new UserNotFoundException("User bulunamadi, id : " + id);
    }

    @Override
    public UserResponseDto replace(Long id, UserRequestDto userRequestDto, User user) {
        if (!id.equals(user.getId())) {
            throw new TwitterApiException(
                    "Sadece kendi profilini güncelleyebilirsin.",
                    HttpStatus.FORBIDDEN);
        }

        User foundUser = userRepository.findById(id)
                .orElseThrow(() -> new TwitterApiException(
                        "Kullanıcı bulunamadı: " + id,
                        HttpStatus.NOT_FOUND));

        if (userRepository.existsByUsername(userRequestDto.username())
                && !userRequestDto.username().equals(foundUser.getUsername())) {
            throw new TwitterApiException(
                    "Bu kullanıcı adı zaten kullanılıyor: " + userRequestDto.username(),
                    HttpStatus.CONFLICT);
        }

        if (userRepository.existsByEmail(userRequestDto.email())
                && !userRequestDto.email().equals(foundUser.getEmail())) {
            throw new TwitterApiException(
                    "Bu email zaten kayıtlı: " + userRequestDto.email(),
                    HttpStatus.CONFLICT);
        }

        foundUser.setUsername(userRequestDto.username());
        foundUser.setEmail(userRequestDto.email());
        foundUser.setBio(userRequestDto.bio());

        return userMapper.toResponseDto(userRepository.save(foundUser));
    }



        @Override
        public UserResponseDto update (Long id, UserPatchRequestDto userPatchRequestDto, User user){

            if (!id.equals(user.getId())) {
                throw new TwitterApiException(
                        "Sadece kendi profilini güncelleyebilirsin.",
                        HttpStatus.FORBIDDEN);
            }

            User foundUser = userRepository.findById(id)
                    .orElseThrow(() -> new TwitterApiException(
                            "Kullanıcı bulunamadı: " + id,
                            HttpStatus.NOT_FOUND));

            if (userPatchRequestDto.username() != null && !userPatchRequestDto.username().isBlank()) {
                if (userRepository.existsByUsername(userPatchRequestDto.username())
                        && !userPatchRequestDto.username().equals(user.getUsername())) {
                    throw new TwitterApiException(
                            "Bu kullanıcı adı zaten kullanılıyor: " + userPatchRequestDto.username(),
                            HttpStatus.CONFLICT);
                }
                foundUser.setUsername(userPatchRequestDto.username());
            }

            if (userPatchRequestDto.email() != null && !userPatchRequestDto.email().isBlank()) {
                if (userRepository.existsByEmail(userPatchRequestDto.email())
                        && !userPatchRequestDto.email().equals(user.getEmail())) {
                    throw new TwitterApiException(
                            "Bu email zaten kayıtlı: " + userPatchRequestDto.email(),
                            HttpStatus.CONFLICT);
                }
                foundUser.setEmail(userPatchRequestDto.email());
            }

            if (userPatchRequestDto.bio() != null){
                foundUser.setBio(userPatchRequestDto.bio());
            }

            return userMapper.toResponseDto(userRepository.save(foundUser));
        }


        @Override
        public void delete(Long id, User user) {
            if (!id.equals(user.getId())) {
                throw new TwitterApiException(
                        "Sadece kendi hesabını silebilirsin.",
                        HttpStatus.FORBIDDEN);
            }

            User foundUser = userRepository.findById(id)
                    .orElseThrow(() -> new TwitterApiException(
                            "Kullanıcı bulunamadı: " + id,
                            HttpStatus.NOT_FOUND));

            userRepository.delete(foundUser);

        }

}

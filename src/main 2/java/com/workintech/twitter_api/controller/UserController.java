package com.workintech.twitter_api.controller;

import com.workintech.twitter_api.dto.request.UserPatchRequestDto;
import com.workintech.twitter_api.dto.request.UserRequestDto;
import com.workintech.twitter_api.dto.response.UserResponseDto;
import com.workintech.twitter_api.entity.User;
import com.workintech.twitter_api.service.UserService;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("user")
public class UserController {

    private UserService userService;


    @GetMapping
    public List<UserResponseDto> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public UserResponseDto findById(@Positive @PathVariable("id") Long id){
        return userService.findById(id);
    }


    @PutMapping("/{id}")
    public UserResponseDto replace(@Positive @PathVariable Long id,
                                   @Validated @RequestBody UserRequestDto userRequestDto, @AuthenticationPrincipal User user) {
        return userService.replace(id, userRequestDto, user);
    }


    @PatchMapping("/{id}")
    public UserResponseDto update(@Positive@PathVariable Long id,
                                  @RequestBody UserPatchRequestDto userPatchRequestDto, @AuthenticationPrincipal User user) {
        return userService.update(id, userPatchRequestDto, user);
    }


    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@Positive@PathVariable Long id,
                                  @AuthenticationPrincipal User user) {
         userService.delete(id, user);
    }
}

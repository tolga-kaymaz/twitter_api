package com.workintech.twitter_api.util;

import com.workintech.twitter_api.dto.response.UserResponseDto;
import com.workintech.twitter_api.entity.User;
import org.springframework.stereotype.Component;

@Component

public class UserMapper {

    public UserResponseDto toResponseDto(User user){

        return new UserResponseDto(

                user.getUsername(),
                user.getEmail(),
                user.getBio(),
                user.getCreatedAt()
        );

    }
}

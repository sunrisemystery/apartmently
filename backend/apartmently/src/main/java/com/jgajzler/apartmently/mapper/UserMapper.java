package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.UserDto;
import com.jgajzler.apartmently.entity.User;
import com.jgajzler.apartmently.entity.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserDto toDto(User user, UserDetails userDetails) {

        return new UserDto(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                userDetails.getName(),
                userDetails.getSurname(),
                userDetails.getPhoneNumber(),
                userDetails.getImageUrl()
        );
    }
}

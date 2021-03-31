package com.jgajzler.apartmently.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserDto {

    private final Long userId;
    private final String email;
    private final String username;
    private final String name;
    private final String surname;
    private final String phoneNumber;
    private final String imageUrl;
}

package com.jgajzler.apartmently.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long userId;
    private String email;
    private String username;
    private String name;
    private String surname;
    private String phoneNumber;
    private String imageUrl;

    public UserDto(Long userId, String email,String username, String name, String surname,
                   String phoneNumber, String imageUrl) {
        this.userId = userId;
        this.email = email;
        this.username = username;
        this.name = name;
        this.surname = surname;
        this.phoneNumber = phoneNumber;
        this.imageUrl = imageUrl;
    }


}

package com.jgajzler.apartmently.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LoginDto {
    private final String username;
    private final String password;
}

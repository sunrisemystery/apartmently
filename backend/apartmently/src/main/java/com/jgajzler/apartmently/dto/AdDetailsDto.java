package com.jgajzler.apartmently.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AdDetailsDto {
    private final Long id;
    private final int floor;
    private final Long userId;
    private final String userName;
    private final String userPhone;
    private final String userEmail;
    private final String userImage;
    private final String description;
    private final double longitude;
    private final double latitude;

}

package com.jgajzler.apartmently.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AddressDto {

    private final String streetName;
    private final int streetNumber;
    private final String postalCode;
    private final double latitude;
    private final double longitude;
}

package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.AddressDto;
import com.jgajzler.apartmently.entity.Address;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper {
    public AddressDto toDto(Address address) {

        return new AddressDto(
                address.getStreetName(),
                address.getStreetNumber(),
                address.getPostalCode(),
                address.getLatitude(),
                address.getLongitude()
        );
    }
}

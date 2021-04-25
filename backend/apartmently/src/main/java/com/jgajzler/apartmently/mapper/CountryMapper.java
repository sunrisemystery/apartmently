package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.CountryDto;
import com.jgajzler.apartmently.entity.Country;
import org.springframework.stereotype.Component;

@Component
public class CountryMapper {
    public CountryDto toDto(Country country) {

        return new CountryDto(
                country.getId(),
                country.getName()
        );
    }
}

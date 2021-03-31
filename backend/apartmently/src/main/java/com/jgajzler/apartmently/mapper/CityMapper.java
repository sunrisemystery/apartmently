package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.CityDto;
import com.jgajzler.apartmently.entity.City;
import org.springframework.stereotype.Component;

@Component
public class CityMapper {
    public CityDto toDto(City city) {

        return new CityDto(
                city.getName()
        );
    }
}

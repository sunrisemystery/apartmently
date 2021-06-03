package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.AdEditDto;
import com.jgajzler.apartmently.entity.Ad;
import org.springframework.stereotype.Component;

@Component
public class AdEditMapper {
    private final AddressMapper addressMapper = new AddressMapper();
    private final CountryMapper countryMapper = new CountryMapper();
    private final CityMapper cityMapper = new CityMapper();

    public AdEditDto toDto(Ad ad) {
        return new AdEditDto(
                ad.getId(),
                ad.getAdName(),
                ad.getDescription(),
                ad.getPlotSurface(),
                ad.getPrice(),
                ad.getNumberOfBedrooms(),
                ad.getNumberOfBathrooms(),
                ad.getFloorNumber(),
                addressMapper.toDto(ad.getAddress()),
                countryMapper.toDto(ad.getAddress().getCountry()),
                cityMapper.toDto(ad.getAddress().getCity()),
                ad.getAdType()

        );
    }
}

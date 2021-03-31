package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.entity.Ad;
import org.springframework.stereotype.Component;

@Component
public class AdMapper {

    public AdDto toDto(Ad ad) {

        return new AdDto(
                ad.getId(),
                ad.getAdName(),
                ad.getPlotSurface(),
                ad.getPrice(),
                ad.getNumberOfBedrooms(),
                ad.getNumberOfBathrooms(),
                ad.getDateCreated(),
                ad.getLastUpdated(),
                ad.isActive(),
                ad.getAddress().getCity().getName(),
                ad.getAddress().getCountry().getName(),
                ad.getAdImages(),
                ad.getAdType()
        );
    }
}

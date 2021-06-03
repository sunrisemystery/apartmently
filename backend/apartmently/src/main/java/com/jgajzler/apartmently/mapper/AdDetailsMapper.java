package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.AdDetailsDto;
import com.jgajzler.apartmently.entity.Ad;
import org.springframework.stereotype.Component;

@Component
public class AdDetailsMapper {
    public AdDetailsDto toDto(Ad ad) {
        return new AdDetailsDto(
                ad.getId(),
                ad.getFloorNumber(),
                ad.getUser().getId(),
                ad.getUser().getUserDetails().getName(),
                ad.getUser().getUserDetails().getPhoneNumber(),
                ad.getUser().getEmail(),
                ad.getUser().getUserDetails().getImageUrl(),
                ad.getDescription(),
                ad.getAddress().getLongitude(),
                ad.getAddress().getLatitude()
        );
    }
}

package com.jgajzler.apartmently.dto;

import com.jgajzler.apartmently.entity.AdImage;
import com.jgajzler.apartmently.entity.enums.AdType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;
import java.util.Set;

@Getter
@AllArgsConstructor

public class AdDto {

    private final Long id;
    private final String adName;
    private final double plotSurface;
    private final int price;
    private final int numberOfBedrooms;
    private final int numberOfBathrooms;
    private final Date dateCreated;
    private final Date lastUpdated;
    private final boolean isActive;
    private final String city;
    private final String country;
    private final Set<AdImage> adImages;
    private final AdType adType;
}

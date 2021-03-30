package com.jgajzler.apartmently.dto;

import com.jgajzler.apartmently.entity.AdImage;
import com.jgajzler.apartmently.entity.AdType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class AdDto {
    private Long id;
    private String adName;
    private double plotSurface;
    private int price;
    private int numberOfBedrooms;
    private int numberOfBathrooms;
    private Date dateCreated;
    private Date lastUpdated;
    private boolean isActive;
    private String city;
    private String country;
    private Set<AdImage> adImages;
    AdType adType;




}

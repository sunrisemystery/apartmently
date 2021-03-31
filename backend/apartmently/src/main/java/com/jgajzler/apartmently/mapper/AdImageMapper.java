package com.jgajzler.apartmently.mapper;

import com.jgajzler.apartmently.dto.AdImageDto;
import com.jgajzler.apartmently.entity.AdImage;
import org.springframework.stereotype.Component;

@Component
public class AdImageMapper {

    public AdImageDto toDto(AdImage adImage) {

        return new AdImageDto(
                adImage.getImageUrl()
        );
    }
}

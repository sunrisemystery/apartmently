package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.AdImageDto;
import com.jgajzler.apartmently.service.AdImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/ad-image")
public class AdImageController {
    private final AdImageService adImageService;

    @Autowired
    public AdImageController(AdImageService adImageService) {
        this.adImageService = adImageService;
    }

    @GetMapping(path = "ad/{adId}")
    public List<AdImageDto> getAdImageByAdId(@PathVariable("adId") Long id) {
        return adImageService.getImageUrlByAdId(id);
    }
}

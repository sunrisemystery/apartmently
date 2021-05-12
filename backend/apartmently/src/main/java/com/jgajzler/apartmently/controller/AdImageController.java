package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.AdImageDto;
import com.jgajzler.apartmently.service.AdImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
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

    @PostMapping(path = "{adId}")
    public void saveAdImageToAd(@PathVariable("adId") Long id, @RequestBody List<String> urls) {
        adImageService.saveImage(id, urls);
    }

}

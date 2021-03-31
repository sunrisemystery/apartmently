package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/ad")
public class AdController {
    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping(path = "{adId}")
    public AdDto getAdById(@PathVariable("adId") Long id) {
        return adService.getAdById(id);
    }

    @GetMapping(path = "user/{userId}")
    public Page<AdDto> getAllByUserId(@PathVariable("userId") Long id, Pageable pageable) {
        return adService.getAllByUserId(id, pageable);
    }
}

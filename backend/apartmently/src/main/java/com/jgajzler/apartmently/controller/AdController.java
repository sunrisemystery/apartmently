package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.AdDetailsDto;
import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.enums.AdType;
import com.jgajzler.apartmently.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Collections;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/ad")
public class AdController {
    private final AdService adService;

    @Autowired
    public AdController(AdService adService) {
        this.adService = adService;
    }

    @GetMapping(path = "details/{adId}")
    public AdDetailsDto getAdDetailsById(@PathVariable("adId") Long id) {
        return adService.getAdDetailsById(id);
    }

    @GetMapping(path = "{adId}")
    public AdDto getAdById(@PathVariable("adId") Long id) {
        return adService.getAdById(id);
    }

    @GetMapping(path = "user/{userId}")
    public Page<AdDto> getAllByUserId(@PathVariable("userId") Long id, Pageable pageable) {
        return adService.getAllByUserId(id, pageable);
    }

    @GetMapping(path = "rent")
    public Page<AdDto> getAllRent(Pageable pageable) {
        return adService.getAdByAdType(AdType.RENT, pageable);
    }

    @GetMapping(path = "sale")
    public Page<AdDto> getAllSale(Pageable pageable) {
        return adService.getAdByAdType(AdType.SALE, pageable);
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody Ad ad) {

        Long createdId = adService.add(ad);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdId).toUri();
        return ResponseEntity.created(location).body(Collections.singletonMap("id", createdId));
    }

    //doing it on the side where joining column
    @PostMapping(path = "favorites/{adId}/{userId}")
    public void addToFavorites(@PathVariable("userId") Long userId, @PathVariable("adId") Long adId) {
        adService.addToFavorites(userId, adId);
    }
}

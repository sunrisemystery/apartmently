package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.AdDetailsDto;
import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.dto.AdEditDto;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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

    @GetMapping
    public Page<AdDto> getAll(Pageable pageable) {
        return adService.getAll(pageable);
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

    @GetMapping(path = "search/{keyword}")
    public Page<AdDto> getAllByKeyword(@PathVariable("keyword") String keyword, Pageable pageable) {
        return adService.findAllByKeyword(keyword, pageable);
    }

    @GetMapping(path = "favorites/{userId}")
    public Page<AdDto> getUserFavorites(@PathVariable("userId") Long id, Pageable pageable) {
        return adService.findUserFavoritesByUserId(id, pageable);
    }

    @GetMapping(path = "ad-types")
    public List<AdType> getAdTypes() {
        return Arrays.asList(AdType.values());
    }
    @GetMapping(path = "edit/{adId}")
    public AdEditDto getAdForEdit(@PathVariable("adId") Long id){
        return adService.getAdForEdit(id);
    }

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody Ad ad) {

        Long createdId = adService.add(ad);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdId).toUri();
        return ResponseEntity.created(location).body(Collections.singletonMap("id", createdId));
    }

    @PutMapping("/{adId}")
    public void update(@RequestBody Ad ad, @PathVariable("adId") Long id) {
        adService.update(ad, id);
    }

    //doing it on the side where joining column
    @PostMapping(path = "favorites/{adId}/{userId}")
    public void addToFavorites(@PathVariable("userId") Long userId, @PathVariable("adId") Long adId) {
        adService.addToFavorites(userId, adId);
    }

    @DeleteMapping(path = "{adId}")
    public ResponseEntity<Map<String, String>> deleteAd(@PathVariable("adId") Long id) {
        adService.deleteAdById(id);
        return ResponseEntity.ok().body(Collections.singletonMap("message", "Ad deleted successfully"));
    }

    @PatchMapping("active/{adId}/{isActive}")
    public void changeActiveState(@PathVariable("adId") Long id, @PathVariable("isActive") boolean isActive) {
        adService.setActiveById(id, isActive);
    }
}

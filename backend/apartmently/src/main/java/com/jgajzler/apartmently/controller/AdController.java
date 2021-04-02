package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.entity.Ad;
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

    @PostMapping
    public ResponseEntity<Map<String, Long>> create(@RequestBody Ad ad) {

        Long createdId = adService.add(ad);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdId).toUri();
        return ResponseEntity.created(location).body(Collections.singletonMap("id", createdId));
    }

}

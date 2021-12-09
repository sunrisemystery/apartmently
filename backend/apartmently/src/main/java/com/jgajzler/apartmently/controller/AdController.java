package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.AdDetailsDto;
import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.dto.AdEditDto;
import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.enums.AdType;
import com.jgajzler.apartmently.service.AdService;
import com.jgajzler.apartmently.util.PDFGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.ByteArrayInputStream;
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
    public ResponseEntity<Page<AdDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok().body(adService.getAll(pageable));
    }

    @GetMapping(path = "details/{adId}")
    public ResponseEntity<AdDetailsDto> getAdDetailsById(@PathVariable("adId") Long id) {
        return ResponseEntity.ok().body(adService.getAdDetailsById(id));
    }

    @GetMapping(path = "{adId}")
    public AdDto getAdById(@PathVariable("adId") Long id) {
        return adService.getAdDtoById(id);
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

    @GetMapping(path = "favlist/{userId}")
    public List<Long> getUserFavoritesIds(@PathVariable("userId") Long id) {
        return adService.findFavList(id);

    }

    @GetMapping(path = "ad-types")
    public List<AdType> getAdTypes() {
        return Arrays.asList(AdType.values());
    }

    @GetMapping(path = "edit/{adId}")
    public AdEditDto getAdForEdit(@PathVariable("adId") Long id) {
        return adService.getAdForEdit(id);
    }

    @GetMapping(path = "generate-pdf/{adId}", produces = MediaType.APPLICATION_PDF_VALUE)
    public ResponseEntity<InputStreamResource> adReport(@PathVariable("adId") Long id) {
        Ad ad = adService.getAdById(id);
        ByteArrayInputStream bis = PDFGenerator.adReport(ad);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Content-Disposition", "inline");
        return ResponseEntity
                .ok()
                .headers(httpHeaders)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));
    }


    @GetMapping(path = "permitted/{userId}")
    public List<AdDto> getPermissionListByUserId(@PathVariable("userId") Long userId) {
        return adService.getPermissionList(userId);
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

    @PostMapping(path = "permit-pdf/{adId}/{userId}")
    public void givePermission(@PathVariable("adId") Long adId, @PathVariable("userId") Long userId) {
        adService.givePermissionForPdf(adId, userId);
    }

    @PutMapping(path = "favorites/{adId}/{userId}")
    public void removeFromFavorites(@PathVariable("userId") Long userId, @PathVariable("adId") Long adId) {
        adService.removeFromFavorites(userId, adId);
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

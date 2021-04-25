package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.entity.City;
import com.jgajzler.apartmently.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/cities")
public class CityController {
    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @GetMapping(path = "check/{name}")
    public boolean checkIfExists(@PathVariable("name") String name) {
        return cityService.checkIfExist(name);
    }

    @GetMapping("{name}")
    public Long getIdByName(@PathVariable("name") String name) {
        return cityService.getIdByName(name);
    }

    @PostMapping
    public ResponseEntity<Long> create(@RequestBody City city) {
        Long createdId = cityService.add(city);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(createdId).toUri();
        return ResponseEntity.created(location).body(createdId);
    }

}

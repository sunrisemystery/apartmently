package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.CountryDto;
import com.jgajzler.apartmently.mapper.CountryMapper;
import com.jgajzler.apartmently.repository.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final CountryMapper countryMapper;

    @Autowired
    public CountryService(CountryRepository countryRepository, CountryMapper countryMapper) {
        this.countryRepository = countryRepository;
        this.countryMapper = countryMapper;
    }


    public List<CountryDto> getCountries(){
        return countryRepository.findAll().stream().map(countryMapper::toDto)
                .collect(Collectors.toList());
    }
}

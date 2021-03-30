package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.mapper.AdMapper;
import com.jgajzler.apartmently.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AdService {
    private AdRepository adRepository;
    private AdMapper adMapper;

    @Autowired
    public AdService(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    public AdDto getAdById(Long id){
        return adMapper.toDto(adRepository.findAdById(id).
                orElseThrow(EntityNotFoundException::new));
    }


}

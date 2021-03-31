package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.mapper.AdMapper;
import com.jgajzler.apartmently.repository.AdRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AdService {
    private final AdRepository adRepository;
    private final AdMapper adMapper;

    @Autowired
    public AdService(AdRepository adRepository, AdMapper adMapper) {
        this.adRepository = adRepository;
        this.adMapper = adMapper;
    }

    public AdDto getAdById(Long id) {
        return adMapper.toDto(adRepository.findAdById(id).
                orElseThrow(EntityNotFoundException::new));
    }

    public Page<AdDto> getAllByUserId(Long id, Pageable pageable) {
        return adRepository.findAllByUserId(id, pageable).map(adMapper::toDto);
    }

}

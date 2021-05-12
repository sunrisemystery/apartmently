package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.AdImageDto;
import com.jgajzler.apartmently.mapper.AdImageMapper;
import com.jgajzler.apartmently.repository.AdImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdImageService {
    private final AdImageRepository adImageRepository;
    private final AdImageMapper adImageMapper;

    @Autowired
    public AdImageService(AdImageRepository adImageRepository, AdImageMapper adImageMapper) {
        this.adImageRepository = adImageRepository;
        this.adImageMapper = adImageMapper;
    }

    public List<AdImageDto> getImageUrlByAdId(Long id) {
        return adImageRepository.findAllByAdId(id)
                .stream()
                .map(adImageMapper::toDto)
                .collect(Collectors.toList());
    }

    public void saveImage(Long id, List<String> urls) {
        for (String url : urls) {
            adImageRepository.saveImageByAdId(id, url);
        }
    }
}

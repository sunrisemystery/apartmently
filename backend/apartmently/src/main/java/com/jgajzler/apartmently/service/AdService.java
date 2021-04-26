package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.AdDetailsDto;
import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.entity.Ad;
import com.jgajzler.apartmently.entity.enums.AdType;
import com.jgajzler.apartmently.mapper.AdDetailsMapper;
import com.jgajzler.apartmently.mapper.AdMapper;
import com.jgajzler.apartmently.repository.AdRepository;
import com.jgajzler.apartmently.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AdService {
    private final AdRepository adRepository;
    private final UserRepository userRepository;
    private final AdMapper adMapper;
    private final AdDetailsMapper adDetailsMapper;

    @Autowired
    public AdService(AdRepository adRepository, UserRepository userRepository,
                     AdMapper adMapper, AdDetailsMapper adDetailsMapper) {
        this.adRepository = adRepository;
        this.userRepository = userRepository;
        this.adMapper = adMapper;
        this.adDetailsMapper = adDetailsMapper;
    }

    public AdDetailsDto getAdDetailsById(Long id) {
        return adDetailsMapper.toDto(adRepository.findAdById(id).
                orElseThrow(EntityNotFoundException::new));
    }

    public AdDto getAdById(Long id) {
        return adMapper.toDto(adRepository.findAdById(id).
                orElseThrow(EntityNotFoundException::new));
    }

    public Page<AdDto> getAllByUserId(Long id, Pageable pageable) {
        return adRepository.findAllByUserId(id, pageable).map(adMapper::toDto);
    }

    public Long add(Ad ad) {
        return adRepository.save(ad).getId();
    }

    public void addToFavorites(Long userId, Long adId) {

        Ad ad = adRepository.findAdById(adId).orElseThrow(EntityNotFoundException::new);
        ad.getUsersFav().add(userRepository.findUserById(userId));
        adRepository.save(ad);
    }

    public Page<AdDto> getAdByAdType(AdType adType, Pageable pageable) {
        return adRepository.findAllByAdType(adType, pageable).map(adMapper::toDto);
    }

    public Page<AdDto> getAll(Pageable pageable) {
        return adRepository.findAll(pageable).map(adMapper::toDto);
    }

    public Page<AdDto> findAllByKeyword(String keyword, Pageable pageable) {
        return adRepository.findAllByKeyword(keyword, pageable).map(adMapper::toDto);
    }

    public Page<AdDto> findUserFavoritesByUserId(Long id, Pageable pageable) {
        return adRepository.findAdsByUsersFavId(id, pageable).map(adMapper::toDto);
    }

    public void deleteAdById(Long id) {
        adRepository.deleteById(id);
    }

    public void setActiveById(Long id, boolean isActive) {
        adRepository.setActiveById(id, isActive);
    }


}

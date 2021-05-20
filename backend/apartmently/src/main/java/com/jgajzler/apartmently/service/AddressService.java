package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.AddressDto;
import com.jgajzler.apartmently.mapper.AddressMapper;
import com.jgajzler.apartmently.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class AddressService {
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;

    @Autowired
    public AddressService(AddressRepository addressRepository, AddressMapper addressMapper) {
        this.addressRepository = addressRepository;
        this.addressMapper = addressMapper;
    }

    public AddressDto getAddressByAdId(Long id) {
        return addressMapper.toDto(addressRepository.findAddressByAdId(id)
                .orElseThrow(EntityNotFoundException::new));
    }

}

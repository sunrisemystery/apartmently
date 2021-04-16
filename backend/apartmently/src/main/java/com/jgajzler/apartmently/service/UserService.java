package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.dto.UserDto;
import com.jgajzler.apartmently.entity.User;
import com.jgajzler.apartmently.entity.UserDetails;
import com.jgajzler.apartmently.mapper.AdMapper;
import com.jgajzler.apartmently.mapper.UserMapper;
import com.jgajzler.apartmently.repository.UserDetailsRepository;
import com.jgajzler.apartmently.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserMapper userMapper;
    private final AdMapper adMapper;


    @Autowired
    public UserService(UserRepository userRepository, UserDetailsRepository userDetailsRepository, UserMapper userMapper, AdMapper adMapper) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userMapper = userMapper;
        this.adMapper = adMapper;
    }


    public UserDto getUserById(Long id) {
        return userMapper.toDto(findById(id), findByUserId(id));
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public UserDetails findByUserId(Long id) {
        return userDetailsRepository.findByUserId(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public Set<AdDto> getUserFavorites(Long id) {
        return userRepository.findUserById(id)
                .getFavoriteAds()
                .stream()
                .map(adMapper::toDto).collect(Collectors.toSet());

    }


}

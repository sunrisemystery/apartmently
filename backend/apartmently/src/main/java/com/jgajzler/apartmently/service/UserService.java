package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.UserDto;
import com.jgajzler.apartmently.entity.User;
import com.jgajzler.apartmently.entity.UserDetails;
import com.jgajzler.apartmently.mapper.UserMapper;
import com.jgajzler.apartmently.repository.UserDetailsRepository;
import com.jgajzler.apartmently.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
public class UserService {

    private UserRepository userRepository;
    private UserDetailsRepository userDetailsRepository;
    private UserMapper userMapper;

    @Autowired

    public UserService(UserRepository userRepository,
                       UserDetailsRepository userDetailsRepository,
                       UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userMapper = userMapper;
    }

    public UserDto getUserById(Long id) {
        return userMapper.toDto(findById(id), findByUserId(id));
    }



    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    private UserDetails findByUserId(Long id) {
        return userDetailsRepository.findByUserId(id)
                .orElseThrow(EntityNotFoundException::new);
    }
}

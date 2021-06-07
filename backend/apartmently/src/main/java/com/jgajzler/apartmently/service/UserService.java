package com.jgajzler.apartmently.service;

import com.jgajzler.apartmently.dto.UserDto;
import com.jgajzler.apartmently.entity.User;
import com.jgajzler.apartmently.entity.UserDetails;
import com.jgajzler.apartmently.mapper.UserMapper;
import com.jgajzler.apartmently.repository.UserDetailsRepository;
import com.jgajzler.apartmently.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final UserDetailsRepository userDetailsRepository;
    private final UserMapper userMapper;


    @Autowired
    public UserService(UserRepository userRepository, UserDetailsRepository userDetailsRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.userMapper = userMapper;
    }

    public UserDto getUserById(Long id) {
        return userMapper.toDto(findById(id), findDetailsByUserId(id));
    }

    public User findUserById(Long id) {
        return userRepository.findUserById(id);
    }

    private User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public UserDetails findDetailsByUserId(Long id) {
        return userDetailsRepository.findByUserId(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public void add(User user) {
        userRepository.save(user);
    }

    public void addDetails(UserDetails userDetails) {
        userDetailsRepository.save(userDetails);
    }

    public void update(UserDto userDto) {
        User updatedUser = userRepository.findUserById(userDto.getUserId());
        updatedUser.getUserDetails().setPhoneNumber(userDto.getPhoneNumber());
        updatedUser.getUserDetails().setSurname(userDto.getSurname());
        updatedUser.getUserDetails().setName(userDto.getName());
        userRepository.save(updatedUser);
    }

    public Page<UserDto> getAll(Pageable pageable) {
        List<UserDto> userDtoList = new ArrayList<>();

        userRepository.findAll(pageable).map(
                user -> {
                    UserDetails userDetails = user.getUserDetails();
                    userDtoList.add(userMapper.toDto(user, userDetails));
                    return userMapper.toDto(user, userDetails);
                }
        );
        return new PageImpl<>(userDtoList.subList(0, userDtoList.size()), pageable, userDtoList.size());
    }

    public void deleteUserById(Long id) {
        userDetailsRepository.delete(userRepository.findUserById(id).getUserDetails());

    }


}

package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.AdDto;
import com.jgajzler.apartmently.dto.UserDto;
import com.jgajzler.apartmently.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/user")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path = "{userId}")
    public UserDto getUserById(@PathVariable("userId") Long id) {
        return userService.getUserById(id);
    }

    @GetMapping(path = "favorites/{userId}")
    public Set<AdDto> getUserFavorites(@PathVariable("userId") Long id) {
        return userService.getUserFavorites(id);
    }


}

package com.jgajzler.apartmently.controller;

import com.jgajzler.apartmently.dto.JwtDto;
import com.jgajzler.apartmently.dto.LoginDto;
import com.jgajzler.apartmently.dto.RegisterDto;
import com.jgajzler.apartmently.dto.UserDto;
import com.jgajzler.apartmently.entity.User;
import com.jgajzler.apartmently.security.UserDetails;
import com.jgajzler.apartmently.security.jwt.JwtUtils;
import com.jgajzler.apartmently.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(path = "api/user")
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;

    @Autowired
    public UserController(UserService userService, AuthenticationManager authenticationManager, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }


    @GetMapping(path = "{userId}")
    public UserDto getUserById(@PathVariable("userId") Long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/sign-in")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginDto loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtDto(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> registerUser(@RequestBody RegisterDto register) {
        if (userService.existsByUsername(register.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", "Username is already taken!"));
        }

        if (userService.existsByEmail(register.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(Collections.singletonMap("error", "Email is already in use!"));
        }

        User user = new User();
        user.setEmail(register.getEmail());
        user.setUsername(register.getUsername());
        user.setPassword(passwordEncoder.encode(register.getPassword()));

        userService.add(user);

        return ResponseEntity.ok(Collections.singletonMap("message", "User registered successfully!"));

    }

//    @GetMapping(path = "favorites/{userId}")
//    public Set<AdDto> getUserFavorites(@PathVariable("userId") Long id) {
//        return userService.getUserFavorites(id);
//    }


}

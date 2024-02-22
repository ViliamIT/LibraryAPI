package com.nextitproject.libraryapi.frontend.controller;


import com.nextitproject.libraryapi.backend.entity.AuthRequest;
import com.nextitproject.libraryapi.backend.entity.AuthResponse;
import com.nextitproject.libraryapi.backend.entity.UserInfo;
import com.nextitproject.libraryapi.backend.service.impl.JwtService;
import com.nextitproject.libraryapi.backend.service.impl.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class LoginController {
//
    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;
//
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

//    @ResponseStatus()
//    public String addNewUser(@RequestBody UserInfo userInfo) {
//        return service.addUser(userInfo);
//    }
    @PostMapping("/addNewUser")
    public ResponseEntity<Void> addNewUser(@RequestBody UserInfo userInfo) {
        service.addUser(userInfo);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping("/user/userProfile")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String userProfile() {
        return "Welcome to User Profile";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public AuthResponse authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(jwtService.generateToken(authRequest.getUsername()));
        if (authentication.isAuthenticated()) {
            return authResponse;
        } else {
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

}

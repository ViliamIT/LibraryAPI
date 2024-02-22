package com.nextitproject.libraryapi.backend.service;

import com.nextitproject.libraryapi.backend.entity.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService {
    UserInfo findUserByEmail(String email);

    UserDetails loadUserByUsername(String email) throws UsernameNotFoundException;
}

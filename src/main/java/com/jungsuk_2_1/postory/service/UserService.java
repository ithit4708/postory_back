package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    void create(UserDto userDto);
    UserDto getByCredentials(String userEmail, String password, PasswordEncoder encoder);
    String checkUserStatus(UserDto userDto);
}
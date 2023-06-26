package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.HeaderDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

public interface UserService {
    void create(UserDto userDto) throws Exception;
    UserDto getByCredentials(String userEmail, String password, PasswordEncoder encoder) throws Exception;
    String checkUserStatus(UserDto userDto) throws Exception;
    HeaderDto getHeaderInfo (String userId);
}

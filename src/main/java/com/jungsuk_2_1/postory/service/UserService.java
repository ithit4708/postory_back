package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.HeaderChannelDto;
import com.jungsuk_2_1.postory.dto.HeaderUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

public interface UserService {
    void create(UserDto userDto) throws Exception;
    UserDto getByCredentials(String userEmail, String password, PasswordEncoder encoder) throws Exception;
    String checkUserStatus(UserDto userDto) throws Exception;
    List<HeaderChannelDto> getHeaderInfo (String userId);
    HeaderUserDto getHeaderUserInfo(String userId);
}

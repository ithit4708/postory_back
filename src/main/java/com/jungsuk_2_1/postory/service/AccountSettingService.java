package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;

public interface AccountSettingService {
    void changeUserProfile(UserDto userInfo) throws Exception;
    ProfileUserDto findUserByUserId(String userId);
}

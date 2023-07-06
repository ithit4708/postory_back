package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.ProfileUserDto;
import com.jungsuk_2_1.postory.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface AccountSettingService {
    void changeUserProfile(UserDto userInfo) throws Exception;

    ProfileUserDto findUserByUserId(String userId);

    String saveImage(MultipartFile userImgFile, String userId) throws Exception;
}

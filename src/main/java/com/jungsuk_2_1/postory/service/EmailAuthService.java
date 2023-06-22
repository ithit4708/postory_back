package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.EmailAuthDto;
import com.jungsuk_2_1.postory.dto.UserDto;

import java.util.Date;
import java.util.Map;

public interface EmailAuthService {
    void sendSimpleMessage(EmailAuthDto emailAuthDto)throws Exception;
    UserDto getUserByUserId(String userId);
    Date getExpireTime(EmailAuthDto emailAuthDto);
    Boolean compareCertiNo(Map<String,String> certinoCheckMap);
    void changeUserStatus(String userId);
}

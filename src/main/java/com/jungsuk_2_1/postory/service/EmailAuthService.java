package com.jungsuk_2_1.postory.service;

import com.jungsuk_2_1.postory.dto.EmailAuthDto;
import com.jungsuk_2_1.postory.dto.UserDto;

import java.util.Date;
import java.util.Map;

public interface EmailAuthService {
    void sendSimpleMessage(EmailAuthDto emailAuthDto)throws Exception;
    UserDto getUserByUserId(String userId) throws Exception;
    Date getExpireTime(EmailAuthDto emailAuthDto) throws Exception;
    Boolean compareCertiNo(Map<String,String> certinoCheckMap) throws Exception;
    void changeUserStatus(String userId) throws Exception;
}

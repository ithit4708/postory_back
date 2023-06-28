package com.jungsuk_2_1.postory.dao;

import com.jungsuk_2_1.postory.dto.EmailAuthDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.Date;
import java.util.Map;

@Mapper
public interface EmailAuthDao {
    void updateEmailAuth(EmailAuthDto emailAuthDto);
    Date findExpireTimeByUser(EmailAuthDto emailAuthDto);
    Boolean existsByUserIdAndCertino(Map<String, String> certino);
    void addUserStatus(String userId);
    void changeUserEmail(EmailAuthDto userInfo);
}